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

//si bien esta clase contiene tests y por eso la llamaste Tests, no es claro que funcionalidades
//vas a estar testeando. Deberias ponerle el nombre de una suite de tests. Ej: HomePageSuite y esta
//contenga solo los tests relacionados a dicha suite.

public class Tests {

    //No se suelen utilizar estas variables 
	private HomePage homePage;
	private WebDriver driver;	
	
	
	@BeforeMethod
	public void before() {
		driver = new FirefoxDriver();		
		homePage = PageFactory.initElements(driver, HomePage.class);
	}

	@AfterMethod
	public void after() {
		driver.quit();
	}

	@Test
	public void checkTitle() {
		homePage.go();
		//Si bien el reporte se usa, se lo hace a traves de listeners de testng. En tus tests
		//solo tiene que page objects y asserts.
		Assert.assertTrue(homePage.isHomePage());		
	}
	
	//no se usan los comments. El metodo debe indicarte que estas testeando. Los timeouts tampoco.
	//Por eso te renombro el test a modo de ejemplo.
	@Test
	public void searchingForFirstTimeReturnsNoResults() {
		homePage.go();
		SearchResultsPage searchPage = homePage.search("searchQuery");		
		Assert.assertTrue(searchPage.foundResults());
	}

	@Test
	public void checkDateTime() {
		homePage.go();
		String dateTime = homePage.getDateTime();
		PostPage postPage = homePage.clickPost();
		String dateTimePost = postPage.getDateTime();
		Assert.assertEquals(dateTime, dateTimePost);
	}

	@Test
	public void contactUs() {
		homePage.go();
		ContactUsPage contactUsPage = homePage.clickContactUs();
		//cuando tenes que pasar data a los metodos, tenes 2 opciones:
		//hardcodear los valores si es que eso cubre lo que estas testeando, o bien
		//usar los data providers de testng. En la pagina esta documentado.
		contactUsPage.fillContactData("name", "email", "subject", "message");
		contactUsPage.clickSubmit();
		Assert.assertTrue(contactUsPage.sentSuccesfully());
	}

	@Test
	public void contactUs_failNoName() {
		homePage.go();
		ContactUsPage contactUsPage = homePage.clickContactUs();
		contactUsPage.fillContactData("", "email", "subject", "message");
		contactUsPage.clickSubmit();
		Assert.assertFalse(contactUsPage.sentSuccesfully());
		contactUsPage.fillName("name");
		contactUsPage.clickSubmit();
		Assert.assertTrue(contactUsPage.sentSuccesfully(), "Submit Failed");
	}
	
	//este test habria que reescribirlo
	@Test
	public void countPosts(){
		homePage.go();
		if(homePage.existsPostsForCurrentMonth()){				
			homePage = homePage.goToDayWithPosts();
		}
		else{
			homePage = homePage.goToMonthWithPosts();
			homePage = homePage.goToDayWithPosts();
		}
		homePage.waitForLoad();
		int posts = homePage.countPosts();
		for(int i = 0; i < posts; i++){
			Reporter.log("Title of post " + i + " is: " + homePage.getPostTitle(i) + "<br/>");
		}
	}
}
