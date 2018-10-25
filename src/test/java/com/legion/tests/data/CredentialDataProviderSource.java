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
     @DataProvider(name = "legionTeamCredentials", parallel = true)
    public static Object[][] credentialsByRole (Method testMethod) {
        if (testMethod.getName().contains("InternalAdmin")) {
            return new Object[][] { {"Chrome", "admin1.a", "admin1.a" }, {"Chrome", "admin2.a", "admin2.a" }};
        }
        else if (testMethod.getName().contains("NoneStoreManager")) {
            return new Object[][] { {"Chrome", "Paris.P", "Paris.P" }, {"Chrome", "Dario.D", "Dario.D" }};
        }
        else {
            return JsonUtil.getArraysFromJsonFile("src/test/resources/UsersCredentials.json");
         }
    }
     
     @DataProvider(name = "legionTeamCredentialsByEnterprise", parallel = true)
    public static Object[][] credentialsByEnterprise (Method testMethod) {
        String fileName = "UsersCredentials.json";
        Enterprise e = testMethod.getAnnotation(Enterprise.class);
        if (testMethod.getName().contains(Constants.EnterpriseSalesMonday30Slot) ||
            e != null && !Strings.isNullOrEmpty(e.name()) &&
                e.name().equalsIgnoreCase(Constants.EnterpriseSalesMonday30Slot) ) {
            String name = propertyMap.get(Constants.EnterpriseSalesMonday30Slot);
            fileName=name != null ? name+fileName : Constants.DefaultEnterpriseSalesMonday30Slot+fileName;
        }
        return JsonUtil.getArraysFromJsonFile("src/test/resources/" + fileName);
    }
     
     /*
      * Added By Naval
      */
     
     @DataProvider(name = "legionTeamCredentialsByRoles", parallel = true)
     public static Object[][] credentialsByRoles (Method testMethod) {
         String fileName = "UsersCredentials.json";
         int credentialsLength = 1;
         Object[][] credentials = new Object[credentialsLength][];
         Enterprise enterprise = testMethod.getAnnotation(Enterprise.class);
         if (enterprise != null && !Strings.isNullOrEmpty(enterprise.name()) ) {
             String name = propertyMap.get(enterprise.name());
             fileName=name != null ? name+fileName : propertyMap.get(Constants.DefaultEnterpriseSalesMonday30Slot)+fileName;
             HashMap<String, ArrayList<String>> userCredentials = SimpleUtils
            		 .getEnvironmentBasedUserCredentialsFromJson(fileName);
             int index = 0;
             for(Map.Entry<String, ArrayList<String>> entry : userCredentials.entrySet())
             {
                 if(testMethod.getName().contains(entry.getKey()))
                 {
                     credentials[index] =  entry.getValue().toArray();
                     index = index + 1;
                 }
             } 
         }
         return credentials;
     }
}