package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class StepDefinitions {
    WebDriver driver;

    @Given("I launch the application {string}")
    public void launchApplication(String url) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
    }

    @Then("I verify the title of the page is {string}")
    public void verifyPageTitle(String expectedTitle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='nav-title']")));
        String actualTitle = element.getText();
        Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match!");
    }

    @When("I click on the {string} link")
    public void clickLink(String linkText) {
        WebElement link = driver.findElement(By.xpath("//*[@id='iframe']"));
        // Scroll to the link element
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
        link.click();
    }

    @Then("I switch to the new tab")
    public void switchToNewTab() {
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
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
            System.out.println("Looking for the image element...");
            WebElement image = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@class,'slide-image')]")));
            Assert.assertTrue(image.isDisplayed(), "Image is not displayed!");
            System.out.println("Image is displayed successfully.");
        } catch (TimeoutException e) {
            Assert.fail("Image is not present or visible within the timeout period!");
        }
    }

    @When("I click on the right arrow button")
    public void clickRightArrowButton() {
        driver.findElement(By.xpath("//*[@class='right carousel-control']")).click();
    }

    @Then("I verify the images are changing accordingly")
    public void verifyImageChanges() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement firstImage = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//img[contains(@class, 'slide-image')]")));
        String firstImageSrc = firstImage.getAttribute("src");

        driver.findElement(By.xpath("//*[@class='right carousel-control']")).click();

        WebElement secondImage = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//img[contains(@class, 'slide-image')]")));
        String secondImageSrc = secondImage.getAttribute("src");

        Assert.assertEquals(firstImageSrc, secondImageSrc, "Images are not changing as expected!");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed successfully.");
        }
    }
}
