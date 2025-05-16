package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import util.DriverFactory;

public class Hooks {

    /** create a browser before every scenario (order=0 ⇒ run first) */
    @Before(order = 0)
    public void setUp() {
        DriverFactory.initDriver();
    }

    /** close it afterwards (order=0 ⇒ run last) */
    @After(order = 0)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
