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

	public void clickSubmit() {
		submitButton.click();
	}
	
	public void fillContactData(String name, String email, String subject,
			String message) {
		nameInput.sendKeys(name);	
		emailInput.sendKeys(email);
		subjectInput.sendKeys(subject);
		messageInput.sendKeys(message);
	}

	
	public boolean sentSuccesfully() {
	    return !driver.findElements(By.id("cntctfrm_thanks")).isEmpty();
	}
	
	//Fijate que solo usas este setter solo cuando testear que algun campo esta incompleto.
	//Lo que se debe hacer es sacar este metodo al igual que todos los setters del form y
	//reemplazar el metodo fillContactData con un Builder pattern para llenar los campos
	public void fillName(String name) {
	    this.nameInput.sendKeys(name);
	}
}
