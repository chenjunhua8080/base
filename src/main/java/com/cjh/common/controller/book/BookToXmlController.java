package com.cjh.common.controller.book;

import com.baomidou.mybatisplus.extension.api.R;
import com.cjh.common.dao.BookToXmlConfigDto;
import com.cjh.common.dao.BookToXmlConfigV2Dto;
import com.cjh.common.dao.BookToXmlConfigV2Dto.BookToXmlConfigV2Item;
import com.cjh.common.enums.Role;
import com.cjh.common.enums.Style;
import com.cjh.common.enums.Voice;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book/to/xml")
public class BookToXmlController {

    private final static String DEFAULT_VOICE = "p21";

    /**
     * 常见的文本转语音配额和限制
     * Quota	免费 (F0)	标准 (S0)
     * 预生成神经语音的每个时间段的事务数上限。	每 60 秒 20 个事务
     * <p>
     * 此限制不可调整。	每秒 200 个事务 (TPS)（默认值）
     * <p>
     * 对于标准 (S0) 资源，此限制可上调至 1000 TPS。 请参阅其他说明、最佳做法和调整说明。
     * 每个请求已生成的最大音频长度	10 分钟	10 分钟
     * SSML 中非重复 <voice> 和 <audio> 标记的最大总数	50	50
     * WebSocket 每轮的最大 SSML 消息大小	64 KB	64 KB
     */
    @PostMapping
    public R<String> convert(@RequestBody @RequestParam("text") String text, @RequestParam("speed") String speed) {
        if (text == null) {
            return R.failed("text 不能为空");
        }
        if (speed == null) {
            return R.failed("speed 不能为空");
        }
        StringBuilder xml = new StringBuilder();

        //speak start
        xml.append("<speak version=\"1.0\" xmlns=\"http://www.w3.org/2001/10/synthesis\"\n"
            + "       xmlns:mstts=\"https://www.w3.org/2001/mstts\" xml:lang=\"zh-CN\">");

        boolean inVoice = false;
        boolean inStyle = false;
        List<String> codeList = Voice.getCodeList();
        List<String> codeAndStyleList = codeList.stream().map(s -> s + ":").collect(Collectors.toList());
        String[] array = text.split("\n");
        for (String s : array) {
            if (s.isEmpty()) {
                continue;
            }
            s = s.trim();
            if (inVoice) {
                //判断是否新角色
                if (codeList.contains(s)) {
                    //voice end
                    xml.append("</prosody>" + (inStyle ? "</mstts:express-as>" : "") + "</voice>");
                    //voice start
                    xml.append("<voice name=\"" + Voice.from(s).getName() + "\">" + "<prosody rate=\"" + speed + "\">");
                    inStyle = false;
                    continue;
                }
                //判断是否新角色，带语气和角色
                if (isContains(s, codeAndStyleList)) {
                    String[] split = s.split(":");
                    //voice end
                    xml.append("</prosody>" + (inStyle ? "</mstts:express-as>" : "") + "</voice>");
                    //voice start
                    if (split.length == 2) {
                        xml.append("<voice name=\"" + Voice.from(split[0]).getName() + "\">"
                            + "<mstts:express-as styledegree=\"1.5\" style=\"" + Style.from(split[1]).getName() + "\">"
                            + "<prosody rate=\"" + speed + "\">");
                    } else {
                        xml.append("<voice name=\"" + Voice.from(split[0]).getName() + "\">"
                            + "<mstts:express-as styledegree=\"1.5\" style=\"" + Style.from(split[1]).getName()
                            + "\" role=\"" + Role.from(split[2]).getName() + "\">"
                            + "<prosody rate=\"" + speed + "\">");
                    }
                    inStyle = true;
                    continue;
                }
                xml.append(s + "，");
            } else {
                //判断是否设置角色
                if (codeList.contains(s)) {
                    //voice start
                    xml.append("<voice name=\"" + Voice.from(s).getName() + "\">" + "<prosody rate=\"" + speed + "\">");
                    inVoice = true;
                    continue;
                } else if (isContains(s, codeAndStyleList)) {
                    //判断是否新角色，带语气和角色
                    String[] split = s.split(":");
                    //voice start
                    if (split.length == 2) {
                        xml.append("<voice name=\"" + Voice.from(split[0]).getName() + "\">"
                            + "<mstts:express-as styledegree=\"1.5\" style=\"" + Style.from(split[1]).getName() + "\">"
                            + "<prosody rate=\"" + speed + "\">");
                    } else {
                        xml.append("<voice name=\"" + Voice.from(split[0]).getName() + "\">"
                            + "<mstts:express-as styledegree=\"1.5\" style=\"" + Style.from(split[1]).getName()
                            + "\" role=\"" + Role.from(split[2]).getName() + "\">"
                            + "<prosody rate=\"" + speed + "\">");
                    }
                    inStyle = true;
                    inVoice = true;
                    continue;
                } else {
                    //设置默认角色
                    //voice start
                    xml.append(
                        "<voice name=\"" + Voice.from(DEFAULT_VOICE).getName() + "\">" + "<prosody rate=\"" + speed
                            + "\">");
                }
                xml.append(s + "，");
                inVoice = true;
            }
        }

        //voice end
        xml.append("</prosody>" + (inStyle ? "</mstts:express-as>" : "") + "</voice>");
        //speak end
        xml.append("</speak>");
        return R.ok(xml.toString());
    }

    private static boolean isContains(String s, List<String> codeAndStyleList) {
        for (String item : codeAndStyleList) {
            if (s.startsWith(item)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/getConfig")
    public R<BookToXmlConfigDto> getConfig() {
        BookToXmlConfigDto dto = new BookToXmlConfigDto();
        dto.setVoiceList(Arrays.stream(Voice.values()).map(item -> item.getCode() + ":" + item.getDesc())
            .collect(Collectors.toList()));
        dto.setStyleList(Arrays.stream(Style.values()).map(item -> item.getCode() + ":" + item.getDesc())
            .collect(Collectors.toList()));
        dto.setRoleList(Arrays.stream(Role.values()).map(item -> item.getCode() + ":" + item.getDesc())
            .collect(Collectors.toList()));
        return R.ok(dto);
    }

    @GetMapping("/getConfig/v2")
    public R<BookToXmlConfigV2Dto> getConfigV2() {
        BookToXmlConfigV2Dto dto = new BookToXmlConfigV2Dto();
        List<BookToXmlConfigV2Item> voiceList = Lists.newArrayList();
        List<BookToXmlConfigV2Item> styleList = Lists.newArrayList();
        List<BookToXmlConfigV2Item> roleList = Lists.newArrayList();
        for (Voice item : Voice.values()) {
            voiceList.add(new BookToXmlConfigV2Item(item.getName(), item.getDesc()));
        }
        for (Style item : Style.values()) {
            styleList.add(new BookToXmlConfigV2Item(item.getName(), item.getDesc()));
        }
        for (Role item : Role.values()) {
            roleList.add(new BookToXmlConfigV2Item(item.getName(), item.getDesc()));
        }
        dto.setVoiceList(voiceList);
        dto.setStyleList(styleList);
        dto.setRoleList(roleList);
        return R.ok(dto);
    }

}
