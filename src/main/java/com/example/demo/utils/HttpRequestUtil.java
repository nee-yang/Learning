//package com.example.demo.utils;
//
//import com.alibaba.fastjson.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**create by frank
// * on 2017/12/11
// */
//public class aHttpRequestUtil {
//
//    public static JSONObject getRep(String urlLine) throws MalformedURLException {
//
//        URL url = new URL(urlLine);
//        String dataLine ="";
//        JSONObject jsonObject = new JSONObject();
//        try {
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setConnectTimeout(2000);
//            httpURLConnection.setReadTimeout(2000);
//            BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
//            String line = null;
//            while ((line = inputStreamReader.readLine()) != null) {
//                dataLine += line.toString();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (dataLine != null)
//            jsonObject =JSONObject.parseObject(dataLine);
//        else
//            jsonObject = null;
//
//        return jsonObject;
//    }
//
//    public static void main(String[] args) {
//
//
//        try {
//            System.out.println(HttpRequestUtil.getRep("http://localhost:8080/test/one"));
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
