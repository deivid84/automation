package com.globant.training.david_goldes.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage extends MasterPage{
		
	public void go() {
		driver.get("http://www.cheaptickets.com/");
	}
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	public void waitForLoad() {
		super.waitForLoad();
	}	
}
