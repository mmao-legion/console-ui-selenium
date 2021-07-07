package com.legion.pages;

import java.util.Map;

public interface IntegrationPage {

	public void clickOnConsoleIntegrationPage() throws Exception;
	public void createConfig(Map<String, String> configInfo) throws Exception;
	public boolean checkIsConfigExists(String channel, String application) throws Exception;
}
