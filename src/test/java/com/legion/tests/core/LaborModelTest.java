package com.legion.tests.core;

import com.legion.pages.*;
import com.legion.tests.TestBase;
import com.legion.tests.annotations.Automated;
import com.legion.tests.annotations.Enterprise;
import com.legion.tests.annotations.Owner;
import com.legion.tests.annotations.TestName;
import com.legion.tests.data.CredentialDataProviderSource;
import com.legion.utils.SimpleUtils;
import org.apache.commons.collections.ListUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;


public class LaborModelTest extends TestBase {

    public enum modelSwitchOperation{
        Console("Console"),
        OperationPortal("Operation Portal");

        private final String value;
        modelSwitchOperation(final String newValue) {
            value = newValue;
        }
        public String getValue() { return value; }
    }

    @Override
    @BeforeMethod()
    public void firstTest(Method testMethod, Object[] params) throws Exception{


        this.createDriver((String)params[0],"83","Window");
        visitPage(testMethod);
        loginToLegionAndVerifyIsLoginDoneWithoutUpdateUpperfield((String)params[1], (String)params[2],(String)params[3]);
        DashboardPage dashboardPage = pageFactory.createConsoleDashboardPage();
        SimpleUtils.assertOnFail("DashBoard Page not loaded Successfully!", dashboardPage.isDashboardPageLoaded(), false);
        LocationsPage locationsPage = pageFactory.createOpsPortalLocationsPage();
        locationsPage.clickModelSwitchIconInDashboardPage(modelSwitchOperation.OperationPortal.getValue());
        SimpleUtils.assertOnFail("OpsPortal Page not loaded Successfully!", locationsPage.isOpsPortalPageLoaded(), false);

    }


    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Labor Model Sanity Test")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyUserCanCreateDeleteAndPublishLaborModelTemplateAsInternalAdminForLaborModel(String browser, String username, String password, String location) throws Exception {
        try{
            SimpleDateFormat dfs=new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime=dfs.format(new Date()).trim();
            String templateName="AutoCreate"+currentTime;
            String dynamicGroupName ="For Auto";
            String action = "Archive";

            LaborModelPage laborModelPage = pageFactory.createOpsPortalLaborModelPage();
            laborModelPage.addNewLaborModelTemplate(templateName);
            laborModelPage.deleteDraftLaborModelTemplate(templateName);
            laborModelPage.publishNewLaborModelTemplate(templateName,dynamicGroupName);
//            laborModelPage.clickOnLaborModelTab();
//            laborModelPage.goToLaborModelTile();
//            laborModelPage.archivePublishedOrDeleteDraftTemplate(templateName,action);
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Global External Attributes")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyCreateUpdateAndDeleteNewAttributeFunctionAsInternalAdminForLaborModel(String browser, String username, String password, String location) throws Exception {
        try{
            SimpleDateFormat dfs=new SimpleDateFormat("yyyyMMddHHmmss");
            String currentTime=dfs.format(new Date()).trim();
            String attributeName="AutoCreate"+currentTime;
            String attributeValue = "33";
            String attributeDescription = attributeName;
            String attributeValueUpdate = "34";
            String attributeDescriptionUpdate = "Update";
            String label = "External Attributes";

            LaborModelPage laborModelPage = pageFactory.createOpsPortalLaborModelPage();
            laborModelPage.clickOnLaborModelTab();
            laborModelPage.goToLaborStandardRepositoryTile();
            laborModelPage.selectLaborStandardRepositorySubTabByLabel(label);
            laborModelPage.clickOnEditButton();
            laborModelPage.clickOnAddAttributeButton();
            //Cancel create attributes
            laborModelPage.cancelCreateNewAttribute(attributeName,attributeValue,attributeDescription);
            laborModelPage.selectLaborStandardRepositorySubTabByLabel(label);
            if(!laborModelPage.isSpecifyAttributeExisting(attributeName)){
                SimpleUtils.pass("User can cancel create attribute successfully!");
            }else {
                SimpleUtils.fail("User failed to cancel create attribute!",false);
            }

            //Create attributes
            laborModelPage.clickOnEditButton();
            laborModelPage.clickOnAddAttributeButton();
            laborModelPage.createNewAttribute(attributeName,attributeValue,attributeDescription);
            laborModelPage.selectLaborStandardRepositorySubTabByLabel(label);
            if(laborModelPage.isSpecifyAttributeExisting(attributeName)){
                SimpleUtils.pass("User created attribute successfully!");
            }else {
                SimpleUtils.fail("User failed to created attribute!",false);
            }

            //Update attribute
            laborModelPage.clickOnEditButton();
            List<String> updatedVal = laborModelPage.clickOnPencilButtonAndUpdateAttribute(attributeName,attributeValueUpdate,attributeDescriptionUpdate);
            laborModelPage.selectLaborStandardRepositorySubTabByLabel(label);
            if(updatedVal.get(0).equals(attributeValueUpdate) && updatedVal.get(1).equals(attributeDescriptionUpdate)){
                SimpleUtils.pass("User can update attribute value and description successfully!");
            }else {
                SimpleUtils.fail("User failed to update attribute value and description!",false);
            }

            //Delete attribute
            laborModelPage.checkDeleteAttributeButtonForEachAttribute();
            laborModelPage.clickOnDeleteAttributeButton(attributeName);
            laborModelPage.clickCancelBtnOnDeleteAttributeDialog();
            laborModelPage.clickOnDeleteAttributeButton(attributeName);
            laborModelPage.clickOkBtnOnDeleteAttributeDialog();
            laborModelPage.clickOnSaveButton();
            laborModelPage.selectLaborStandardRepositorySubTabByLabel(label);
            if(!laborModelPage.isSpecifyAttributeExisting(attributeName)){
                SimpleUtils.pass("User can delete attribute successfully!");
            }else {
                SimpleUtils.fail("User failed to delete attribute!",false);
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }

    @Automated(automated = "Automated")
    @Owner(owner = "Fiona")
    @Enterprise(name = "Op_Enterprise")
    @TestName(description = "Template level external attributes")
    @Test(dataProvider = "legionTeamCredentialsByRoles", dataProviderClass = CredentialDataProviderSource.class)
    public void verifyTemplateLevelAttributeFunctionAsInternalAdminForLaborModel(String browser, String username, String password, String location) throws Exception {
        try{
            String label = "External Attributes";
            HashMap<String,List<String>> attributesInfoInGlobal = new HashMap<>();
            HashMap<String,List<String>> attributesInfoInTemplate = new HashMap<>();
            HashMap<String,List<String>> attributesUpdatedInfoInTemplate = new HashMap<>();
            String attributeName ="AutoUsingAttribute";
            String attributeValueUpdate = "23";

            String templateName = "AutoUsingByFiona";
            String mode = "edit";
            LaborModelPage laborModelPage = pageFactory.createOpsPortalLaborModelPage();
            //Check are the default value of attributes at template level correct or not?
            laborModelPage.clickOnLaborModelTab();
            laborModelPage.goToLaborStandardRepositoryTile();
            laborModelPage.selectLaborStandardRepositorySubTabByLabel(label);
            attributesInfoInGlobal = laborModelPage.getValueAndDescriptionForEachAttribute();
            laborModelPage.clickOnLaborModelTab();
            laborModelPage.goToLaborModelTile();
            laborModelPage.clickOnSpecifyTemplateName(templateName,mode);
            laborModelPage.selectLaborModelTemplateDetailsPageSubTabByLabel(label);
            attributesInfoInTemplate = laborModelPage.getValueAndDescriptionForEachAttribute();
            //Compare two map info
            for(String key:attributesInfoInTemplate.keySet()){
                for(String key1:attributesInfoInGlobal.keySet()){
                    if(key.equals(key1)){
                        List<String> valuesInGlobal = attributesInfoInGlobal.get(key1);
                        List<String> valuesInTemplate = attributesInfoInTemplate.get(key);

                        if(ListUtils.isEqualList(valuesInGlobal,valuesInTemplate)){
                            SimpleUtils.pass("The attribute " + key + " in template level is correct.");
                            break;
                        }else{
                            SimpleUtils.fail("The attribute " + key + " in template level is NOT correct.",false);
                        }
                    }
                }
            }
            //update template attribute value
            laborModelPage.clickOnEditButtonOnTemplateDetailsPage();
            laborModelPage.updateAttributeValueInTemplate(attributeName,attributeValueUpdate);
            laborModelPage.selectLaborModelTemplateDetailsPageSubTabByLabel("Details");
            laborModelPage.saveAsDraftTemplate();
            //go to template details page check updated or not?
            laborModelPage.clickOnSpecifyTemplateName(templateName,mode);
            laborModelPage.selectLaborModelTemplateDetailsPageSubTabByLabel(label);
            attributesUpdatedInfoInTemplate = laborModelPage.getValueAndDescriptionForEachAttribute();
            for(String key2:attributesUpdatedInfoInTemplate.keySet()){
                if(key2.equals(attributeName)){
                    if(attributesUpdatedInfoInTemplate.get(key2).get(0).equals(attributeValueUpdate)){
                        SimpleUtils.pass("User can update attribute value successfully in labor model template.");
                        break;
                    }else {
                        SimpleUtils.fail("User can NOT update attribute value successfully in labor model template.",false);
                    }
                }
            }
        } catch (Exception e){
            SimpleUtils.fail(e.getMessage(), false);
        }
    }


}
