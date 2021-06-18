package com.cjh.common.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjh.common.boss.BossException;
import com.cjh.common.dao.EmailsDao;
import com.cjh.common.po.EmailsPO;
import com.cjh.common.request.EmailsRequest;
import com.cjh.common.service.EmailsService;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 邮件表
 *
 * @author cjh
 * @email chenjunhua8080@qq.com
 * @date 2021-05-29 14:29:43
 */
@Slf4j
@AllArgsConstructor
@Service
public class EmailsServiceImpl extends ServiceImpl<EmailsDao, EmailsPO> implements EmailsService {

    /**
     * createDir
     *
     * @param username qq
     */
    private static String createDir(String username) {
        String filePath = System.getProperty("user.home")
            + File.separator + "resume"
            + File.separator + username
            + File.separator + DateUtil.formatDate(DateUtil.date())
            + File.separator + System.currentTimeMillis()
            + File.separator;
        File dir = new File(filePath);
        if (dir.exists()) {
            int i = 2;
            while (true) {
                if (filePath.contains("_")) {
                    filePath = filePath.replaceAll("_.*", "_" + i);
                } else {
                    filePath = filePath + "_" + i;
                }
                dir = new File(filePath);
                if (dir.exists()) {
                    i++;
                } else {
                    dir.mkdirs();
                    return filePath;
                }
            }
        } else {
            dir.mkdirs();
        }
        return filePath;
    }

    @Override
    public IPage<EmailsPO> listByPage(EmailsRequest emailsRequest) {
        Page page = new Page(emailsRequest.getPageNum(), emailsRequest.getPageSize());
        IPage<EmailsPO> pages = baseMapper.listByPage(page, emailsRequest);
        return pages;
    }

    @Override
    public Integer getNotDownCount(EmailsRequest emails) {
        LambdaQueryWrapper<EmailsPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(EmailsPO::getOrgTo, emails.getOrgTo());
        queryWrapper.isNull(EmailsPO::getDownloadTime);
        return baseMapper.selectCount(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public File zip(EmailsRequest emails) {
        List<Integer> emailsIds = emails.getIds();
        if (CollectionUtils.isEmpty(emailsIds)) {
            throw new BossException("请选择需要下载的邮件！");
        }
        LambdaQueryWrapper<EmailsPO> queryWrapper = new LambdaQueryWrapper<>();
        List<EmailsPO> list = baseMapper.selectList(queryWrapper.in(EmailsPO::getId, emailsIds));
//        File[] srcFiles = list.stream().map(item -> new File(item.getAttrSrc())).toArray(File[]::new);
        return handleData(emails, list);
    }

    @Override
    public File down(EmailsRequest emails) {
        EmailsPO emailsPO = baseMapper.selectById(emails.getId());
        if (ObjectUtils.isEmpty(emailsPO)) {
            throw new BossException("请选择需要下载的邮件！");
        }

        EmailsPO entity = new EmailsPO();
        entity.setId(emailsPO.getId());
        entity.setDownloadTime(new Date());
        baseMapper.updateById(entity);

        return new File(emailsPO.getAttrSrc());
    }

    @Override
    public File downloadByNever(EmailsRequest emails) {
        LambdaQueryWrapper<EmailsPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(EmailsPO::getOrgTo, emails.getOrgTo());
        queryWrapper.isNull(EmailsPO::getDownloadTime);
        List<EmailsPO> list = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BossException("请选择需要下载的邮件！");
        }
        return handleData(emails, list);
    }

    private File handleData(EmailsRequest emails, List<EmailsPO> list) {
        list = list.stream().filter(po -> !StringUtils.isEmpty(po.getAttrSrc())).collect(
            Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(EmailsPO::getAttrSrc)))
                , ArrayList::new
            )
        );
        if (CollectionUtils.isEmpty(list)) {
            throw new BossException("附件地址为空！");
        }
        List<File> fileList = Lists.newArrayList();
        for (Iterator<EmailsPO> iterator = list.iterator(); iterator.hasNext(); ) {
            EmailsPO po = iterator.next();
            File file = new File(po.getAttrSrc());
            if (file.exists()) {
                fileList.add(file);
            } else {
                iterator.remove();
            }
        }
//        Set<String> set = list.stream().map(EmailsPO::getAttrSrc).collect(Collectors.toSet());
//        File[] files = set.stream().map(File::new).filter(File::exists).toArray(File[]::new);
        File[] files = fileList.toArray(new File[0]);
        if (ObjectUtils.isEmpty(files)) {
            throw new BossException("附件文件为空！");
        }
        String name = "";
        try {
            name = new String((DateUtil.formatDate(DateUtil.date()) + "_共" + files.length + "份.zip").getBytes("utf-8"));
        } catch (Exception ignored) {

        }
        File zipFile = new File(createDir(emails.getOrgTo()) + name);
        File zip = ZipUtil.zip(zipFile, false, files);

        List<EmailsPO> updateList = list.stream().map(item -> {
            EmailsPO po = new EmailsPO();
            po.setId(item.getId());
            po.setDownloadTime(new Date());
            return po;
        }).collect(Collectors.toList());

        saveOrUpdateBatch(updateList);

        return zip;
    }

}
