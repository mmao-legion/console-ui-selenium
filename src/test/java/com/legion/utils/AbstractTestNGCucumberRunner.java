//package com.legion.utils;
//
//import com.legion.tests.annotations.Tags;
//import cucumber.api.java.gl.E;
//import cucumber.api.testng.AbstractTestNGCucumberTests;
//import cucumber.api.testng.PickleEventWrapper;
//import cucumber.api.testng.TestNGCucumberRunner;
//import cucumber.runtime.Runtime;
//import jdk.nashorn.internal.objects.annotations.Getter;
//import org.apache.commons.lang.reflect.FieldUtils;
//import org.apache.commons.lang3.AnnotationUtils;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.DataProvider;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class AbstractCucumberRunner extends AbstractTestNGCucumberTests {
////    runner
//    private static ThreadLocal<AbstractCucumberRunner> currentRunner = new ThreadLocal<AbstractCucumberRunner>();
//
//    @BeforeTest
//    public void setCurrentRunner(){currentRunner.set(this);}
//
//    public static AbstractCucumberRunner getCurrentRunner(){return currentRunner.get();}
//
//    public static Runtime getCurrentRuntime(){
//        try{
//            AbstractCucumberRunner testRunner = AbstractCucumberRunner.getCurrentRunner();
//            TestNGCucumberRunner testNGCucumberRunner = (TestNGCucumberRunner) FieldUtils.getField(AbstractTestNGCucumberTests.class,"",true).get(testRunner);
//            return (Runtime).FieldUtils.getField(AbstractTestNGCucumberTests.class,"",true).get(testNGCucumberRunner);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//    /*====================Scenarios==================*/
//    @Getter
//    private List<PickleEventWrapper> scenarioList;
//
//    @DataProvider
//    @Override
//    public Object [][] scenarios(){
//
//        Object[][] scenariosParameters = super.scenarios();
//        List<Object[]> resultList = new ArrayList<>();
//        scenarioList = new ArrayList<>();
//
//        //Tags Filter
//        Tags tags = AnnotationUtils.findAnnotation(this.getClass(),Tags.class);
//        List<String> tagList = tags ==null? Arrays.asList() : Arrays.asList(tags.value());
//        for (Object[] parameters : scenariosParameters) {
//            if (parameters[0] != null) {
//                PickleEventWrapper wrapper = (PickleEventWrapper)parameters[0];
//                List<String> wrapperTag = GherkinUtils.getTagList(wrapper.getPickleEvent().pickle.getTags());
//
//                if (wrapperTag.contains("NotAutomatable")) {
//
//                }else if(tagList.isEmpty()){
//                    resultList.add(parameters);
//                    scenarioList.add(wrapper);
//                }else {
//                    for (String tag : wrapperTag
//                         ) {
//                        if (tagList.contains(tag)) {
//                            resultList.add(parameters);
//                            scenarioList.add(wrapper);
//                            break;
//                        }
//
//                    }
//                }
//             }
//        }
//        return  resultList.toArray()
//    }
//
///*===========Plugins================*/
//
////    @Getter
////    private ScenarioInterceptor scenarioInterceptor;
//
//
//
//    @AfterTest
//    public void tearDown() throws Exception{
//
//        tearDownClass();
//    }
//}
