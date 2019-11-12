import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class ExceptionsTest {

    private RemoteWebDriver driver;
    private static final String ACCESS_KEY = System.getenv("SEETEST_IO_ACCESS_KEY");
    private WebDriverWait wait;

    public ExceptionsTest () {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("generateReport", true);
        String title = "Selenium Test";
        dc.setCapability("accessKey", ACCESS_KEY);
        String Selenium_Hub = "https://cloud.seetest.io/wd/hub/";
        dc.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        dc.setCapability("testName", title);

        try {
            driver = new RemoteWebDriver(new URL(Selenium_Hub), dc);
            wait = new WebDriverWait(driver, 10);

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + Selenium_Hub);
        } catch (SessionNotCreatedException e) {
            System.out.println("Wrong SeeTest access key" + ACCESS_KEY);
        }
    }

    @Test
    public void myClass() {
        try {
            //A code that may cause exception goes here
        } catch(Exception e) {
            //What to do in a case of exception
        } finally {
            //Add here a code, that has to be executed anyway
        }
    }


    @Test
    public void testSelenium() {
        driver.get("https://seetest.io");
        try {
            assertEquals(driver.findElement(By.xpath("/html/body/section/div/div[1]/div[1]/div")).getText(), "START TESTING NOW");
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Can't find \"START TESTING NOW\" button");

        }
    }

    @Test
    public void testSelenium_wait() {
        driver.get("https://seetest.io");
        try {
            driver.findElement(By.xpath("/html/body/section/div/div[1]/div[1]/div/a")).click();
        } catch (TimeoutException e) {
            wait.until( ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='register']")));
            driver.findElement(By.xpath("/html/body/section/div/div[1]/div[1]/div/a")).click();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Can't find \"START TESTING NOW\" button", e);
        }
    }

    @Test
    public void testSelenium2() {
        driver.get("https://seetest.io");
        try {
            driver.findElement(By.xpath("/html/body/section/div/div[1]/div[1]/div/a")).click();
        } catch (NoSuchElementException e) {
            assertEquals(true, true);
            return;
        }
        assertEquals(true, false);
    }



    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
