package com.cjh.common.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.cjh.common.po.EmailsPO;
import com.cjh.common.request.EmailsRequest;
import com.cjh.common.service.EmailsService;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件表
 *
 * @author cjh
 * @email chenjunhua8080@qq.com
 * @date 2021-05-29 14:29:43
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/common/emails")
public class EmailsController {

    private final EmailsService emailsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R<IPage<EmailsPO>> list(EmailsRequest emails) throws InterruptedException {
        IPage<EmailsPO> pageData = emailsService.listByPage(emails);
//        Thread.sleep(1000);
        return R.ok(pageData);
    }

    /**
     * 获取未下载数量
     */
    @RequestMapping("/getNotDownCount")
    public R<Integer> getNotDownCount(EmailsRequest emails) {
        Integer count = emailsService.getNotDownCount(emails);
        return R.ok(count);
    }

    /**
     * 批量下载
     */
    @RequestMapping("/downloadByIds")
    public void downloadByIds(EmailsRequest emails, HttpServletResponse response) {
        File zip = null;
        Exception error = null;
        try {
            zip = emailsService.zip(emails);
        } catch (Exception e) {
            e.printStackTrace();
            error = e;
        }
        outputFile(response, zip, true, error);
    }

    /**
     * 单个下载
     */
    @RequestMapping("/downloadById")
    public void downloadById(EmailsRequest emails, HttpServletResponse response) {
        File file = null;
        Exception error = null;
        try {
            file = emailsService.down(emails);
        } catch (Exception e) {
            e.printStackTrace();
            error = e;
        }
        outputFile(response, file, false, error);
    }

    /**
     * 下载未下载过的 从未
     */
    @RequestMapping("/downloadByNever")
    public void downloadByNever(EmailsRequest emails, HttpServletResponse response) {
        File zip = null;
        Exception error = null;
        try {
            zip = emailsService.downloadByNever(emails);
        } catch (Exception e) {
            e.printStackTrace();
            error = e;
        }
        outputFile(response, zip, true, error);
    }

    @SneakyThrows
    private void outputFile(HttpServletResponse response, File zip, boolean deleteFile, Exception error) {
        if (!ObjectUtils.isEmpty(error)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                String msg = StringUtils.isEmpty(error.getMessage()) ? error.toString() : error.getMessage();
                IoUtil.write(outputStream, false,
                    JSON.toJSONString(R.failed(msg)).getBytes(StandardCharsets.UTF_8));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return;
        }
        String encodeFileName = URLEncoder.encode(zip.getName(), "UTF-8");
        response.setContentType("application/octet-stream");
        response.setContentLengthLong(zip.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName);
        //设置这个才会给前端拿到文件名
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            FileUtil.writeToStream(zip, outputStream);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (deleteFile) {
                log.info("删除{}：{}", zip.getName(), zip.delete());
            }
        }
    }


}
