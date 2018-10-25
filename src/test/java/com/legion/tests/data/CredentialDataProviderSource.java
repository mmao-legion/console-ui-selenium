package com.legion.tests.data;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.DataProvider;
import org.testng.util.Strings;

import com.legion.tests.annotations.Enterprise;
import com.legion.utils.Constants;
import com.legion.utils.JsonUtil;

public class CredentialDataProviderSource {
	
	private static HashMap<String, String> propertyMap = JsonUtil.getPropertiesFromJsonFile("src/test/resources/envCfg.json");

    @DataProvider(name = "legionTeamCredentials", parallel = true)
    public static Object[][] credentialsByRole (Method testMethod) {
        if (testMethod.getName().contains("InternalAdmin")) {
            return new Object[][] { {"Chrome", "admin1.a", "admin1.a", "Carmel Club" }, {"Chrome", "admin2.a", "admin2.a", "Legion Coffee Mock Store" }};
        }
        else if (testMethod.getName().contains("NoneStoreManager")) {
            return new Object[][] { {"Chrome", "Paris.P", "Paris.P", "Carmel Club" }, {"Chrome", "Dario.D", "Dario.D", "Legion Coffee Mock Store"}};
        }
        else {
            return JsonUtil.getArraysFromJsonFile("src/test/resources/UsersCredentials.json");

        }
    }

    @DataProvider(name = "legionTeamCredentialsByEnterprise", parallel = true)
    public static Object[][] credentialsByEnterprise (Method testMethod) {
        String fileName = "UsersCredentials.json";
        Enterprise e = testMethod.getAnnotation(Enterprise.class);
        if (testMethod.getName().contains(Constants.SalesSunday15Slot) ||
            e != null && !Strings.isNullOrEmpty(e.name()) &&
                e.name().equalsIgnoreCase(Constants.SalesSunday15Slot) ) {
            String name = propertyMap.get(Constants.SalesSunday15Slot);
            fileName=name != null ? name+fileName : propertyMap.get(Constants.DefaultEnterpriseSalesMonday30Slot)+fileName;
        }
        return JsonUtil.getArraysFromJsonFile("src/test/resources/" + fileName);
    }

}
