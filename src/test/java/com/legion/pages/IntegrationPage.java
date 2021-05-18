package com.legion.pages;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public interface IntegrationPage {
    public boolean checkIsConfigExists(String channel, String application) throws Exception;
    public void createConfig(Map<String, String> configInfo) throws Exception;
}
