package com.cjh.common.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.NioUtil;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cjh.common.api.ApiConfig;
import com.cjh.common.dao.BindFarmDao;
import com.cjh.common.enums.PlatformEnum;
import com.cjh.common.po.BindFarmPO;
import com.cjh.common.po.EmailsPO;
import com.cjh.common.service.EmailsService;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 邮件服务，批量下载简历
 *
 * @author cjh
 * @date 2021/4/26
 */
@EnableScheduling
@Component
@Slf4j
public class EmailJob {

    private static final ExecutorService executor = ExecutorBuilder.create()
        .setCorePoolSize(2)
        .setMaxPoolSize(2)
        .setKeepAliveTime(0)
        .setWorkQueue(new LinkedBlockingQueue<>(1000))
        .build();

    @Autowired
    private ApiConfig apiConfig;
    @Autowired
    private BindFarmDao bindFarmDao;
    @Autowired
    private EmailsService emailsService;

    public static List<EmailsPO> readEmail(String emailAccount, String auth) throws Exception {
        System.setProperty("sun.jnu.encoding ","utf-8");

        List<EmailsPO> list = new ArrayList<>();

        ApiConfig apiConfig = SpringUtil.getBean(ApiConfig.class);

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";//ssl加密,jdk1.8无法使用

        // 定义连接imap服务器的属性信息
        String port = "993";
        String imapServer = "imap.qq.com";
        String protocol = "imap";

        //有些参数可能不需要
        Properties props = new Properties();
        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.transport.protocol", protocol); // 使用的协议
        props.setProperty("mail.imap.port", port);
        props.setProperty("mail.imap.socketFactory.port", port);
        //断点续传，很慢的 https://www.jianshu.com/p/060b82ce19dd
        //api https://javaee.github.io/javamail/docs/api/com/sun/mail/imap/package-summary.html
        props.setProperty("mail.imap.partialfetch", "false");
        props.setProperty("mail.imap.fetchsize", "2000000");

        // 获取连接
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);

        // 获取Store对象
        Store store = session.getStore(protocol);
        store.connect(imapServer, emailAccount, auth); // 登陆认证

        // 通过imap协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
        Folder folder = store.getFolder("INBOX");// 获得用户的邮件帐户
        folder.open(Folder.READ_WRITE); // 设置对邮件帐户的访问权限

        int n = folder.getUnreadMessageCount();// 得到未读数量
        if (n>0){
            log.info("未读数量: {}", n);
        }

        //createDir
        String filePath = createDir(emailAccount);
        log.debug(filePath);

        //查找未读的邮件
        FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false); // false代表未读，true代表已读
        Message[] messages = folder.search(ft);
        CountDownLatch countDownLatch = ThreadUtil.newCountDownLatch(messages.length);

        // 标记已读
        List<Message> readMessages = new ArrayList<>();
        // 标记未读
        List<Message> unReadMessages = new ArrayList<>();
        for (Message message : messages) {
            EmailsPO emailsPO = new EmailsPO();

            String subject = message.getSubject();// 获得邮件主题
            Address from = message.getFrom()[0];// 获得发送者地址
            if (!subject.replaceAll(apiConfig.getBossConfig().getSubjectMatch(), "666~").contains("666~")) {
                log.warn("邮件内容不是简历: {}", subject);
                //不支持单个标记，只能标记文件夹
//                message.setFlag(Flags.Flag.SEEN, false);     //imap读取后邮件状态会变为已读,设为未读
//                message.saveChanges();
                unReadMessages.add(message);
                countDownLatch.countDown();
                continue;
            }
            log.debug("邮件的主题为: {}", subject);
            log.debug("发件人地址为: {}", decodeText(from.toString()));
            log.debug("日期: {}", message.getSentDate());

            emailsPO.setFromMail(emailAccount);
            emailsPO.setOrgTitle(subject);
            emailsPO.setOrgFrom(decodeText(from.toString()));
            emailsPO.setOrgSendTime(message.getSentDate());
            try {
                emailsPO.setOrgTo(decodeText(message.getRecipients(Message.RecipientType.TO)[0].toString()));
            } catch (Exception e) {
                log.error("获取接收者异常...", e);
            }

//            Enumeration headers = message.getAllHeaders();
//              log.info("----------------------allHeaders-----------------------------");
//              while (headers.hasMoreElements()) {
//                     Header header = (Header)headers.nextElement();
//                     log.info(header.getName()+" ======= "+header.getValue());
//                     log.info("----------------------------");
//                 }
            executor.execute(() -> {
                try {
                    String file = parseMultipart((Multipart) message.getContent(), filePath);
                    emailsPO.setAttrSrc(file);
//                    message.setFlag(Flags.Flag.SEEN, true);     //imap读取后邮件状态会变为已读,设为未读
//                    log.info("标记已读！");

                    readMessages.add(message);
                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
//                    try {
//                        message.setFlag(Flags.Flag.SEEN, false);     //imap读取后邮件状态会变为已读,设为未读
//                        log.info("异常，标记未读！");
//                    } catch (MessagingException eee) {
//                        eee.printStackTrace();
//                    }
                } finally {
//                    try {
//                        message.saveChanges();
//                    } catch (MessagingException e) {
//                        log.error("保存邮件状态标记失败！！!", e);
//                    }
                    countDownLatch.countDown();
                }
            });
            list.add(emailsPO);
        }
        countDownLatch.await();

        try {
            folder.setFlags(unReadMessages.toArray(new Message[0]), new Flags(Flags.Flag.SEEN), false);
        } catch (MessagingException e) {
            log.error("标记未读失败了。。。",e);
        }
        try {
            folder.setFlags(readMessages.toArray(new Message[0]), new Flags(Flags.Flag.SEEN), true);
        } catch (MessagingException e) {
            log.error("标记已读失败了。。。",e);
        }

        folder.close(false);// 关闭邮件夹对象
        store.close(); // 关闭连接对象

        return list;
    }

    protected static String decodeText(String text) throws UnsupportedEncodingException {
        if (text == null) {
            return null;
        }
        if (text.startsWith("=?GB") || text.startsWith("=?gb") || text.startsWith("=?utf-8")) {
            text = MimeUtility.decodeText(text);
        } else {
            text = new String(text.getBytes(StandardCharsets.ISO_8859_1));
        }
        return text;
    }

    /**
     * 对复杂邮件的解析
     *
     * @return 文件全路径
     */
    private static String parseMultipart(Multipart multipart, String filePath) throws MessagingException, IOException {
        int count = multipart.getCount();
        log.debug("count =  {}", count);
        for (int idx = 0; idx < count; idx++) {
            BodyPart bodyPart = multipart.getBodyPart(idx);
            log.debug(bodyPart.getContentType());
            if (bodyPart.isMimeType("text/plain")) {
                log.debug("plain.................\n{}", bodyPart.getContent());
            } else if (bodyPart.isMimeType("text/html")) {
                log.debug("html...................\n{}", bodyPart.getContent());
            } else if (bodyPart.isMimeType("multipart/*")) {
                Multipart mpart = (Multipart) bodyPart.getContent();
                parseMultipart(mpart, filePath);

            } else if (bodyPart.isMimeType("application/octet-stream")) {
                String disposition = bodyPart.getDisposition();
                if (disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
                    String fileName = bodyPart.getFileName();
                    fileName = MimeUtility.decodeText(fileName);
                    fileName = fileName.replaceAll("\\|", "-");
                    log.debug(Part.ATTACHMENT + "...................\n{}", fileName);
                    InputStream is = bodyPart.getInputStream();
                    String fileFullPath = filePath + File.separator + new String(fileName.getBytes("utf-8"));
                    log.debug(fileFullPath);
                    copy(is, fileFullPath);
                    return fileFullPath;
                }
            }
        }
        return null;
    }

    /**
     * createDir
     *
     * @param username qq
     */
    private static String createDir(String username) {
        String filePath = System.getProperty("user.home")
            + File.separator + "resume"
            + File.separator + username
            + File.separator + DateUtil.formatDate(DateUtil.date());
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return filePath;
    }

    /**
     * 文件拷贝，在用户进行附件下载的时候，可以把附件的InputStream传给用户进行下载
     */
    private static void copy(InputStream is, String fileFullPath) {
        BufferedReader reader = IoUtil.getReader(is, StandardCharsets.ISO_8859_1);
        BufferedOutputStream outputStream = FileUtil.getOutputStream(fileFullPath);
        OutputStreamWriter writer = IoUtil.getWriter(outputStream, StandardCharsets.ISO_8859_1);
        IoUtil.copy(reader, writer, NioUtil.DEFAULT_MIDDLE_BUFFER_SIZE);
        IoUtil.close(writer);
        IoUtil.close(outputStream);
        IoUtil.close(reader);
        IoUtil.close(is);
    }

    public static void main(String[] args) throws IOException {
//        System.setProperty("sun.jnu.encoding","UTF-8");
//        FileUtil.getOutputStream("C:\\Users\\11095\\resume\\1249709667@qq.com\\2021-05-29_10\\【项目助理 | 广州4-5K】罗镜标 2年.jpg");
//        String path = "C:\\Users\\11095\\resume\\1249709667@qq.com\\2021-06-17";
//        String name = "【HTML前端工程师 - 广州4-7K】陈剑峰 3年.pdf";

        String path = "/resume";
        String name = "【HTML前端工程师 - 广州4-7K】陈剑峰 3年.pdf";
        File file = new File(path + File.separator + name);
//        FileUtil.getOutputStream(file);
//        new FileOutputStream(file);

//        file.createNewFile();

//        new FileOutputStream(file);
        System.out.println(file.length());

        InputStream is = new FileInputStream(file);
        String fileFullPath = path + File.separator + ((int)(Math.random()*100)) + new String(name.getBytes("utf-8"));
        log.info(fileFullPath);
        copy(is, fileFullPath);
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void job() throws Exception {
        if (apiConfig.getBossConfig().getEmailWorking()) {
            LambdaQueryWrapper<BindFarmPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BindFarmPO::getPlatformType, PlatformEnum.BOSS_EMAIL.getCode());
            List<BindFarmPO> binds = bindFarmDao.selectList(queryWrapper);
            for (BindFarmPO bind : binds) {
                String cookie = bind.getCookie();
                log.info("[----- email job: {} -----", cookie);
                String[] split = cookie.split(";");
                List<EmailsPO> list = readEmail(split[0], split[1]);
                if (!CollectionUtils.isEmpty(list)) {
                    emailsService.saveBatch(list);
                }
            }
        }
    }

}
