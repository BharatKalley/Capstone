package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class StepDefinitions {

    private static final Logger logger = LogManager.getLogger(StepDefinitions.class);
    WebDriver driver;

    @Given("I launch the application {string}")
    public void launchApplication(String url) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        logger.info("Application launched successfully with url: {}", url);
    }

    @Then("I verify the title of the page is {string}")
    public void verifyPageTitle(String expectedTitle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='nav-title']")));
        String actualTitle = element.getText();
        Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match!");
        logger.info("Page title verified successfully. Expected: {}, Actual: {}", expectedTitle, actualTitle);
    }

    @When("I click on the {string} link")
    public void clickLink(String linkText) {
        WebElement link = driver.findElement(By.xpath("//*[@id='iframe']"));
        // Scroll to the link element
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
        link.click();
        logger.info("Clicked on the link: {}", linkText);
    }

    @Then("I switch to the new tab")
    public void switchToNewTab() {
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getTitle().contains("IFrame")) {
                logger.info("Switched to the new tab with title: {}", driver.getTitle());
                Assert.assertTrue(driver.getTitle().contains("IFrame"), "Not switched to the new tab!");
                break;
            }
        }
    }

    @Then("I verify the image is present")
    public void verifyImagePresence() {

        driver.manage().window().maximize();
        driver.get("https://webdriveruniversity.com/IFrame/index.html");

        WebElement iframe = driver.findElement(By.id("frame"));
        driver.switchTo().frame(iframe);

        // Wait for the image to be present and visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            logger.info("Looking for the image element...");
            WebElement image = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@class,'slide-image')]")));
            Assert.assertTrue(image.isDisplayed(), "Image is not displayed!");
            logger.info("Image is displayed successfully.");
        } catch (TimeoutException e) {
            Assert.fail("Image is not present or visible within the timeout period!");
        }
    }

    @When("I click on the right arrow button")
    public void clickRightArrowButton() {
        driver.findElement(By.xpath("//*[@class='right carousel-control']")).click();
        logger.info("Clicked on the right arrow button.");
    }

    @Then("I verify the images are changing accordingly")
    public void verifyImageChanges() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement firstImage = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//img[contains(@class, 'slide-image')]")));
        String beforeBase64 = firstImage.getScreenshotAs(OutputType.BASE64);
        driver.findElement(By.xpath("//*[@class='right carousel-control']")).click();

        WebElement secondImage = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//img[contains(@class, 'slide-image')]")));

        String afterBase64 = secondImage.getScreenshotAs(OutputType.BASE64);

        Assert.assertNotEquals(beforeBase64, afterBase64, "Images are not changing as expected!");
        logger.info("Images are changing successfully.");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed successfully.");
        }
    }
}
