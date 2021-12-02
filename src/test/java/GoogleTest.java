import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertFalse;

public class GoogleTest {
    private WebDriver driver;
    private String baseUrl = "https://google.com/ncr";


    @BeforeTest
    public void launchBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @Test
    public void checkFirstImageOnGoogleSearch() {
        WebElement webElement = driver.findElement(By.xpath("//input[@title='Search']"));
        webElement.sendKeys("Epam" + Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//a[@data-hveid='CAIQBA']")).click();
        WebElement image = driver.findElement(By.xpath("//div[@data-id='TdPCogHT2SBWgM']//child::*//child::*/img"));
        assertFalse(image.getAttribute("src").contains("data:image/png"));
    }

    @AfterMethod
    public void takesScreenshot(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                TakesScreenshot scrShot = ((TakesScreenshot) driver);
                File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File("./ScreenShot_Folder/TakesScreenshot.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}