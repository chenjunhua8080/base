package com.cjh.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Voice {

//    zh-CN-XiaochenNeural（女）晓辰 *
//    zh-CN-XiaohanNeural（女）晓涵
//    zh-CN-XiaojianNeural（女性、儿童）晓健
//    zh-CN-XiaojieNeural（女）晓洁
//    zh-CN-XiaomengNeural（女）晓梦
//    zh-CN-XiaomoNeural（女）晓墨
//    zh-CN-XiaoruiNeural（女）晓睿
//    zh-CN-XiaoshuangNeural（女性、儿童）晓双
//    zh-CN-XiaoxiaoNeural（女）晓晓
//    zh-CN-XiaoxuanNeural（女）晓渲
//    zh-CN-XiaoyanNeural（女）晓颜
//    zh-CN-XiaoyiNeural（女）晓伊
//    zh-CN-XiaoyouNeural（女性、儿童）晓悠
//    zh-CN-XiaozhenNeural（女）晓甄
//    zh-CN-YunfengNeural（男）云枫
//    zh-CN-YunhaoNeural（男）云皓
//    zh-CN-YunjianNeural（男）云健
//    zh-CN-YunxiaNeural（男）云夏
//    zh-CN-YunxiNeural（男）云希 *
//    zh-CN-YunyeNeural（男）云野
//    zh-CN-YunyangNeural（男）云杨
//    zh-CN-YunzeNeural（男）云泽

    Xiaochen("g1", "zh-CN-XiaochenNeural", "（女）晓辰 *"),
    Xiaohan("g2", "zh-CN-XiaohanNeural", "（女）晓涵"),
    Xiaojian("g3", "zh-CN-XiaojianNeural", "（女性、儿童）晓健"),
    Xiaojie("g4", "zh-CN-XiaojieNeural", "（女）晓洁"),
    Xiaomeng("g5", "zh-CN-XiaomengNeural", "（女）晓梦"),
    Xiaomo("g6", "zh-CN-XiaomoNeural", "（女）晓墨"),
    Xiaorui("g7", "zh-CN-XiaoruiNeural", "（女）晓睿"),
    Xiaoshuang("g8", "zh-CN-XiaoshuangNeural", "（女性、儿童）晓双"),
    Xiaoxiao("g9", "zh-CN-XiaoxiaoNeural", "（女）晓晓"),
    Xiaoxuan("g10", "zh-CN-XiaoxuanNeural", "（女）晓渲"),
    Xiaoyan("g11", "zh-CN-XiaoyanNeural", "（女）晓颜"),
    Xiaoyi("g12", "zh-CN-XiaoyiNeural", "（女）晓伊"),
    Xiaoyou("g13", "zh-CN-XiaoyouNeural", "（女性、儿童）晓悠"),
    Xiaozhen("g14", "zh-CN-XiaozhenNeural", "（女）晓甄"),

    Yunxi("b1", "zh-CN-YunxiNeural", "（男）云希 *"),
    Yunhao("b2", "zh-CN-YunhaoNeural", "（男）云皓"),
    Yunjian("b3", "zh-CN-YunjianNeural", "（男）云健"),
    Yunxia("b4", "zh-CN-YunxiaNeural", "（男）云夏"),
    Yunfeng("b5", "zh-CN-YunfengNeural", "（男）云枫"),
    Yunye("b6", "zh-CN-YunyeNeural", "（男）云野"),
    Yunyang("b7", "zh-CN-YunyangNeural", "（男）云杨"),
    Yunze("b8", "zh-CN-YunzeNeural", "（男）云泽"),

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
