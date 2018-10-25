package com.legion.tests.core;

import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class MyTest extends TestBase {

    @Override
    @BeforeMethod
    public  void  firstTest (Method method, Object[] params) throws Exception
    {
        String classname = getClass().getSimpleName();
        String methodName = method.getName();
        System.out.println("Running "+ classname + "." + methodName + "  before test");
        String paramsList = Arrays.asList(params).toString();
        System.out.println((String)params[0]+","+(String)params[1]+","+(String)params[2]);
        this.createDriver((String)params[0],"68","Mac");
        visitPage(method);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2]);

    }

    @Automated(automated = "Manual")
    @Owner(owner = "Gunjan")
    @Test(dataProvider = "legionTeamCredentials", dataProviderClass=CredentialDataProviderSource.class)
    public void exampleTestInternalAdmin(String browser, String username,  String  password)  throws Exception
    {
        SimpleUtils.pass( username + ":" + password);

    }

    @Automated(automated = "Manual")
    @Owner(owner = "Gunjan")
    @Enterprise(name = "Coffee_Enterprise")
    @Test(dataProvider = "legionTeamCredentialsByEnterprise", dataProviderClass=CredentialDataProviderSource.class)
    public void exampleTest(String browser, String username,  String  password)  throws Exception
    {
        SimpleUtils.pass( username + ":" + password);

    }
}