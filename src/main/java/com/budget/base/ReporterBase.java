package com.budget.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public abstract class ReporterBase {

	public ExtentHtmlReporter reporter;
	public static ExtentReports extent;
	public ExtentTest test, eachNode;
	File filetest = Paths.get(".").toAbsolutePath().normalize().toFile();
	
	public String testCaseName, testCaseDescription, author, category;

	@BeforeSuite
	public void startReport() {
		reporter = new ExtentHtmlReporter(filetest+"\\reports\\result.html");
		reporter.setAppendExisting(false);
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}

	@BeforeClass
	public void report() throws IOException {
		test = extent.createTest(testCaseName, testCaseDescription);
		test.assignAuthor(author);
		test.assignCategory(category);
	}

	public abstract long takeSnap();

	public void reportStep(String desc, String status, boolean bSnap) {
		MediaEntityModelProvider img = null;
		if (bSnap && !status.equalsIgnoreCase("INFO")) {
			long snapNumber = 100000L;
			snapNumber = takeSnap();
			try {
				img = MediaEntityBuilder.createScreenCaptureFromPath(filetest+"\\reports\\images\\" + snapNumber + ".png").build();
			} catch (IOException e) {
				System.out.println("Error taking screenshot"+e);
			}
		}
		if (status.equalsIgnoreCase("PASS")) {
    		eachNode.pass(desc, img);
			eachNode.log(Status.PASS, desc, img);
		} else if (status.equalsIgnoreCase("FAIL")) {
			eachNode.fail(desc, img);
			eachNode.log(Status.FAIL, "Usage: BOLD TEXT");
			eachNode.log(Status.FAIL, desc, img);
		} else if (status.equalsIgnoreCase("WARNING")) {
			eachNode.warning(desc, img);
			eachNode.log(Status.FAIL, desc, img);
		} else if (status.equalsIgnoreCase("INFO")) {
			eachNode.info(desc);
			eachNode.log(Status.INFO, desc, img);
		}
	}

	public void reportStep(String desc, String status) {
		reportStep(desc, status, true);
	}

	@AfterSuite
	public void stopReport() {
		extent.flush();
	}
}
