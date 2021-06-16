package com.cjh.common.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.cjh.common.po.EmailsPO;
import com.cjh.common.request.EmailsRequest;
import com.cjh.common.service.EmailsService;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        Thread.sleep(1000);
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
        File zip = emailsService.zip(emails);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zip.getName(), "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            FileUtil.writeToStream(zip, outputStream);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            log.info("删除{}：{}", zip.getName(), zip.delete());
        }
    }


    /**
     * 单个下载
     */
    @RequestMapping("/downloadById")
    public void downloadById(EmailsRequest emails, HttpServletResponse response) {
        File file = emailsService.down(emails);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            FileUtil.writeToStream(file, outputStream);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * 下载未下载过的 从未
     */
    @RequestMapping("/downloadByNever")
    public void downloadByNever(EmailsRequest emails, HttpServletResponse response) {
        File zip = emailsService.downloadByNever(emails);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zip.getName(), "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            FileUtil.writeToStream(zip, outputStream);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


}
