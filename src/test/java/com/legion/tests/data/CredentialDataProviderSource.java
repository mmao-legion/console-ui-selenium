package com.legion.tests.data;

import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.utils.Constants;
import com.legion.utils.JsonUtil;
import com.legion.utils.SimpleUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.DataProvider;
import org.testng.util.Strings;
import static com.legion.utils.MyThreadLocal.*;


 public class CredentialDataProviderSource {
    private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

     @DataProvider(name = "legionTeamCredentialsByEnterprise", parallel = false)
    public static Object[][] credentialsByEnterprise (Method testMethod) {
        String fileName = "UsersCredentials.json";
        Object[][] credentials = null;
        fileName=SimpleUtils.getEnterprise(testMethod)+fileName;
        HashMap<String, ArrayList<String>> userCredentials = SimpleUtils
       		 .getEnvironmentBasedUserCredentialsFromJson(fileName);
        try{
            credentials = new Object[userCredentials.size()][];
        int index = 0;
        for(Map.Entry<String, ArrayList<String>> entry : userCredentials.entrySet())
        {
            credentials[index] =  entry.getValue().toArray();
            index = index + 1;
        }
        }catch(NullPointerException e){
           return new Object[0][];
        }
        return credentials;
    }
     
     @DataProvider(name = "legionTeamCredentialsByRoles", parallel = true)
     public static Object[][] credentialsByRoles (Method testMethod) {
         String fileName = "UsersCredentials.json";
         fileName=SimpleUtils.getEnterprise(testMethod)+fileName;
         int credentialsLength = 1;
         Object[][] credentials = new Object[credentialsLength][];         
         //ToDo -need to handle null userCredential map value
         HashMap<String, ArrayList<String>> userCredentials = SimpleUtils
        		 .getEnvironmentBasedUserCredentialsFromJson(fileName);
         int index = 0;
         for(Map.Entry<String, ArrayList<String>> entry : userCredentials.entrySet())
         {
             if(testMethod.getName().contains(entry.getKey()))
             {
                 credentials[index] =  entry.getValue().toArray();
             }
         } 
         return credentials;
     }
}