package com.cjh.common.controller.book;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.collect.Lists;
import com.microsoft.cognitiveservices.speech.AudioDataStream;
import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.OutputFormat;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisCancellationDetails;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisOutputFormat;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import java.io.File;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/xml/to/mp3")
public class BookToMp3Controller {

    /**
     * 1. voice <br/>
     * 2. style <br/>
     * 3. styledegree <br/>
     * 4. role <br/>
     * 5. rate <br/>
     * 6. text <br/>
     */
    private static String XML = ""
        + "<speak xmlns=\"http://www.w3.org/2001/10/synthesis\" xmlns:mstts=\"http://www.w3.org/2001/mstts\" xmlns:emo=\"http://www.w3.org/2009/10/emotionml\" version=\"1.0\" xml:lang=\"zh-CN\">\n"
        + "    <voice name=\"%s\">\n"
        + "        <mstts:express-as  style=\"%s\" styledegree=\"%s\" role=\"%s\">\n"
        + "            <prosody rate=\"%s\" volume=\"%s\">%s</prosody>\n"
        + "        </mstts:express-as>\n"
        + "    </voice>\n"
        + "</speak>";

    // This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
    private static String speechKey = System.getenv("SPEECH_KEY");
    private static String speechRegion = System.getenv("SPEECH_REGION");

    @Value("${domain}")
    private String domain;

    @PostMapping
    public R<String> convert(@RequestBody XmlParams params) {
        if (params.getXml() == null) {
            return R.failed("xml is null");
        }
        int i = countOccurrences(params.getXml(), "</voice>");
        if (i > 50) {
            return R.failed("voice tag > 50, " + i);
        }

        log.info(System.getProperty("java.library.path"));
        log.info(System.getProperty("java.library.path"));
        log.info(System.getProperty("java.library.path"));

        String path = "/home/book/mp3/";
        String fileName = DigestUtil.md5Hex(params.getXml()) + ".wav";
        String outputVideoPath = path + fileName;
        mkdirs(path);
        File file = new File(outputVideoPath);

        if (!file.exists()) {
            log.info("speechRegion: {}", speechRegion);
            SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
            SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig, null);
            SpeechSynthesisResult result = speechSynthesizer.SpeakSsml(params.getXml());
            if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                log.info("语音合成成功: {}", result.getReason());
            } else if (result.getReason() == ResultReason.Canceled) {
                log.error("语音合成失败: {}", result.getReason());
                SpeechSynthesisCancellationDetails details = SpeechSynthesisCancellationDetails.fromResult(result);
                log.error("CANCELED: Reason=" + details.getReason());

                if (details.getReason() == CancellationReason.Error) {
                    log.error("CANCELED: ErrorCode=" + details.getErrorCode());
                    log.error("CANCELED: ErrorDetails=" + details.getErrorDetails());
                }
                return R.failed("语音合成失败: " + result.getReason());
            } else {
                log.warn("语音合成异常: {}", result.getReason());
                return R.failed("语音合成异常: " + result.getReason());
            }
            AudioDataStream stream = AudioDataStream.fromResult(result);
            stream.saveToWavFile(outputVideoPath);
        }

        return R.ok(domain + "/file/mp3/" + fileName);
    }

    @PostMapping("/v2")
    public R<String> convertV2(@RequestBody V2Params params) throws Exception {
        String text = params.getText();
        if (!StringUtils.hasText(text)) {
            return R.failed("text is null");
        }

        log.info(System.getProperty("java.library.path"));
        log.info(System.getProperty("java.library.path"));
        log.info(System.getProperty("java.library.path"));

        List<String> xmlList = getXML(params.getVoice(),
            params.getStyle(),
            params.getStyledegree(),
            params.getRole(),
            params.getRate(),
            params.getVolume(),
            text.trim());

        String path = "/home/book/mp3/";
        String fileName = DigestUtil.md5Hex(xmlList.toString()) + ".wav";
        String outputAudioPath = path + fileName;
        mkdirs(path);
        File file = new File(outputAudioPath);

        if (!file.exists()) {
            if (xmlList.size() == 1) {
                String msg = createAudio(xmlList.get(0), outputAudioPath);
                if (msg != null) {
                    return R.failed(msg);
                }
            } else {
                List<File> files = Lists.newArrayList();
                for (int i = 0; i < xmlList.size(); i++) {
                    String newFile = outputAudioPath.replace(".wav", "_" + i + ".wav");
                    String xml = xmlList.get(i);
                    String msg = createAudio(xml, newFile);
                    if (msg != null) {
                        return R.failed(msg);
                    }
                    files.add(new File(newFile));
                }
                mergeAudioFiles(files, file);
            }
        }

        return R.ok(domain + "/file/mp3/" + fileName);
    }

    /**
     * Create audio
     *
     * @param xml             xml
     * @param outputVideoPath file path
     * @return error msg
     */
    private static String createAudio(String xml, String outputVideoPath)
        throws ExecutionException, InterruptedException {
        log.info("speechRegion: {}", speechRegion);
        log.info("xml: \n{}", xml);
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
        speechConfig.setOutputFormat(OutputFormat.Detailed);
        // 高品质
        speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Riff48Khz16BitMonoPcm);
        // 输出流
        AudioConfig audioConfig = AudioConfig.fromWavFileOutput(outputVideoPath);
        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
        SpeechSynthesisResult result = speechSynthesizer.SpeakSsmlAsync(xml).get();
        if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
            log.info("语音合成成功: {}", result.getReason());
        } else if (result.getReason() == ResultReason.Canceled) {
            log.error("语音合成失败: {}", result.getReason());
            SpeechSynthesisCancellationDetails details = SpeechSynthesisCancellationDetails.fromResult(result);
            log.error("CANCELED: Reason=" + details.getReason());

            if (details.getReason() == CancellationReason.Error) {
                log.error("CANCELED: ErrorCode=" + details.getErrorCode());
                log.error("CANCELED: ErrorDetails=" + details.getErrorDetails());
            }
            return "语音合成失败: " + result.getReason();
        } else {
            log.error("语音合成异常: {}", result.getReason());
            return "语音合成异常: " + result.getReason();
        }
//        AudioDataStream stream = AudioDataStream.fromResult(result);
//        stream.saveToWavFile(outputVideoPath);
        result.close();
        speechSynthesizer.close();
        audioConfig.close();

        return null;
    }

    /**
     * Get XML
     *
     * @param voice       voice
     * @param style       style
     * @param styledegree styledegree
     * @param role        role
     * @param rate        rate
     * @param volume      volume
     * @param text        text
     * @return list
     */
    private static List<String> getXML(String voice, String style, String styledegree, String role, String rate,
        String volume, String text) {
        // test
//        int pageSize = 50;
        int pageSize = 3000;
        List<String> list = Lists.newArrayList();
        int length = text.length();
        if (length > pageSize) {
            int pageTotal = length / pageSize + (length % pageSize == 0 ? 0 : 1);
            for (int i = 0; i < pageTotal; i++) {
                String newText = text.substring(i * pageSize, Math.min((i + 1) * pageSize, text.length()));
                list.add(String.format(XML, voice, style, styledegree, role, rate, volume, newText));
            }
        } else {
            list.add(String.format(XML, voice, style, styledegree, role, rate, volume, text));
        }
        return list;
    }

    public static void mergeAudio(List<File> audioFiles, File outputFile) throws Exception {

    }


    private static void mergeAudioFiles(List<File> audioFiles, File outputFile) throws Exception {
        // 总时长
        long totalLength = 0;
        AudioFormat audioFormat = null;
        AudioInputStream audioInputStream;
        List<AudioInputStream> audioInputStreams = new ArrayList<>();

        // 读取每个音频文件，获取其音频格式和音频流
        for (File audioFile : audioFiles) {
            audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            totalLength += audioInputStream.getFrameLength();
            audioFormat = audioInputStream.getFormat();
            audioInputStreams.add(audioInputStream);
        }

        // 创建新的音频格式和流
        boolean isSigned = audioFormat.getEncoding() == AudioFormat.Encoding.PCM_SIGNED;
        AudioFormat newAudioFormat = new AudioFormat(audioFormat.getSampleRate(),
            audioFormat.getSampleSizeInBits(),
            audioFormat.getChannels(),
            isSigned,
            audioFormat.isBigEndian());
        AudioInputStream mergedAudioStream = new AudioInputStream(
            new SequenceInputStream(Collections.enumeration(audioInputStreams)),
            newAudioFormat,
            totalLength
        );

        // 写入合并后的音频流到输出文件
        AudioSystem.write(mergedAudioStream, AudioFileFormat.Type.WAVE, outputFile);

        // 关闭所有音频流
        for (AudioInputStream ais : audioInputStreams) {
            ais.close();
        }

        log.info("合并 {} 个语音完成：{}", audioFiles.size(), outputFile);

        mergedAudioStream.close();
    }

    private static void mkdirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static int countOccurrences(String input, String target) {
        int count = 0;
        int index = 0;

        while ((index = input.indexOf(target, index)) != -1) {
            count++;
            index += target.length();
        }

        return count;
    }

    @Data
    private static class XmlParams {

        private String xml;
    }

    @Data
    private static class V2Params {

        private String text;
        private String voice;
        private String role;
        private String style;
        private String styledegree;
        private String rate;
        private String volume;
    }
}
