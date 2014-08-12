package com.globant.training.david_goldes.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends MasterPage {

	private static final String PAGETITLE = "Automation Training | Aprender a automatizar en un solo sitio";

	@FindBy(id = "s")
	private WebElement searchInput;

	@FindBy(xpath = "//*[@id='content']/article[1]//time")
	private WebElement dateTime;

	@FindBy(xpath = "//*[@id='access']/div[3]/ul/li[2]/a")
	private WebElement contactUs;

	@FindBy(xpath="//*[@id='prev']/a")
	private WebElement prevMonth;
	
	@FindBy(xpath = "//*[@id='wp-calendar']/tbody//td/a[1]")
	private WebElement firstDayWithPosts;
	
	@FindBy(xpath="//*[@id='content']/article//h1/a")
	private List<WebElement> posts;

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public void go() {
		driver.get("http://10.28.148.127/wordpress/");
	}

	public boolean isHomePage() {
		return driver.getTitle().equals(PAGETITLE);
	}

	public SearchResultsPage search(String query) {
		searchInput.sendKeys(query);
		searchInput.submit();
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}

	public String getDateTime() {
		return dateTime.getAttribute("datetime");
	}

	public PostPage clickPost() {
		dateTime.click();
		return PageFactory.initElements(driver, PostPage.class);
	}

	public void waitForLoad() {
		super.waitForLoad(dateTime);
	}

	//returns false if there were no posts this month
	public boolean checkForPostsThisMonth() {
		return !(driver.findElements(By.xpath("//*[@id='wp-calendar']/tbody//td/a")).isEmpty());
	}
	
	public HomePage goToMonthWithPosts(){
		prevMonth.click();
		return PageFactory.initElements(driver, HomePage.class);
	}
	
	public HomePage goToDayWithPosts(){
		firstDayWithPosts.click();
		return PageFactory.initElements(driver, HomePage.class);
	}
	
	public int countPosts(){
		return posts.size();		
	}
	
	public String getPostTitle(int pos){
		if(pos < posts.size())
			return posts.get(pos).getText();
		else
			return "Invalid Index";
	}

}
