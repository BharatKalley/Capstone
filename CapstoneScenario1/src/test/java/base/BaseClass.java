package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
    protected WebDriver driver;

    /**
     * Initializes the WebDriver instance using WebDriverManager.
     */
    public void initializeDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Ensure the browser window is maximized
    }

    /**
     * Quits the WebDriver instance if it is not null.
     */
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // Set driver to null to avoid potential memory leaks
        }
    }
}
