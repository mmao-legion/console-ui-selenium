package com.legion.tests.core;

import com.legion.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;

public class InboxTest extends TestBase {
    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{
        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDone((String)params[1], (String)params[2],(String)params[3]);
    }

    //Added by Nora

    //Added by Julie

    //Added by Marym

    //Added by Haya
}
