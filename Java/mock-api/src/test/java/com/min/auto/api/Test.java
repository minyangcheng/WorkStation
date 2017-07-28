package com.min.auto.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.min.auto.api.util.GsonUtil;

/**
 * Created by minyangcheng on 2017/7/28.
 */

public class Test {

    @org.junit.Test
    public void testGson(){
        String s="{\n" +
                "    \"code\": 10000,\n" +
                "    \"data\": {\n" +
                "        \"currentNodeKey\": \"LOAN_MODIFY_LAUNCH\",\n" +
                "        \"bopInfoId\": 200408,\n" +
                "        \"businessGroupId\": 490,\n" +
                "        \"loanApplyId\": 257376,\n" +
                "        \"businessTypeCode\": \"LOAN_MODIFY_FLOW\"\n" +
                "    }\n" +
                "}";
        JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
        if(jsonObject.has("flowType1")){
            System.out.println("flowType");
        }
        System.out.println(jsonObject.toString());
    }

}
