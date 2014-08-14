package com.globant.training.david_goldes.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//emanuel: Casi siempre vas a a heredar de una clase base ya existente. Pero si creas una,
//la convencion para el nombre es BasePage, o AbstractPage o Page
public class MasterPage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	@FindBy(xpath = "//*[@id='access']/div[3]/ul/li[2]/a")
	private WebElement contactUs;
	
	protected MasterPage(WebDriver driver){
		this.driver = driver;
		
		//no hay que hardcodear el wait. El wait se debe crear cuando se necesite
		//(deben ser la menor cantidad de veces posible)
		wait = new WebDriverWait(driver, 10);
	}
	
	public ContactUsPage clickContactUs() {
		contactUs.click();
		return PageFactory.initElements(driver, ContactUsPage.class);
	}
	
	//este metodo no es necesario. Ademas no es claro que estas esperando. Es decir el metodo
	//se llama wait for load pero no se cuanto tiempo va a esperar a hacer que cosa.
	protected void waitForLoad(WebElement element){
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
}
