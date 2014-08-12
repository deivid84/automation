package com.globant.training.david_goldes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactUsPage extends MasterPage {
	
	@FindBy(id = "cntctfrm_contact_name")
	private WebElement nameInput;

	@FindBy(id = "cntctfrm_contact_email")
	private WebElement emailInput;

	@FindBy(id = "cntctfrm_contact_subject")
	private WebElement subjectInput;

	@FindBy(id = "cntctfrm_contact_message")
	private WebElement messageInput;

	@FindBy(xpath = "//*[@id='cntctfrm_contact_form']/div[last()]/input[4]")
	private WebElement submitButton;


	public ContactUsPage(WebDriver driver) {
		super(driver);
	}

	public void setName(String name) {
		nameInput.sendKeys(name);
	}

	public void setEmail(String email) {
		emailInput.sendKeys(email);
	}

	public void setSubject(String subject) {
		subjectInput.sendKeys(subject);
	}

	public void setMessaage(String message) {
		messageInput.sendKeys(message);
	}

	public void clickSubmit() {
		submitButton.click();
	}

	// fills the corresponding textboxes
	public void fillContactData(String name, String email, String subject,
			String message) {
		setName(name);
		setEmail(email);
		setSubject(subject);
		setMessaage(message);		
	}

	
	public boolean sentSuccesfully() {
		if (driver.findElements(By.id("cntctfrm_thanks")).isEmpty())
			return false;
		else
			return true;
	}
	
	//este metodo lo hice para jugar un poco con el reporte, el unico problema es
	//que el emailable-report queda con todos los tags sin interpretar
	public String toString(){
		return "<strong style=\"color:red;\">name: </strong>" + nameInput.getAttribute("value") + "<br/>" + 
				"<b style=\"color:red;\">email: </b>" + emailInput.getAttribute("value") + "<br/>" +
				"<strong style=\"color:red;\">subject: </strong>" + subjectInput.getAttribute("value") + "<br/>" +
				"<strong style=\"color:red;\">message: </strong>" + messageInput.getAttribute("value");
	}
	
	public void waitForLoad(){		
		  super.waitForLoad(submitButton);
	}
}
