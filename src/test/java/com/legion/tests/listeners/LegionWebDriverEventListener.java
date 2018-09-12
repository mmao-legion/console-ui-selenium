package com.legion.tests.listeners;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.legion.tests.TestBase;

import static com.legion.utils.MyThreadLocal.getDriver;
import static com.legion.utils.MyThreadLocal.getURL;

public class LegionWebDriverEventListener extends TestBase implements WebDriverEventListener {
	
	private String elementTextToReport = "";
    private String elementType = "";
    private boolean selected, isPopover, isClearable;

	@Override
	public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
		 
		
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
		takeScreenShot();
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
		elementTextToReport = "";
        elementType = "";
        if (element.getTagName().equals("input")) {
            elementType = element.getAttribute("type");
        } else {
            elementType = element.getTagName();
        }
        if (elementType.equals("checkbox") || elementType.equals("radio")) {
            selected = !element.isSelected();
        }
        String elementText = element.getText();
        String elementName = element.getAttribute("name") != null ? element.getAttribute("name").replaceAll("[-_]", " ") : "";
        String elementId = element.getAttribute("id") != null ? element.getAttribute("id") : "";
        String elementValue = element.getAttribute("value") != null ? element.getAttribute("value") : "";
        String elementTitle = element.getAttribute("title") != null ? element.getAttribute("title") : "";

        isPopover = element.getAttribute("class").contains("mi-popover");

        switch (elementType) {
            case "option":
                WebElement select = element.findElement(By.xpath(".."));
                elementText = select.getText();
                elementName = select.getAttribute("name") != null ? select.getAttribute("name").replaceAll("[-_]", " ") : "";
                elementId = select.getAttribute("id") != null ? select.getAttribute("id") : "";
                elementValue = select.getAttribute("value") != null ? select.getAttribute("value") : "";
                elementTitle = select.getAttribute("title") != null ? select.getAttribute("title") : "";
                if (!elementId.equals("")) {
                    elementTextToReport = StringUtils
                            .join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(elementId)), ' ');
                } else if (!elementName.equals("")) {
                    elementTextToReport = StringUtils
                            .join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(elementName)), ' ');
                } else if (!elementText.equals("")) {
                    elementTextToReport = elementText;
                } else if (!elementValue.equals("")) {
                    elementTextToReport = elementValue;
                } else if (!elementTitle.equals("")) {
                    elementTextToReport = elementTitle;
                }
                break;
            default:
                if (!elementName.equals("")) {
                    elementTextToReport = StringUtils
                            .join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(elementName)), ' ');
                } else if (!elementText.equals("")) {
                    elementTextToReport = elementText;
                } else if (!elementId.equals("")) {
                    elementTextToReport = StringUtils
                            .join(StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(elementId)), ' ');
                } else if (!elementValue.equals("")) {
                    elementTextToReport = elementValue;
                } else if (!elementTitle.equals("")) {
                    elementTextToReport = elementTitle;
                }
        }
        takeScreenShot();
		
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		takeScreenShot();
		
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver,
			CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver,
			CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	
}
