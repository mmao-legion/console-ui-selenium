package com.legion.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileUtils {

    public static String read(String filePath) {
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(filePath));
            String s;
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator()).append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException("close failed！");
                }
            }
        }
        return result.toString();
    }



    public static void write(String text, String filePath) {
        write(text, filePath, false);
    }


    public static void write(String text, String filePath, boolean isWriting) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath, isWriting);
            fw.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null)
                try {
                    fw.close();
                } catch (IOException e) {
                    throw new RuntimeException("close failed！");
                }
        }
    }
}
