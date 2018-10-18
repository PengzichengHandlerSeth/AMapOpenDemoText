package com.seth.amap.http;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2018/3/5.
 */

public class ResponseUtils {
    public  static String YZMREGIST = "0";
    public  static String YZMRCHANGE = "6";
    public  static String YZMRSET = "2";

    /**
     * 验证密码
     *
     * @param pass 密码
     * @return 结果
     */
    public static boolean isPassword(String pass) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{8,16}+$"); // 验证密码
        Matcher m = p.matcher(pass);
        return m.matches();
    }
//    public  static Boolean getresult(String resultcode){
//
//        if("-102".equals(resultcode)||"-103".equals(resultcode)||"-104".equals(resultcode)){
//            Intent mIntent = new Intent(AssetApplication.getContext(), LoginActivity.class);
//            mIntent.putExtra("logout","yes");
//            AssetApplication.getContext().startActivity(mIntent);
//            AssetApplication.getInstance().exit();
//            return false;
//        }else return true;
//
//    }

    public static Map<String, String> setHeaders(){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type","application/json");
        headers.put("Accept","application/json");
//        headers.put("Cookie",SharepreferenceUtils.getInstance().readShare("cookie").replace("[","").replace("]",""));
        return  headers;
    }



//    public void setObject(Object object) {
//        this.object = object;
//
//    }

    public static String getRequestBody(Object object) {
        String res = "";
        String str4="";
        String str5="";
        Field fields[] = object.getClass().getDeclaredFields();// 获得对象所有属性
        if (fields.length != 0) {
            for (int i = 0; i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true);
                    res += fields[i].getName();
                    res += ":";
                    res += fields[i].get(object);
                    res += ",";
                } catch (IllegalArgumentException | IllegalAccessException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
         /*   String sourceStrArray  =res.substring(0, res.length() - 1);
            String str[] =sourceStrArray.split(",");
            for (int j=0;j<str.length;j++){
             String s = str[j];
             String a[] = s.split(":");
                for (int k =0; k<a.length;k++){
                         JS.put(a[0],a[1]);
                }
            }*/
            String str = res.substring(0, res.length() - 1);
            JSONObject obj = new JSONObject();
            String[] str1 = str.split(","),str3;
            String str2;
            for (int a = 0;a<str1.length;a++) {
                str2 = str1[a];
                if (!"".contains(str2)) {
                    str3 = str2.split(":",-2);
                    if (!"".contains(str3[0])) {
                        try {
                            obj.put(str3[0], str3[1]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            obj.length();
            str4 = obj.toString();
            str5  =str4.substring(0,str4.length()-1);
            return str4;
        } else
            return str4;
    }
}
