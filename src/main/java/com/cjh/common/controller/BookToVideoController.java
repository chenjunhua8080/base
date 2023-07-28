package com.cjh.common.controller;

import cn.hutool.core.io.IoUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book/to/video")
public class BookToVideoController {

    @GetMapping("/qq")
    public void qq(String url, Integer index, HttpServletResponse response) throws IOException {
        index = index == null ? 1 : index;
//        if (url == null) {
//            return "url 不能为空";
//        }
//        if (!url.startsWith("https://book.qq.com/book-detail/")) {
//            return "url 格式不对，例：https://book.qq.com/book-detail/xxx";
//        }
        String bookId = url.substring(url.lastIndexOf("/") + 1);
        BookController bookController = new BookController();
        String text = bookController.find(url, index, index);
        String path = "/home/book/video/";
        String fileName = bookId + "_" + index + ".mp4";
        String outputVideoPath = path + fileName;
        mkdirs(path);
        try {
            // 读取小说文本内容
            List<String> novelLines = readNovelText(text);
            // 将小说文本转为图片
            List<BufferedImage> images = convertNovelToImages(novelLines);
            // 合成视频并保存
            createVideoFromImages(images, outputVideoPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        IoUtil.copy(Files.newInputStream(Paths.get(outputVideoPath)), response.getOutputStream());
    }

    private static void mkdirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private static List<String> readNovelText(String text) throws Exception {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(text));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    private static List<BufferedImage> convertNovelToImages(List<String> novelLines) {
        List<BufferedImage> images = new ArrayList<>();

        // 加载中文字体
        Font chineseFont = new Font("Microsoft YaHei", Font.PLAIN, 36);

        BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 1920, 1080);
        graphics.setFont(chineseFont);
        graphics.setColor(Color.BLACK);

        // 在图片上绘制小说内容
        int margin = 100;  // 左右边距
        int y = 100;
        int lineHeight = 50;  // 行高
        int maxWidth = 1920 - 2 * margin;  // 文字可用的最大宽度

        for (String line : novelLines) {

            Rectangle2D bounds = graphics.getFontMetrics().getStringBounds(line.trim(), graphics);

            if (bounds.getWidth() >= maxWidth) {
                int index = findNearestComma(line);
                String str1 = line.substring(0, index + 1);
                String str2 = line.substring(index + 1);

                graphics.drawString(str1.trim(), margin, y);
                y += lineHeight;

                if (y + lineHeight > 1080 - 100) {
                    images.add(image);
                    image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
                    graphics = image.createGraphics();
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(0, 0, 1920, 1080);
                    graphics.setFont(chineseFont);
                    graphics.setColor(Color.BLACK);
                    y = 100;
                }

                graphics.drawString(str2.trim(), margin, y);
                y += lineHeight;

                if (y + lineHeight > 1080 - 100) {
                    images.add(image);
                    image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
                    graphics = image.createGraphics();
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(0, 0, 1920, 1080);
                    graphics.setFont(chineseFont);
                    graphics.setColor(Color.BLACK);
                    y = 100;
                }
                continue;
            }

            graphics.drawString(line.trim(), margin, y);
            y += lineHeight;

            if (y + lineHeight > 1080 - 100) {
                images.add(image);
                image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
                graphics = image.createGraphics();
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, 1920, 1080);
                graphics.setFont(chineseFont);
                graphics.setColor(Color.BLACK);
                y = 100;
            }
        }
        images.add(image);

        return images;
    }


    private static void createVideoFromImages(List<BufferedImage> images, String file) throws Exception {
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(file, 1920, 1080);
        recorder.setFrameRate(1);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFormat("mp4");
        recorder.start();

        Java2DFrameConverter converter = new Java2DFrameConverter();
        for (int j = 0, imagesSize = images.size(); j < imagesSize; j++) {
            BufferedImage image = images.get(j);
            // 将BufferedImage转换为Frame
            Frame frame = converter.convert(image);
            for (int i = 0; i < 3; i++) {
                // 写入视频帧（持续3秒）
                recorder.record(frame);
            }
            // 生成过渡帧
            // ...
        }

        recorder.stop();
        recorder.close();
    }


    private static int findNearestComma(String sentence) {
        int middleIndex = sentence.length() / 2;
        int leftIndex = middleIndex - 1;
        int rightIndex = middleIndex + 1;

        while (leftIndex >= 0 && rightIndex < sentence.length()) {
            if (sentence.charAt(leftIndex) == ',' || sentence.charAt(leftIndex) == '，') {
                return leftIndex;
            } else if (sentence.charAt(rightIndex) == ',' || sentence.charAt(rightIndex) == '，') {
                return rightIndex;
            }
            leftIndex--;
            rightIndex++;
        }

        return -1; // 未找到逗号
    }
}
