package com.budget.pages;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.budget.base.ProjectMethods;

public class CarSelectionPage extends ProjectMethods {
	public static String vehicleType;
	public static int baseFare;
	public static float floatingPrice;
	public static float finalBasePrice;

	public CarSelectionPage(RemoteWebDriver driver, ExtentTest eachNode) {
		this.driver = driver;
		this.eachNode = eachNode;
		clickSelectThisLocationPopUp();
		clickneedACarPopUp();
		if (!verifyTitle("Reservations | Budget Car Rental")) {
			reportStep("This is not Car Reservation Page", "Fail");
		}
	}

	public CarSelectionPage clickSelectThisLocationPopUp() {
		WebElement eleSelectLocation = locateElement("xpath",
				prop.getProperty("CarSelectionPage.selectThisLocPopUp.xpath"));
		click(eleSelectLocation);
		return this;
	}

	public CarSelectionPage clickneedACarPopUp() {
		WebElement eleNeedCar = locateElement("xpath", prop.getProperty("CarSelectionPage.needACar.xpath"));
		if (verifyDisplayed(eleNeedCar))
			click(eleNeedCar);
		return this;
	}

	public CarSelectionPage verifyPickUpLocation(String data) {
		WebElement elePickUpLocation = locateElement("xpath", prop.getProperty("CarSelectionPage.pickupLocText.xpath"));
		verifyExactText(elePickUpLocation, data);
		return this;
	}

	public CarSelectionPage verifyDropLocation(String data) {
		WebElement eleDropLocation = locateElement("xpath", prop.getProperty("CarSelectionPage.dropLocText.xpath"));
		verifyExactText(eleDropLocation, data);
		return this;
	}

	public CarSelectionPage clickVehicleType() {
		WebElement eleVehicleType = locateElement("id", prop.getProperty("CarSelectionPage.vehicleTypeDrop.id"));
		click(eleVehicleType);
		return this;
	}

	public CarSelectionPage selectVehicleType(String carType) {
		String element = "//span[contains(text(),'" + carType + "')]";
		WebElement eleVehicleType = locateElement("xpath", element);
		click(eleVehicleType);
		return this;
	}

	public CarSelectionPage clickVehicleSort() {
		WebElement eleVehicleSort = locateElement("id", prop.getProperty("CarSelectionPage.vehicleSortDropdown.id"));
		click(eleVehicleSort);
		return this;
	}

	public CarSelectionPage selectVehicleBySort(String text) {
		WebElement eleVehicleType = locateElement("xpath",
				prop.getProperty("CarSelectionPage.vehicleSortDropOptions.xpath"));
		click(eleVehicleType);
		return this;
	}

	public RentalOptionsPage clickPayNowButton(String doorsText, String seatsText) {
		List<WebElement> fourDoors = locateElements("xpath", prop.getProperty("CarSelectionPage.fourDoors.xpath"));
		List<WebElement> fiveSeats = locateElements("xpath", prop.getProperty("CarSelectionPage.fiveSeat.xpath"));
		List<WebElement> viewInfo = locateElements("xpath",
				prop.getProperty("CarSelectionPage.vehicleInfoLinks.xpath"));

		Iterator<WebElement> doors = fourDoors.iterator();
		Iterator<WebElement> seats = fiveSeats.iterator();
		Iterator<WebElement> vehicleInfo = viewInfo.iterator();
		int i = 1;
		while (doors.hasNext() && seats.hasNext() && vehicleInfo.hasNext()) {
			click(vehicleInfo.next());
			String doorText = getElementText(doors.next());
			String seatText = getElementText(seats.next());
			if (doorText.equals(doorsText) && seatText.equals(seatsText)) {
				String fiveSeatsLoc = prop.getProperty("CarSelectionPage.fiveSeat.xpath");
				String payNowLoc = prop.getProperty("CarSelectionPage.PayNowButton.xpath");
				String carType = "(" + fiveSeatsLoc
						+ prop.getProperty("CarSelectionPage.carType.xpath") + ")[" + i + "]";
				System.out.println("Car Type Xpath: " + carType);

				WebElement eleVehicleType = locateElement("xpath", carType);
				vehicleType = getElementText(eleVehicleType);

				String payNow = "(" + fiveSeatsLoc + payNowLoc + ")[" + i + "]";
				WebElement elePayNowBtn = locateElement("xpath", payNow);

				String textBase = "(" + fiveSeatsLoc + prop.getProperty("CarSelectionPage.priceBase.xpath") + ")[" + i
						+ "]";

				String textFloat = "(" + fiveSeatsLoc + prop.getProperty("CarSelectionPage.priceNext.xpath") + ")[" + i
						+ "]";
				WebElement eleBasePrice = locateElement("xpath", textBase);
				WebElement eleVehicleFloatPrice = locateElement("xpath", textFloat);
				String baseText = getElementText(eleBasePrice);
				String eleText = getElementText(eleVehicleFloatPrice);
				String trimText = eleText.replace("$", "");
				floatingPrice = Float.parseFloat(trimText);

				click(elePayNowBtn);
				break;
			}
		}
		return new RentalOptionsPage(driver, eachNode);
	}
}
