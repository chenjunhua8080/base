package com.cjh.common.boss;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.cjh.common.api.ApiConfig;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
import lombok.extern.slf4j.Slf4j;

/**
 * 邮件服务，批量下载简历
 *
 * @author cjh
 * @date 2021/4/26
 */
@Slf4j
public class EmailService {

    private static final ExecutorService executor = ExecutorBuilder.create()
        .setCorePoolSize(2)
        .setMaxPoolSize(2)
        .setKeepAliveTime(0)
        .setWorkQueue(new LinkedBlockingQueue<>(1000))
        .build();

    public static Map<String, Object> getResumeZip(String emailAccount, String auth) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
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
        log.info("未读数量: {}", n);

        //createDir
        String filePath = createDir(emailAccount);
        log.info(filePath);

        //简历数量
        AtomicInteger resumeCount = new AtomicInteger(0);

        //查找未读的邮件
        FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false); // false代表未读，true代表已读
        Message[] messages = folder.search(ft);
        CountDownLatch countDownLatch = ThreadUtil.newCountDownLatch(messages.length);
        for (Message message : messages) {
            String subject = message.getSubject();// 获得邮件主题
            Address from = message.getFrom()[0];// 获得发送者地址
            if (!subject.replaceAll(apiConfig.getBossConfig().getSubjectMatch(), "666~").contains("666~")) {
                log.warn("邮件内容不是简历: {}", subject);
                message.setFlag(Flags.Flag.SEEN, false);     //imap读取后邮件状态会变为已读,设为未读
                continue;
            }
            log.info("邮件的主题为: {}", subject);
            log.info("发件人地址为: {}", decodeText(from.toString()));
            log.info("日期: {}", message.getSentDate());
//            Enumeration headers = message.getAllHeaders();
//              log.info("----------------------allHeaders-----------------------------");
//              while (headers.hasMoreElements()) {
//                     Header header = (Header)headers.nextElement();
//                     log.info(header.getName()+" ======= "+header.getValue());
//                     log.info("----------------------------");
//                 }
            executor.execute(() -> {
                try {
                    if (parseMultipart((Multipart) message.getContent(), filePath)) {
                        resumeCount.getAndAdd(1);
                    }
                    message.setFlag(Flags.Flag.SEEN, true);     //imap读取后邮件状态会变为已读,设为未读
                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        folder.close(false);// 关闭邮件夹对象
        store.close(); // 关闭连接对象

        //压缩附件
        if (resumeCount.get() > 0) {
            File zip = ZipUtil.zip(filePath, filePath + "_共" + resumeCount + "份.zip");
            log.info(zip.getAbsolutePath());
            map.put("link", "http://resume.springeasy.cn/"
                + URLEncoder.createDefault().encode(zip.getName(), StandardCharsets.UTF_8));
        }
        map.put("count", resumeCount.get());

        return map;
    }

    protected static String decodeText(String text) throws UnsupportedEncodingException {
        if (text == null) {
            return null;
        }
        if (text.startsWith("=?GB") || text.startsWith("=?gb")) {
            text = MimeUtility.decodeText(text);
        } else {
            text = new String(text.getBytes(StandardCharsets.ISO_8859_1));
        }
        return text;
    }

    /**
     * 对复杂邮件的解析
     * 返回 true 就是下载到附件
     */
    private static boolean parseMultipart(Multipart multipart, String filePath) throws MessagingException, IOException {
        int count = multipart.getCount();
        log.info("count =  {}", count);
        for (int idx = 0; idx < count; idx++) {
            BodyPart bodyPart = multipart.getBodyPart(idx);
            log.info(bodyPart.getContentType());
            if (bodyPart.isMimeType("text/plain")) {
                log.info("plain.................\n{}", bodyPart.getContent());
            } else if (bodyPart.isMimeType("text/html")) {
                log.info("html...................\n{}", bodyPart.getContent());
            } else if (bodyPart.isMimeType("multipart/*")) {
                Multipart mpart = (Multipart) bodyPart.getContent();
                parseMultipart(mpart, filePath);

            } else if (bodyPart.isMimeType("application/octet-stream")) {
                String disposition = bodyPart.getDisposition();
                if (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT)) {
                    String fileName = bodyPart.getFileName();
                    log.info(BodyPart.ATTACHMENT + "...................\n{}", fileName);
                    InputStream is = bodyPart.getInputStream();
                    String fileFullPath = filePath + File.separator + fileName;
                    log.info(fileFullPath);
                    copy(is, fileFullPath);
                    return true;
                }
            }
        }
        return false;
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

    /**
     * 文件拷贝，在用户进行附件下载的时候，可以把附件的InputStream传给用户进行下载
     */
    private static void copy(InputStream is, String fileFullPath) throws IOException {
        try (FileOutputStream os = new FileOutputStream(fileFullPath)) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
