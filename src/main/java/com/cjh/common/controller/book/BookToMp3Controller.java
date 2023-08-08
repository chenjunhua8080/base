package com.cjh.common.controller.book;

import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.microsoft.cognitiveservices.speech.AudioDataStream;
import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisCancellationDetails;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/xml/to/mp3")
public class BookToMp3Controller {

    // This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
    private static String speechKey = System.getenv("SPEECH_KEY");
    private static String speechRegion = System.getenv("SPEECH_REGION");

    @PostMapping
    public void convert(@RequestBody Params params, HttpServletResponse response)
        throws IOException {
        if (params.getXml() == null) {
            response.setStatus(413);
            response.getWriter().write("xml is null");
            return;
        }
        int i = countOccurrences(params.getXml(), "</voice>");
        if (i > 50) {
            response.setStatus(413);
            response.getWriter().write("voice tag > 50, " + i);
            return;
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
            } else {
                log.warn("语音合成异常: {}", result.getReason());
            }
            AudioDataStream stream = AudioDataStream.fromResult(result);
            stream.saveToWavFile(outputVideoPath);
        }

        response.setContentType("application/octet-stream");
        response.setContentLengthLong(file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        //设置这个才会给前端拿到文件名
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        IoUtil.copy(Files.newInputStream(Paths.get(outputVideoPath)), response.getOutputStream());
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
    private static class Params {

        private String xml;
    }
}
