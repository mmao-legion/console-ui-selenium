package com.legion.pages.core;

import static com.legion.utils.MyThreadLocal.getDriver;

import org.openqa.selenium.support.PageFactory;

import com.legion.pages.BasePage;
import com.legion.pages.ControlsPage;

public class ConsoleControlsPage extends BasePage implements ControlsPage{
	
	public ConsoleControlsPage(){
		PageFactory.initElements(getDriver(), this);
	}

	@Override
	public void gotoControlsPage() throws Exception {
		// TODO Auto-generated method stub
		
	}

	

}
