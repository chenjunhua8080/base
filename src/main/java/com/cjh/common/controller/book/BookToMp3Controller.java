package com.cjh.common.controller.book;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

    private Map<String, V2Params> v3ParamMap = Maps.newConcurrentMap();

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
                String msg = createAudio(xmlList.get(0), outputAudioPath, 3);
                if (msg != null) {
                    return R.failed(msg);
                }
            } else {
                List<File> files = Lists.newArrayList();
                for (int i = 0; i < xmlList.size(); i++) {
                    String newFilePath = outputAudioPath.replace(".wav", "_" + i + ".wav");
                    File newFile = new File(newFilePath);
                    if (!newFile.exists()) {
                        String xml = xmlList.get(i);
                        String msg = createAudio(xml, newFilePath, 3);
                        if (msg != null) {
                            FileUtil.del(newFilePath);
                            return R.failed(msg);
                        }
                    }
                    files.add(new File(newFilePath));
                }
                mergeAudioFiles(files, file);
            }
        }

        return R.ok(domain + "/file/mp3/" + fileName);
    }

    @PostMapping("/v3")
    public R<String> convertV3(@RequestBody V2Params params) {
        String id = IdUtil.objectId();
        v3ParamMap.put(id, params);
        return R.ok(id);
    }

    @GetMapping(value = "/v3/detail", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public SseEmitter v3Detail(String id) {
        SseEmitter emitter = new SseEmitter((long) (60 * 1000 * 10));

        V2Params params = v3ParamMap.get(id);
        if (params == null) {
            getSend(emitter, "result", "id is not found: " + id);
            return emitter;
        }

        Thread thread = ThreadUtil.newThread(() -> {
            ExecutorService executorService = ThreadUtil.newExecutor(8, 8);
            List<Callable<Void>> tasks = new ArrayList<>();

            String text = params.getText();
            if (!StringUtils.hasText(text)) {
                getSend(emitter, "result", "test is null");
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

            getSend(emitter, "progress", "全文：" + text.trim().length() + "字，拆分为" + xmlList.size() + "个音频");

            String path = "/home/book/mp3/";
            String fileName = DigestUtil.md5Hex(xmlList.toString()) + ".wav";
            String outputAudioPath = path + fileName;
            mkdirs(path);
            File file = new File(outputAudioPath);

            if (file.exists()) {
                getSend(emitter, "progress", "已缓存文件");
            } else {
                if (xmlList.size() == 1) {
                    String msg = createAudio(xmlList.get(0), outputAudioPath, 3);
                    if (msg != null) {
                        getSend(emitter, "result", msg);
                    }
                } else {
                    List<File> files = Lists.newArrayList();
                    for (int i = 0; i < xmlList.size(); i++) {
                        int finalI = i;
                        tasks.add(() -> {
                            TimeInterval timer = DateUtil.timer();
                            timer.start();
                            getSend(emitter, "progress", "正在创建第" + finalI + "个音频");
                            String newFilePath = outputAudioPath.replace(".wav", "_" + finalI + ".wav");
                            File newFile = new File(newFilePath);
                            if (!newFile.exists()) {
                                String xml = xmlList.get(finalI);
                                String msg = createAudio(xml, newFilePath, 5);
                                if (msg != null) {
                                    FileUtil.del(newFilePath);
                                    getSend(emitter, "progress", "第" + finalI + "个音频创建失败：" + msg);
                                    return null;
                                }
                                String end = timer.intervalPretty();
                                getSend(emitter, "progress", "成功创建第" + finalI + "个音频，耗时：" + end);
                            } else {
                                getSend(emitter, "progress", "成功创建第" + finalI + "个音频，已缓存");
                            }
                            files.add(new File(newFilePath));
                            getSend(emitter, "progress", "剩余：" + (xmlList.size() - files.size()) + "个");
                            return null;
                        });
                    }

                    try {
                        executorService.invokeAll(tasks);
                    } catch (InterruptedException e) {
                        String msg = "线程任务执行失败: " + e.getClass().getSimpleName() + " " + e.getMessage();
                        log.error("{}", msg, e);
                        getSend(emitter, "result", msg);
                    }

                    try {
                        getSend(emitter, "progress", "正在合并语音，共" + files.size() + "个...");
                        // 排序
                        files.sort(Comparator.comparing(File::getName));
                        mergeAudioFiles(files, file);
                    } catch (Exception e) {
                        String m = String.format("合并语音失败: %s: %s", e.getClass().getSimpleName(), e.getMessage());
                        log.error("{}", m, e);
                        getSend(emitter, "result", m);
                    }
                }
            }

            getSend(emitter, "complete", "complete");
            getSend(emitter, "result", domain + "/file/mp3/" + fileName);

            executorService.shutdown();
        }, "MP3V3");
        thread.start();
        log.info("线程启动！");

        return emitter;
    }

    private static void getSend(SseEmitter emitter, String state, Object data) {
        log.info("{} --- {}", state, data);
        try {
            emitter.send(SseEmitter.event().name(state).data(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create audio
     *
     * @param xml             xml
     * @param outputVideoPath file path
     * @param maxRetry        max retry
     * @return error msg
     */
    private static String createAudio(String xml, String outputVideoPath, int maxRetry) {
        log.info("speechRegion: {}", speechRegion);
        log.info("xml: \n{}", xml);
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
        speechConfig.setOutputFormat(OutputFormat.Detailed);
        // 高品质
        speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Riff48Khz16BitMonoPcm);
        // 输出流
        AudioConfig audioConfig = AudioConfig.fromWavFileOutput(outputVideoPath);
        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
        try {
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
                    throw new RuntimeException("语音合成失败: " + details.getErrorDetails());
                }
                throw new RuntimeException("语音合成失败: " + result.getReason());
//                return "语音合成失败: " + result.getReason();
            } else {
                log.error("语音合成异常: {}", result.getReason());
//                return "语音合成异常: " + result.getReason();
                throw new RuntimeException("语音合成失败: " + result.getReason());
            }
//        AudioDataStream stream = AudioDataStream.fromResult(result);
//        stream.saveToWavFile(outputVideoPath);
            result.close();
            speechSynthesizer.close();
            audioConfig.close();
        } catch (Exception e) {
            if (--maxRetry >= 0) {
                log.info("语音合成失败: {}, 重试...{}", e.getMessage(), maxRetry);
                createAudio(xml, outputVideoPath, maxRetry);
            }
            return e.getClass().getSimpleName() + ": " + e.getMessage();
        }

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
        int pageSize = 200;
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
    public static class V2Params {

        private String text;
        private String voice;
        private String role;
        private String style;
        private String styledegree;
        private String rate;
        private String volume;
    }
}
