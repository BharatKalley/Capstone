package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
    protected WebDriver driver;

    /**
     * Initializes the WebDriver instance using WebDriverManager.
     */
    @Before
    public void initializeDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Ensure the browser window is maximized
    }

    /**
     * Quits the WebDriver instance if it is not null.
     */
    @After
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // Set driver to null to avoid potential memory leaks
        }
    }
}
