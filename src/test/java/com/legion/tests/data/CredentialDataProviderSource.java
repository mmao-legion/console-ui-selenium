package com.legion.tests.data;

import com.legion.utils.SimpleUtils;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.testng.annotations.DataProvider;

public class CredentialDataProviderSource {

    @DataProvider(name = "legionTeamCredentialsByEnterprise", parallel = false)
    public static Object[][] firstCredentialsByEnterprise(Method testMethod) {
        String fileName = "UsersCredentials.json";
        fileName=SimpleUtils.getEnterprise(testMethod)+fileName;
        TreeMap<String, Object[][]> userCredentials = new TreeMap<>(); 
        userCredentials.putAll(SimpleUtils.getEnvironmentBasedUserCredentialsFromJson(fileName)); 
        for(Map.Entry<String, Object[][]> entry : userCredentials.entrySet())
        {
            return entry.getValue();
        }
        return new Object[0][];
        //return Constants.DefaultInternalAdminCredentials;
    }

     @DataProvider(name = "legionTeamCredentialsByEnterpriseP", parallel = true)
     public static Object[][] firstCredentialsByEnterpriseP (Method testMethod) {
         return firstCredentialsByEnterprise(testMethod);
     }
     
     @DataProvider(name = "legionTeamCredentialsByRoles", parallel = false)
     public static Object[][] credentialsByRoles (Method testMethod) {
         String fileName = "UsersCredentials.json";
         fileName=SimpleUtils.getEnterprise(testMethod)+fileName;
         HashMap<String, Object[][]> userCredentials = SimpleUtils
        		 .getEnvironmentBasedUserCredentialsFromJson(fileName);
         for(Map.Entry<String, Object[][]> entry : userCredentials.entrySet())
         {
             if(testMethod.getName().contains(entry.getKey()))
             {
                 return  entry.getValue();
             }
         }
         return new Object[0][];
        //return Constants.DefaultInternalAdminCredentials;
     }

     @DataProvider(name = "legionTeamCredentialsByRolesP", parallel = true)
     public static Object[][] credentialsByRolesP (Method testMethod) {
         return credentialsByRoles(testMethod);
     }
}
