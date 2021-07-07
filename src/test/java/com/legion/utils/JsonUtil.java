package com.legion.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
}
