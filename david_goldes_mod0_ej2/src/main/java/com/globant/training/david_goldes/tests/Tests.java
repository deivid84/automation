package com.globant.training.david_goldes.tests;

import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.globant.training.david_goldes.pages.CustomizeTripPage;
import com.globant.training.david_goldes.pages.FlightPage;
import com.globant.training.david_goldes.pages.HomePage;
import com.globant.training.david_goldes.pages.HotelFlightPage;
import com.globant.training.david_goldes.pages.SelectFlightPage;
import com.globant.training.david_goldes.pages.SelectHotelPage;
import com.globant.training.david_goldes.pages.SearchResultsPage;
import com.globant.training.david_goldes.pages.SignInPage;
import com.globant.training.david_goldes.pages.SignOutPage;
import com.globant.training.david_goldes.pages.TravelerInfoPage;
import com.globant.training.david_goldes.pages.TripDetailsPage;
import com.globant.training.david_goldes.pages.TripSidebarPage;
import com.globant.training.david_goldes.pages.VacationPackageSidebarPage;

public class Tests {

	//constants
	private static final String USERNAME = "david_test@gmail.com";
	private static final String INVALIDUSERNAME = "zdavid_test@gmail.com";
	private static final String PASSWORD = "123456";
	private static final String INVALIDPASSWORD = "123456abc";
	private static final String DEPARTURE = "LAS";
	private static final String ARRIVAL = "LAX";
	private static final int SORTBYFEWERSTOPS = 2;
	private static final int SORTBYUSERRATING = 5;
	private static final int SORTBYSTARS = 3;
	private WebDriver driver;
	
	
	@BeforeMethod
	public void before() {
		driver = new FirefoxDriver();		
	}

	@AfterMethod
	public void after() {
		driver.quit();
	}
	
	@Test(description = "Performs a Sign In and verifies it was succesfull")
	public void checkLogIn(){
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		SignInPage signInPage = homePage.clickSignIn();
		Reporter.log("Entering SignIn Page<br/>");
		signInPage.waitForLoad();
		homePage = signInPage.signIn(USERNAME, PASSWORD);
		homePage.waitForLoad();
		Reporter.log("Signing In with valid data<br/>");
		Assert.assertTrue(homePage.verifySignIn(), "Sign In Failed");
		Reporter.log("Exit");
	}
	
	@Test(description = "Performs a Sign In with and invalid username and verifies it failed")
	public void checkInvalidUserLogIn(){
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		SignInPage signInPage = homePage.clickSignIn();
		Reporter.log("Entering SignIn Page<br/>");
		signInPage.waitForLoad();
		homePage = signInPage.signIn(INVALIDUSERNAME, PASSWORD);
		homePage.waitForLoad();
		Reporter.log("Signing In with invalid data<br/>");
		Assert.assertFalse(homePage.verifySignIn(), "Sign In Succes");
		Reporter.log("Exit");
	}
	
	@Test(description = "Performs a Sign In with and invalid password and verifies it failed")
	public void checkInvalidPasswordLogIn(){
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		SignInPage signInPage = homePage.clickSignIn();
		Reporter.log("Entering SignIn Page<br/>");
		signInPage.waitForLoad();
		homePage = signInPage.signIn(USERNAME, INVALIDPASSWORD);
		homePage.waitForLoad();
		Reporter.log("Signing In with invalid data<br/>");
		Assert.assertFalse(homePage.verifySignIn(), "Sign In Succes");
		Reporter.log("Exit");
	}
	
	@Test(description = "Signs In and the Signs out")
	public void checkSignOut(){
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		SignInPage signInPage = homePage.clickSignIn();
		Reporter.log("Entering SignIn Page<br/>");
		signInPage.waitForLoad();
		homePage = signInPage.signIn(USERNAME, PASSWORD);
		homePage.waitForLoad();
		Reporter.log("Signing In with valid data<br/>");
		SignOutPage signOutPage = homePage.clickSignOut();
		Reporter.log("Signing out<br/>");
		signOutPage.waitForLoad();
		Assert.assertTrue(signOutPage.verifySignedOut(), "Sign Out Failed");
		Reporter.log("Exit");
	}
	
	@Test(description = "Performs a Flight Only search, sorts the results, selects the first one and procceds forward until checkout")
	public void testFlight(){
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		FlightPage flightPage = homePage.clickFlight();
		Reporter.log("Entering FlightPage<br/>");
		flightPage.waitForLoad();
		Reporter.log("FlightPage Loaded<br/>");
		Date leaveDate = new Date();	
		Calendar c = Calendar.getInstance();
		c.setTime(leaveDate);
		c.add(Calendar.DATE, 7);
		leaveDate = c.getTime();
		c.add(Calendar.DATE, 7);
		Date returnDate = c.getTime();
		SearchResultsPage searchResultsPage = flightPage.setFlight(DEPARTURE, ARRIVAL, leaveDate, returnDate);
		Reporter.log("Searching Flights<br/>");
		searchResultsPage.waitForLoad();
		searchResultsPage.waitForSearchComplete();
		Reporter.log("Search Complete<br/>");
		verifySearchResults(searchResultsPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);		
		
		searchResultsPage = searchResultsPage.sortByOption(SORTBYFEWERSTOPS);
		Reporter.log("Sorting<br/>");		
		searchResultsPage.waitForLoad();
		
		TripDetailsPage tripDetailsPage = searchResultsPage.selectFirstOption();
		tripDetailsPage.waitForLoad();
		verifyTripDetail(tripDetailsPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);		
		
		CustomizeTripPage customizeTripPage = tripDetailsPage.clickContinue();
		verifyTripDetail(customizeTripPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);		
		
		TravelerInfoPage travelerInfoPage = customizeTripPage.clickContinue();
		verifyTripDetail(travelerInfoPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);		
		
		travelerInfoPage.setTravelerInformation("David", "Goldes", "Argentina", "1598765432", "M", "May", "28", "1984", "david_test@gmail.com");
		travelerInfoPage.clickSubmit();
		Reporter.log("Exit");
	}
	
	@Test(description = "Attempts to perform a search with an invalid return date and verifies it failed")
	public void returnBeforeLeave(){
	
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		FlightPage flightPage = homePage.clickFlight();
		Reporter.log("Entering FlightPage<br/>");
		flightPage.waitForLoad();
		Reporter.log("FlightPage Loaded<br/>");
		Date leaveDate = new Date();	
		Calendar c = Calendar.getInstance();
		c.setTime(leaveDate);
		c.add(Calendar.DATE, 7);
		leaveDate = c.getTime();
		c.add(Calendar.DATE, -1);
		Date returnDate = c.getTime();
		SearchResultsPage searchResultsPage = flightPage.setFlight(DEPARTURE, ARRIVAL, leaveDate, returnDate);
		Assert.assertFalse(searchResultsPage.verifySearchResultsPage(), "Search Should Not Have Been Completed");
		Reporter.log("Exit");
	}
	
	@Test(description = "Performs a Flight Only search, filters the results by United Airlines and selects the first option")
	public void flightWithUnitedAirlines(){
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		FlightPage flightPage = homePage.clickFlight();
		Reporter.log("Entering FlightPage<br/>");
		flightPage.waitForLoad();
		Reporter.log("FlightPage Loaded<br/>");
		Date leaveDate = new Date();	
		Calendar c = Calendar.getInstance();
		c.setTime(leaveDate);
		c.add(Calendar.DATE, 7);
		leaveDate = c.getTime();
		c.add(Calendar.DATE, 7);
		Date returnDate = c.getTime();
		SearchResultsPage searchResultsPage = flightPage.setFlight(DEPARTURE, ARRIVAL, leaveDate, returnDate);
		Reporter.log("Searching Flights<br/>");
		searchResultsPage.waitForLoad();
		searchResultsPage.waitForSearchComplete();
		Reporter.log("Search Complete<br/>");
		verifySearchResults(searchResultsPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);		
		
		Reporter.log("Filtering<br/>");
		searchResultsPage.clickUnitedAirlinesBox();					
		Reporter.log("Filter Complete<br/>");
		
		TripDetailsPage tripDetailsPage = searchResultsPage.selectFirstOption();
		tripDetailsPage.waitForLoad();
		verifyTripDetail(tripDetailsPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);		
		
		CustomizeTripPage customizeTripPage = tripDetailsPage.clickContinue();
		verifyTripDetail(customizeTripPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);		
		
		TravelerInfoPage travelerInfoPage = customizeTripPage.clickContinue();
		verifyTripDetail(travelerInfoPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);		
		
		travelerInfoPage.setTravelerInformation("David", "Goldes", "Argentina", "1598765432", "M", "May", "28", "1984", "david_test@gmail.com");
		travelerInfoPage.clickSubmit();
		Reporter.log("Exit");
	}
	
	@Test(description = "Performs a Flight Only search, sorts the results, advances until traveler information page, signs in and continues")
	public void testFligthWithLogin(){
	
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		FlightPage flightPage = homePage.clickFlight();
		Reporter.log("Entering FlightPage<br/>");
		flightPage.waitForLoad();
		Reporter.log("FlightPage Loaded<br/>");
		Date leaveDate = new Date();	
		Calendar c = Calendar.getInstance();
		c.setTime(leaveDate);
		c.add(Calendar.DATE, 7);
		leaveDate = c.getTime();
		c.add(Calendar.DATE, 7);
		Date returnDate = c.getTime();
		SearchResultsPage searchResultsPage = flightPage.setFlight(DEPARTURE, ARRIVAL, leaveDate, returnDate);
		Reporter.log("Searching Flights<br/>");
		searchResultsPage.waitForLoad();
		searchResultsPage.waitForSearchComplete();
		Reporter.log("Search Complete<br/>");
		verifySearchResults(searchResultsPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);		
		
		searchResultsPage = searchResultsPage.sortByOption(SORTBYFEWERSTOPS);
		Reporter.log("Sorting<br/>");		
		searchResultsPage.waitForLoad();		
		
		TripDetailsPage tripDetailsPage = searchResultsPage.selectFirstOption();
		tripDetailsPage.waitForLoad();
		verifyTripDetail(tripDetailsPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);		
		
		CustomizeTripPage customizeTripPage = tripDetailsPage.clickContinue();
		verifyTripDetail(customizeTripPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);		
		
		TravelerInfoPage travelerInfoPage = customizeTripPage.clickContinue();
		verifyTripDetail(travelerInfoPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);	
		
		Reporter.log("Signing In<br/>");
		travelerInfoPage = travelerInfoPage.signIn(USERNAME, PASSWORD);
		Reporter.log("Signed In<br/>");
		travelerInfoPage.setTravelerInformationLoggedIn();
		travelerInfoPage.clickSubmit();
		Reporter.log("Exit");	
	}	
	
	@Test(description = "Performs a Flight + Hotel search, sorts results and selects the first option")
	public void flightPlusHotel(){
		
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.go();
		Reporter.log("Entering Homepage<br/>");
		HotelFlightPage hotelFlightPage = homePage.selectFlightHotel();
		Date leaveDate = new Date();	
		Calendar c = Calendar.getInstance();
		c.setTime(leaveDate);
		c.add(Calendar.DATE, 7);
		leaveDate = c.getTime();
		c.add(Calendar.DATE, 7);
		Date returnDate = c.getTime();
		hotelFlightPage.waitForLoad();
		SelectHotelPage selectHotelPage = hotelFlightPage.setFlightHotel(DEPARTURE, ARRIVAL, leaveDate, returnDate, "1");
		selectHotelPage.waitForLoad();
		verifyHotelSearchResults(selectHotelPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);
		Assert.assertTrue(selectHotelPage.verifyResultsAvailable(), "No Results");
		
		selectHotelPage.sortByOption(SORTBYUSERRATING);
		selectHotelPage.sortByOption(SORTBYSTARS);
		Assert.assertTrue(selectHotelPage.verifySortedByStars(), "Not Sorted");
		verifyHotelSearchResults(selectHotelPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);
		
		SelectFlightPage selectFlightPage = selectHotelPage.selectFirstOption();
		selectFlightPage.waitForLoad();
		verifyHotelSearchResults(selectFlightPage, leaveDate, returnDate, DEPARTURE, ARRIVAL);
	}
	
	/**
	 * Runs a series of assertions to verify that the Trip Details are correct
	 * 
	 * @param page a page extending from TripSidebarPage
	 * @param leaveDate
	 * @param returnDate
	 * @param departure
	 * @param arrival
	 */
	private void verifyTripDetail(TripSidebarPage page, Date leaveDate, Date returnDate, String departure, String arrival){
		Assert.assertTrue(page.verifyLeaveDate(leaveDate), "Wrong Leave Date");
		Assert.assertTrue(page.verifyReturnDate(returnDate), "Wrong Return Date");
		Assert.assertTrue(page.verifyLeaveLocation(departure), "Wrong Leave Location");
		Assert.assertTrue(page.verifyReturnLocation(arrival), "Wrong Return Location");
		Reporter.log("Trip Details OK<br/>");
	}
	
	/**
	 * Runs a series of assertions to verify that the "Flight Only" search was made with the correct parameters
	 * 
	 * @param page a SearchResultsPage
	 * @param leaveDate
	 * @param returnDate
	 * @param departure
	 * @param arrival
	 */
	private void verifySearchResults(SearchResultsPage page, Date leaveDate, Date returnDate, String departure, String arrival){
		Assert.assertTrue(page.verifyDepartLocation(departure), "Wrong Depart Location");
		Assert.assertTrue(page.verifyArriveLocation(arrival), "Wrong Arrive Location");
		Assert.assertTrue(page.verifyArriveDate(returnDate), "Wrong Arrive Date");
		Assert.assertTrue(page.verifyDepartDate(leaveDate), "Wrong Depart Date");	
		Assert.assertTrue(page.verifyResultsAvailable(), "No Results");
		Reporter.log("Search Results OK<br/>");
	}
	
	/**
	 * Runs a series of assertions to verify that the "Flight + Hotel" search was made with the correct parameters
	 * 
	 * @param page a page extending from verifyHotelSearchResults
	 * @param leaveDate
	 * @param returnDate
	 * @param departure
	 * @param arrival
	 */
	private void verifyHotelSearchResults(VacationPackageSidebarPage page, Date leaveDate, Date returnDate, String departure, String arrival){
		Assert.assertTrue(page.verifyDepartLocation(departure), "Wrong Depart Location");
		Assert.assertTrue(page.verifyArriveLocation(arrival), "Wrong Arrive Location");
		Assert.assertTrue(page.verifyReturnDate(returnDate), "Wrong Arrive Date");
		Assert.assertTrue(page.verifyLeaveDate(leaveDate), "Wrong Depart Date");			
		Reporter.log("Hotel Search Results OK<br/>");
	}
}
