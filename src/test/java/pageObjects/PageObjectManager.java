package pageObjects;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {
	public LoginObject loginObject;
	public PromoObject promoObject;
	public MyAccountObject filtersValidateObject;
	public RechargeObject rechargeObject;
	public HomePageObject homePageObject;
	public YogiLiveObject yogiLiveObject;
	public ChatModuleObject chatModuleObject;
	public CallModuleObject callModuleObject;

	public WebDriver driver;

	public PageObjectManager(WebDriver driver) {
		this.driver = driver;
	}

	public LoginObject getLogin() {
		loginObject = new LoginObject(driver);
		return loginObject;
	}

	public PromoObject getPromo() {
		promoObject = new PromoObject(driver);
		return promoObject;
	}

	public MyAccountObject getFilter() {
		filtersValidateObject = new MyAccountObject(driver);
		return filtersValidateObject;
	}

//RECHARGE FLOW	
	public RechargeObject getRecharge() {
		rechargeObject = new RechargeObject(driver);
		return rechargeObject;
	}

//Home Page flow
	public HomePageObject getHomePage() {
		homePageObject = new HomePageObject(driver);
		return homePageObject;
	}

//Yogi Live flow
	public YogiLiveObject getYogiLiveObject() {
		yogiLiveObject = new YogiLiveObject(driver);
		return yogiLiveObject;
	}

//Chat Module flow
	public ChatModuleObject getChatModuleObject() {
		chatModuleObject = new ChatModuleObject(driver);
		return chatModuleObject;
	}

//Call Module flow
	public CallModuleObject getCallModuleObject() {
		callModuleObject = new CallModuleObject(driver);
		return callModuleObject;
	}

//Common Astrologer Consultation Flow
	public CommonObject getCommonObject() {
		return new CommonObject(driver);
	}

}