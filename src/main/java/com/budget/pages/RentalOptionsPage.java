package com.budget.pages;

import java.math.BigDecimal;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.budget.base.ProjectMethods;

public class RentalOptionsPage extends ProjectMethods {

	public static double baseFare;
	public static double feesAndTaxes;
	public static double estimatedTotal;

	public RentalOptionsPage(RemoteWebDriver driver, ExtentTest eachNode) {
		this.driver = driver;
		this.eachNode = eachNode;
		if (!verifyTitle("Reservations | Budget Car Rental")) {
			reportStep("This is not Car Reservation Page", "Fail");
		}
	}

	public RentalOptionsPage verifyPickUpLocation(String data) {
		WebElement elePickUpLocation = locateElement("xpath", prop.getProperty("CarSelectionPage.pickupLocText.xpath"));
		verifyExactText(elePickUpLocation, data);
		return this;
	}

	public RentalOptionsPage verifyDropLocation(String data) {
		WebElement eleDropLocation = locateElement("xpath", prop.getProperty("CarSelectionPage.dropLocText.xpath"));
		verifyExactText(eleDropLocation, data);
		return this;
	}

	public RentalOptionsPage verifyVehicleType() {
		WebElement eleCarType = locateElement("xpath", prop.getProperty("RentalOptionsPage.carType.xpath"));
		verifyExactText(eleCarType, CarSelectionPage.vehicleType);
		return this;
	}

	public RentalOptionsPage verifyBasePrice() {
		WebElement eleCarType = locateElement("xpath", prop.getProperty("RentalOptionsPage.baseRate.xpath"));
		String basePriceText = getElementText(eleCarType);
		baseFare = Float.parseFloat(basePriceText);
		verifyExactText(eleCarType, Float.toString(CarSelectionPage.floatingPrice));
		return this;
	}

	@SuppressWarnings("deprecation")
	public RentalOptionsPage verifyTotalPrice() {
		WebElement eleFeesAndTaxes = locateElement("xpath", prop.getProperty("RentalOptionsPage.FeesAndTaxes.xpath"));
		WebElement eleEstimatedTotal = locateElement("xpath",
				prop.getProperty("RentalOptionsPage.EstimatedTotal.xpath"));
		String feesTaxesText = getElementText(eleFeesAndTaxes);
		String estimatedTotalText = getElementText(eleEstimatedTotal);
		feesAndTaxes = Float.parseFloat(feesTaxesText);
		estimatedTotal = Float.parseFloat(estimatedTotalText);
		double expectedTotal = baseFare + feesAndTaxes;
		BigDecimal bd = new BigDecimal(Double.toString(expectedTotal));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		verifyExactText(eleEstimatedTotal, bd.toString());
		return this;
	}

}
