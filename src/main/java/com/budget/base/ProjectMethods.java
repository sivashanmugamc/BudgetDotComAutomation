package com.budget.base;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

public class ProjectMethods extends SeleniumBase {

	public String dataSheetName;

	@BeforeSuite
	public void loadObj() {
		loadObjects();
	}

	@BeforeMethod
	public void beforeMethod() {

		eachNode = test.createNode(testCaseName);
		startApp("chrome", "https://www.budget.com/");
	}

	@AfterMethod
	public void afterMethod() {
		closeBrowser();
	}

	@DataProvider(name = "getData")
	public Object[][] fetchData() throws IOException {
		return DataLibrary.readExcelData(dataSheetName);
	}

	@AfterSuite
	public void unloadObj() {
		unloadObjects();
		closeAllBrowsers();
	}

}
