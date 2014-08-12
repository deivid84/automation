package com.globant.training.david_goldes.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.globant.training.david_goldes.pages.ContactUsPage;
import com.globant.training.david_goldes.pages.HomePage;
import com.globant.training.david_goldes.pages.PostPage;
import com.globant.training.david_goldes.pages.SearchResultsPage;

public class Tests {

	//constants
	private final static String SEARCHQUERY = "Software";
	private final static String NAME = "David";
	private final static String EMAIL = "david@test.com";
	private final static String SUBJECT = "test subject";
	private final static String MESSAGE = "test message";
	
	
	private WebDriver driver;	
	
	
	@BeforeMethod
	public void before() {
		driver = new FirefoxDriver();		
	}

	@AfterMethod
	public void after() {
		driver.quit();
	}

	//verifies that the title of the page is correct
	@Test(timeOut = 30000)
	public void checkTitle() {
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		Assert.assertTrue(homePage.isHomePage(), "Not Homepage");		
		Reporter.log("Exit");
	}

	//does a search for SEARCHQUERY and checks if there were any results
	//no results are found when the first post of the page has id post-0
	@Test(timeOut = 30000)
	public void doSearch() {
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);		
		homePage.go();
		Reporter.log("Entering Homepage<br/>");		
		Reporter.log("Executing search for: " + SEARCHQUERY + "<br/>");
		SearchResultsPage searchPage = homePage.search(SEARCHQUERY);		
		Assert.assertTrue(searchPage.foundResults(), "No Results Were Found");
		Reporter.log("Exit");
	}

	//gets the dateTime from the first post in homepage, enters the post
	//and verifies that both dates match
	@Test(timeOut = 30000)
	public void checkDateTime() {
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);	
		homePage.go();
		Reporter.log("Entering Homepage<br/>");		
		String dateTime = homePage.getDateTime();
		Reporter.log("Time in HomePage is: " + dateTime + "<br/>");
		PostPage postPage = homePage.clickPost();
		Reporter.log("Entering PostPage<br/>");
		String dateTimePost = postPage.getDateTime();
		Reporter.log("Time in PostPage is: " + dateTimePost + "<br/>");
		Assert.assertEquals(dateTime, dateTimePost, "Dates are Different");
		Reporter.log("Exit");
	}

	//enters the contact us page, fills in the fields and submits the form
	//submit fails if the node with id cntctfrm_thanks is not present
	@Test(timeOut = 30000)
	public void contactUs() {
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);		 
		homePage.go();
		Reporter.log("Entering Homepage<br/>");		
		ContactUsPage contactUsPage = homePage.clickContactUs();
		contactUsPage.waitForLoad();
		contactUsPage.fillContactData(NAME, EMAIL, SUBJECT, MESSAGE);
		Reporter.log("Submitting data<br/>");
		Reporter.log(contactUsPage.toString() + "<br/>");
		contactUsPage.clickSubmit();
		Assert.assertTrue(contactUsPage.sentSuccesfully(), "Submit Failed");
		Reporter.log("Exit");
	}

	//enters contact us page, fills all field but name and submits, fills the name field
	//and submits again
	//test fails if the first submit succeeds of the second fails
	@Test(timeOut = 30000)
	public void contactUsWithFail() {
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);		
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		ContactUsPage contactUsPage = homePage.clickContactUs();
		contactUsPage.waitForLoad();
		contactUsPage.fillContactData("", EMAIL, SUBJECT, MESSAGE);
		Reporter.log("Submitting incorrect data<br/>");
		Reporter.log(contactUsPage.toString() + "<br/>");
		contactUsPage.clickSubmit();
		Assert.assertFalse(contactUsPage.sentSuccesfully(), "Submit Succes, should have failed");
		contactUsPage.setName(NAME);
		Reporter.log("Submitting correct data<br/>");
		Reporter.log(contactUsPage.toString() + "<br/>");
		contactUsPage.clickSubmit();
		Assert.assertTrue(contactUsPage.sentSuccesfully(), "Submit Failed");
		Reporter.log("Exit");
	}
	
	//checks for post in the current month and goes to the first day with posts, if there are
	//no posts this month it goes the previous month with posts and selects the first day with posts
	//afterwards it counts the number of posts and logs their titles
	@Test(timeOut = 30000)
	public void countPosts(){
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);		
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		if(homePage.checkForPostsThisMonth()){				
			homePage = homePage.goToDayWithPosts();
			Reporter.log("Going to first day with posts<br/>");
		}
		else{
			homePage = homePage.goToMonthWithPosts();
			Reporter.log("Going to last month with posts<br/>");
			homePage = homePage.goToDayWithPosts();
			Reporter.log("Going to first day with posts<br/>");
		}
		homePage.waitForLoad();
		int posts = homePage.countPosts();
		Reporter.log("There are: " + posts + " posts<br/>");
		for(int i = 0; i < posts; i++){
			Reporter.log("Title of post " + i + " is: " + homePage.getPostTitle(i) + "<br/>");
		}
		Reporter.log("Exit");
	}
}
