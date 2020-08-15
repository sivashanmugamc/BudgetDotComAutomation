package com.budget.testcases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.budget.base.ProjectMethods;
import com.budget.pages.HomePage;

public class BudgetCarFinder extends ProjectMethods {
	@BeforeTest
	public void setTestCaseDetails() {
		testCaseName = "Car Finder";
		testCaseDescription = "Find a Car in Budget.com";
		author = "Sivashanmugam C";
		category = "Smoke";
		dataSheetName = "TC001";		
	}

	@Test(dataProvider = "getData")
	public void findCar(String pickupLoc, String fromToLoc, String carType, String vehicleSort, String door, String seat) throws InterruptedException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/YYYY");  
		   LocalDate now = LocalDate.now();  
		   String pickUpDate=dtf.format(now);
		   LocalDate result = now.plus(1, ChronoUnit.WEEKS);
		   String returnDate=dtf.format(result);
		new HomePage(driver, eachNode)
		.enterPickUpLocation(pickupLoc)
		.enterPickUpDate(pickUpDate)
		.enterReturnDate(returnDate)
		.clickSelectMyCar()
		.verifyPickUpLocation(fromToLoc)
		.verifyDropLocation(fromToLoc)
		.clickVehicleType()
		.selectVehicleType(carType)
		.clickVehicleSort()
		.selectVehicleBySort(vehicleSort)
		.clickPayNowButton(door,seat)
		.verifyPickUpLocation(fromToLoc)
		.verifyDropLocation(fromToLoc)
		.verifyVehicleType()
		.verifyBasePrice()
		.verifyTotalPrice();
	}

}
