package com.cjh.base.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjh.base.enums.AnswerEnum;
import com.cjh.base.po.QuestionBankPO;
import com.cjh.base.util.HttpUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JuHeApi {

    public static final String today_history = "http://v.juhe.cn/todayOnhistory/queryEvent.php?date=DATE&key=e90db0341047acc15edc10ee8458f880";

    public static final String rand_joke = "http://v.juhe.cn/joke/randJoke.php?key=938da9ec9cc43d7aebf5d28bfe91e68f";

    public static final String simple_weadther = "http://apis.juhe.cn/simpleWeather/query?city=CITY&key=cb36aed6af10cfac7a20f9208c6ba14d";

    public static final String constellation = "http://web.juhe.cn:8080/constellation/getAll?consName=NAME&type=week&key=eddce9423c555147abc43a740f299431";

    public static final String question_bank = "http://v.juhe.cn/jztk/query?subject=1&model=c1&testType=&=&key=01b8d30914c2e0c78205b4775ab125dc";

    public static final String question_answers = "http://v.juhe.cn/jztk/answers?key=01b8d30914c2e0c78205b4775ab125dc";

    public String getTodayHistory() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d");
        String url = today_history.replace("DATE", dateFormat.format(new Date()));
        JSONObject resp = JSONObject.parseObject(HttpUtil.doGet(url));
        int errorCode = resp.getIntValue("error_code");
        StringBuilder result = new StringBuilder();
        if (errorCode == 0) {
            JSONArray data = resp.getJSONArray("result");
            for (Object item : data) {
                JSONObject day = (JSONObject) item;
                result.append(day.getString("date")).append("：").append(day.getString("title")).append("\n");
            }
        } else {
            result = new StringBuilder(resp.getString("reason"));
        }
        return result.toString();
    }

    public String getRandJoke() {
        String url = rand_joke;
        JSONObject resp = JSONObject.parseObject(HttpUtil.doGet(url));
        int errorCode = resp.getIntValue("error_code");
        String result = "";
        if (errorCode == 0) {
            JSONArray data = resp.getJSONArray("result");
            JSONObject joke = (JSONObject) data.get(0);
            result = joke.getString("content");
        } else {
            result = resp.getString("reason");
        }
        return result;
    }

    public String getSimpleWeadther(String city) {
        String url = simple_weadther.replace("CITY", city);
        JSONObject resp = JSONObject.parseObject(HttpUtil.doGet(url));
        int errorCode = resp.getIntValue("error_code");
        String result;
        if (errorCode == 0) {
            JSONObject data = resp.getJSONObject("result");
            JSONObject realtime = data.getJSONObject("realtime");
            String info = realtime.getString("info");
            String temperature = realtime.getString("temperature");
            result = info + "，" + temperature + "°C";
        } else {
            result = resp.getString("reason");
        }
        return result;
    }

    public String getConstellation(String name) {
        String url = constellation.replace("NAME", name);
        JSONObject resp = JSONObject.parseObject(HttpUtil.doGet(url));
        int errorCode = resp.getIntValue("error_code");
        String result = "";
        if (errorCode == 0) {
            String love = resp.getString("love");
            String money = resp.getString("money");
            String work = resp.getString("work");
            result = name + "\n\n" + love + "\n\n" + money + "\n\n" + work;
        } else {
            result = resp.getString("reason");
        }
        return result;
    }

    public QuestionBankPO getQuestionBank() {
        String url = question_bank;
        JSONObject resp = JSONObject.parseObject(HttpUtil.doGet(url));
        int errorCode = resp.getIntValue("error_code");
        if (errorCode == 0) {
            JSONArray jsonArray = resp.getJSONArray("result");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String item3 = jsonObject.getString("item3");
            String answer = jsonObject.getString("answer");
            if (item3 == null || item3.equals("")) {
                answer = AnswerEnum.getNameByCode(Integer.parseInt(answer));
                answer = answer.substring(answer.length() - 2);
            } else {
                answer = AnswerEnum.getNameByCode(Integer.parseInt(answer));
                answer = answer.substring(0, 1);
            }
            jsonObject.put("answer", answer);
            return JSONObject.parseObject(jsonObject.toJSONString(), QuestionBankPO.class);
        }
        return null;
    }

    public JSONObject getQuestionAnswers() {
        String url = question_answers;
        JSONObject resp = JSONObject.parseObject(HttpUtil.doGet(url));
        int errorCode = resp.getIntValue("error_code");
        if (errorCode == 0) {
            return resp.getJSONObject("result");
        }
        return null;
    }

}
