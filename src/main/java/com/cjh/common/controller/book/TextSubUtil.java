package com.cjh.common.controller.book;

public class TextSubUtil {

    public static void main(String[] args) {
        String novel = "\n"
            + "\n"
            + "身处末世，么爬滚打了四坤年的你在基地遭到背叛，如今你竟虫回末到世前半个月！\n"
            + "宝子们赶紧艾特你的大冤种一起进入盛夏基地，\n"
            + "书接上文，\n"
            + "\n"
            + "\n"
            + "那群人的皮卡车后斗里一共有二十个大纸壳箱\n"
            + "里面有十满箱泡面，五箱大米，两箱饮用水，一箱各种肉类菜类，还有一箱子的黄金\n"
            + "连价签都没有摘！\n"
            + "一看就是他们路过金店的时候特意抢的\n"
            + "你看到这满满当当，足有三四十斤的黄金真是由衷感慨，这人对黄金的喜爱还真是刻在了骨子里\n"
            + "不过他们带黄金是正确的，就算是在末世，有流通价值的也只有黄金\n"
            + "你把这些物资分门别类送进自己的仓库，此外你还缴获了一支手枪\n"
            + "是警用型号的\n"
            + "看样子这群暴徒肯定是搜刮了不该搜刮的地方\n"
            + "你觉得自己真是一个为民除害的‘好人’\n"
            + "毕竟像你这样充满正义感的热心群众已经不多了，正常人遇到这群暴民哪个不是赶紧跑的，唯恐避之不及，只有你，除了上去英勇反击，打退敌人，还很贴心的送了他们一套丧尸套餐\n"
            + "不让他们去祸害其他人\n"
            + "要不然你大可以只把他们吓跑，赶出自己的领地就行\n"
            + "哪里用得着诱敌深入，非致他们死地\n"
            + "像你这么坏，呸，这么正义的‘好人’，已经很少见了\n"
            + "这些就当给你见义勇为的奖励吧\n"
            + "要不然你都看不下去了！\n"
            + "你欣然接受了自己的战利品\n"
            + "就在这时，你脑海响起了系统的提示音\n"
            + "“滴——检测到基地保卫战乘1”";
        int pageSize = 200; // 定义200个字符断句
        int startIndex = 0; // 从0开始
        int endIndex = pageSize;
        int maxLength = novel.length();

        while (true){
            int newEndIndex = findEndIndex(novel, endIndex);
            String sentence = novel.substring(startIndex, newEndIndex);
            System.out.println(sentence);
//            System.out.println("新的结束索引：" + newEndIndex);

            if (endIndex == maxLength){
                System.out.println("索引已到文本末：" + newEndIndex);
                break;
            }

            startIndex = newEndIndex;
            endIndex = Math.min(endIndex + pageSize, maxLength);
        }

    }

    public static int findEndIndex(String text, int endIndex) {
        int newEndIndex = endIndex;
        while (newEndIndex < text.length()) {
            char currentChar = text.charAt(newEndIndex);
            if (isSentenceDelimiter(currentChar)) {
                break;
            }
            newEndIndex++;
        }
        return newEndIndex;
    }

    private static boolean isSentenceDelimiter(char c) {
        // 判断字符是否为句子分隔符，例如句号、感叹号、问号、分号等
        return c == '。' || c == '！' || c == '？' || c == '；' || c == '，' || c == '：' || c == '、' || c == '\n';
    }
}
