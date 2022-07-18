package com.legion.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Yanming
 */
public class JsonUtil {


        public static HashMap< String,String> getPropertiesFromJsonFile(String pathname) {
            HashMap< String,String> parameterList = null;

            ObjectMapper mapper = new ObjectMapper();

            try {
                parameterList = mapper.readValue(new File(pathname),
                        new TypeReference<HashMap< String,String>>() {
                        });

            } catch (JsonGenerationException e) {
                System.err.println("The json configuration file is not valid. Please verify the file.");

            } catch (JsonMappingException  e) {
                System.err.println("The json configuration file is not valid. Please verify the file.");

            } catch (IOException e) {
                System.err.println("No configuration file available. Cannot Find file: " + pathname);
            }
            return parameterList;

        }

    public static Object[][] getArraysFromJsonFile(String pathname) {

        Object[][] objects = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            objects = mapper.readValue(new File(pathname),
                    new TypeReference<Object[][]>() {
                    });

        } catch (JsonGenerationException e) {
            System.err.println("The json configuration file is not valid. Please verify the file.");

        } catch (JsonMappingException  e) {
            System.err.println("The json configuration file is not valid. Please verify the file.");

        } catch (IOException e) {
            System.err.println("No configuration file available. Cannot Find file: " + pathname);
        }
        return objects;
    }
    
    /*
     * Added by Naval
     */
    
    public static HashMap< String,Object[][]> getCredentialsFromJsonFile(String pathname) {
        HashMap< String,Object[][]> parameterList = new HashMap< String,Object[][]>();

        ObjectMapper mapper = new ObjectMapper();

        try {
            parameterList = mapper.readValue(new File(pathname),
                    new TypeReference<HashMap< String,Object[][]>>() {
                    });

        } catch (JsonGenerationException e) {
            System.err.println("The json configuration file is not valid. Please verify the file."+pathname);

        } catch (JsonMappingException  e) {
            System.err.println("The json configuration file is not valid. Please verify the file."+pathname);

        } catch (IOException e) {
            System.err.println("No configuration file available. Cannot Find file: " + pathname);
        }
        return parameterList;

    }
    
    public static ArrayList<HashMap< String,String>> getArrayOfMapFromJsonFile(String pathname) {
    	ArrayList<HashMap< String,String>> parameterList = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            parameterList = mapper.readValue(new File(pathname),
                    new TypeReference<ArrayList<HashMap< String,String>>>() {
                    });

        } catch (JsonGenerationException e) {
            System.err.println("The json configuration file is not valid. Please verify the file.");

        } catch (JsonMappingException  e) {
            System.err.println("The json configuration file is not valid. Please verify the file.");

        } catch (IOException e) {
            System.err.println("No configuration file available. Cannot Find file: " + pathname);
        }
        return parameterList;

    }

    /**
     * 解析Json内容
     *
     * @return JsonValue 返回JsonObject中Key对应的Value
     * @author Sophia
     * @version 1.0 2022/01/13
     **/
    public static String getJsonValue(String t, String key) {
        //解析数据
        JSONObject jsonObject = JSONObject.parseObject(t);//如果传入的参数t 是string 类型的话，先将其转换成JSONObject
        String jsonValue = jsonObject.getString(key);
        return jsonValue;
    }

    public static String getJsonObjectValue(String t, String objectName, String keyString) {
        //解析数据
        JSONObject jsonObject = JSONObject.parseObject(t);//如果传入的参数t 是string 类型的话，先将其转换成JSONObject
        JSONObject jsonData = jsonObject.getJSONObject(objectName);//用getJSONObject（"name"）获取外层数据
        //然后通过getString进行读值即可
        String jsonValue = jsonData.getString(keyString);
        return jsonValue;
    }

    public static String[] getJsonArrayValue(String t, String objectName, String arrayName) {
        //解析数据
        JSONObject jsonObject = JSONObject.parseObject(t);//如果传入的参数t 是string 类型的话，先将其转换成JSONObject        JSONObject jsonData = jsonObject.getJSONObject(objectName);//用getJSONObject（"name"）获取外层数据
        JSONObject jsonData = jsonObject.getJSONObject(objectName);//用getJSONObject（"name"）获取外层数据
        //然后通过getJSONArray获得这个array的数据
        JSONArray jsonArray = jsonData.getJSONArray(arrayName);

        String[] jsonNewArray = new String[20];//数组长度声明为20确保够用
        int j = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject arrayJSONObject = jsonArray.getJSONObject(i);//json object in the array
            Iterator iterator = (Iterator) arrayJSONObject.keySet();
            String key;
            while (iterator.hasNext()) {
                //hasNext方法，只是判断下一个元素的有无，并不移动指针
                key = (String) iterator.next();//next方法，向下移动指针，并且返回指针指向的元素，如果指针指向的内存中没有元素，会报异常
                jsonNewArray[j] = arrayJSONObject.getString(key);//获取value
                j++;
            }
        }
        return jsonNewArray;
    }

}
