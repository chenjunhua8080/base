package com.cjh.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    r0("r0", "Narrator", "旁白"),

    r11("r11", "Boy", "男孩"),
    r12("r12", "YoungAdultMale", "年轻的成年男性"),
    r13("r13", "OlderAdultMale", "年长的成年男性"),
    r14("r14", "SeniorMale", "年老男性"),

    r21("r21", "Girl", "女孩"),
    r22("r22", "YoungAdultFemale", "年轻的成年女性"),
    r23("r23", "OlderAdultFemale", "年长的成年女性"),
    r24("r24", "SeniorFemale", "年老女性"),

    ;

    private String code;
    private String name;
    private String desc;

    public static Role from(String code) {
        if (code == null) {
            return null;
        }
        for (Role e : Role.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        Role e = from(code);
        return e == null ? "" : e.getName();
    }

    public static List<String> getCodeList() {
        return Arrays.stream(Role.values()).map(Role::getCode).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        String xml = "<speak version=\"1.0\"\n"
            + "    xmlns=\"http://www.w3.org/2001/10/synthesis\"\n"
            + "    xmlns:mstts=\"https://www.w3.org/2001/mstts\" xml:lang=\"zh-CN\">\n"
            + "\n"
            + "    <voice name=\"zh-CN-XiaochenNeural\">\n"
            + "        <prosody rate=\"20%\">大龙国某省的一所大学中，一名普通的大四的学生正在安睡，谁也想不到一件不平凡的事即将发生到她的身上，幽暗的房间神秘的声音响起，</prosody>\n"
            + "    </voice>\n"
            + "    <voice name=\"zh-CN-YunxiNeural\">\n"
            + "        <prosody rate=\"20%\">“刘唯唯，刘唯唯。” ，</prosody>\n"
            + "    </voice>\n"
            + "\n"
            + "    <voice name=\"zh-CN-YunxiNeural\">\n"
            + "        <prosody rate=\"20%\">“刘唯唯。” ，</prosody>\n"
            + "    </voice>\n"
            + "\n"
            + "    <voice name=\"zh-CN-YunxiNeural\">\n"
            + "        <prosody rate=\"20%\">“刘唯唯，你很幸运，你被我选中了。” ，</prosody>\n"
            + "    </voice>\n"
            + "\n"
            + "    <voice name=\"zh-CN-YunxiNeural\">\n"
            + "        <prosody rate=\"20%\">“没错，就是你，能在这个空间待24小时不骄不躁，足以说明你有着常人所不能及的耐心。” ，</prosody>\n"
            + "    </voice>\n"
            + "    <voice name=\"zh-CN-XiaochenNeural\">\n"
            + "        <prosody rate=\"20%\">“多少小时？！” 刘唯唯震惊的问道，</prosody>\n"
            + "    </voice>\n"
            + "    <voice name=\"zh-CN-YunxiNeural\">\n"
            + "        <prosody rate=\"20%\">“24小时。” 光球好脾气的重复到，</prosody>\n"
            + "    </voice>\n"
            + "    <voice name=\"zh-CN-XiaochenNeural\">\n"
            + "        <prosody rate=\"20%\">“24小时！” 刘唯唯站起身，脸上的表情诡异非常，一步一步的靠近光球，再接近的时候一把扑了过去，光球闪躲开，</prosody>\n"
            + "    </voice>\n"
            + "    <voice name=\"zh-CN-YunxiNeural\">\n"
            + "        <prosody rate=\"20%\">“你要干什么？” ，</prosody>\n"
            + "    </voice>\n"
            + "\n"
            + "    <voice name=\"zh-CN-YunxiNeural\">\n"
            + "        <prosody rate=\"20%\">“你放心，这个空间内的时间流速和外界的不同，你的四级考试还没错过。” ，</prosody>\n"
            + "    </voice>\n"
            + "    <voice name=\"zh-CN-XiaochenNeural\">\n"
            + "        <prosody rate=\"20%\">“真的！” 刘唯唯一脸惊喜的抬起头，“那你快送我回去，我还要考试呢？” ，</prosody>\n"
            + "    </voice>\n"
            + "</speak>";
        System.out.println(xml.length());
    }

}
