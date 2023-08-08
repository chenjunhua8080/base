package com.cjh.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Voice {

    p11("p11", "zh-CN-YunxiNeural", "（男）云希 *"),
    p12("p12", "zh-CN-YunhaoNeural", "（男）云皓"),
    p13("p13", "zh-CN-YunjianNeural", "（男）云健"),
    p14("p14", "zh-CN-YunxiaNeural", "（男）云夏"),
    p15("p15", "zh-CN-YunfengNeural", "（男）云枫"),
    p16("p16", "zh-CN-YunyeNeural", "（男）云野"),
    p17("p17", "zh-CN-YunyangNeural", "（男）云杨"),
    p18("p18", "zh-CN-YunzeNeural", "（男）云泽"),

    p21("p21", "zh-CN-XiaochenNeural", "（女）晓辰 *"),
    p22("p22", "zh-CN-XiaohanNeural", "（女）晓涵"),
//    p23("p23", "zh-CN-XiaojianNeural", "（女性、儿童）晓健"),
//    p24("p24", "zh-CN-XiaojieNeural", "（女）晓洁"),
    p25("p25", "zh-CN-XiaomengNeural", "（女）晓梦"),
    p26("p26", "zh-CN-XiaomoNeural", "（女）晓墨"),
    p27("p27", "zh-CN-XiaoruiNeural", "（女）晓睿"),
    p28("p28", "zh-CN-XiaoshuangNeural", "（女性、儿童）晓双"),
    p29("p29", "zh-CN-XiaoxiaoNeural", "（女）晓晓"),
    p210("p210", "zh-CN-XiaoxuanNeural", "（女）晓渲"),
    p211("p211", "zh-CN-XiaoyanNeural", "（女）晓颜"),
    p212("p212", "zh-CN-XiaoyiNeural", "（女）晓伊"),
    p213("p213", "zh-CN-XiaoyouNeural", "（女性、儿童）晓悠"),
    p214("p214", "zh-CN-XiaozhenNeural", "（女）晓甄"),

    ;

    private String code;
    private String name;
    private String desc;

    public static Voice from(String code) {
        if (code == null) {
            return null;
        }
        for (Voice e : Voice.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        Voice e = from(code);
        return e == null ? "" : e.getName();
    }

    public static List<String> getCodeList() {
        return Arrays.stream(Voice.values()).map(Voice::getCode).collect(Collectors.toList());
    }

}
