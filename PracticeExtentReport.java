package practiceExtent;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.Logs;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class PracticeExtentReport {

	public WebDriver driver;
	public ExtentHtmlReporter html;
	public ExtentReports extent;
	public ExtentTest extenttest;

	@BeforeTest
	public void SetupExtent() {

		html = new ExtentHtmlReporter("./test-output/learn_automation1.html");
		extent = new ExtentReports();
		extent.attachReporter(html);

	}

	@BeforeMethod
	public void seteUp() {
		System.setProperty("Webdriver.chrome.driver",
				"C:\\Users\\Nilesh\\eclipse-workspace2\\org.test\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();

	}

	@Test
	public void openurl() {
		driver.get("https://www.irctc.co.in/nget/train-search");

		String title = driver.getTitle();
		AssertJUnit.assertEquals("IRCTC Next Generation eTicketing System1", title);
	}

	@AfterTest
	public void EndTestandReport() {
		extent.flush();
		driver.close();

	}

	public static String snapshot(WebDriver driver) throws IOException {
		String datename = new SimpleDateFormat("yyMMddhhmmss").format(new Date());
		File SrcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// String outputpath = "./test-output/FailedTestScreensots/" + datename +".png";
		String outputpath = System.getProperty("./test-output/FailedTestScreensots") + datename + ".png";

		File Destfile = new File(outputpath);
		FileUtils.copyFile(SrcFile, Destfile);
		System.out.println(outputpath);
		return outputpath;
	}

	@AfterMethod
	public void beginExtent(ITestResult result) throws IOException, InterruptedException {

		extenttest = extent.createTest("openurl");
		System.out.println("success");

		String screenshotpath = PracticeExtentReport.snapshot(driver);
		extenttest.log(Status.FAIL, "Title did not match");
		extenttest.addScreenCaptureFromPath(screenshotpath);
		Thread.sleep(8000);

	}
}