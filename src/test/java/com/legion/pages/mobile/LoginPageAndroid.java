package com.legion.pages.mobile;

public interface LoginPageAndroid {

	public void clickFirstLoginBtn() throws Exception;
	public void verifyLoginTitle(String textLogin) throws Exception;
	public void selectEnterpriseName() throws Exception;
	public void loginToLegionWithCredentialOnMobile(String userName, String Password) throws Exception;
	public void clickShiftOffers(String teamMember) throws Exception;
}
