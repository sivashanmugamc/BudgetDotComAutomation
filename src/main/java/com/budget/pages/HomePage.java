package com.budget.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.budget.base.ProjectMethods;

public class HomePage extends ProjectMethods {

	public HomePage(RemoteWebDriver driver, ExtentTest eachNode) {
		this.driver = driver;
		this.eachNode = eachNode;
		if (!verifyTitle("Discount car rental rates and rental car deals | Budget Car Rental")) {
			reportStep("This is not Budget.com Home Page", "Fail");
		}
	}

	public HomePage enterPickUpLocation(String data) {
		WebElement elePickUpLocation = locateElement("id", prop.getProperty("HomePage.pickupLocBox.id"));
		clearAndType(elePickUpLocation, data);
		return this;
	}

	public HomePage enterPickUpDate(String data) {
		WebElement elePickUpDate = locateElement("id", prop.getProperty("HomePage.pickupDate.id"));
		clearAndType(elePickUpDate, data);
		return this;
	}

	public HomePage enterReturnDate(String data) {
		WebElement eleReturnDate = locateElement("id", prop.getProperty("HomePage.returnDate.id"));
		clearAndType(eleReturnDate, data);
		return this;
	}

	public CarSelectionPage clickSelectMyCar() {
		WebElement eleSelectMyCar = locateElement("id", prop.getProperty("HomePage.selectMyCarBtn.id"));
		click(eleSelectMyCar);
		return new CarSelectionPage(driver, eachNode);
	}

}
