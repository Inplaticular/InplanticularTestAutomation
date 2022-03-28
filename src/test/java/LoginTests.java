import com.paulhammant.ngwebdriver.NgWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests {
    private ChromeDriver driver;
    private NgWebDriver ngWebDriver;

    @BeforeTest
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        ngWebDriver = new NgWebDriver(driver);
        ngWebDriver.waitForAngularRequestsToFinish();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    }


    @Test
    public void signUpTest() throws InterruptedException {
        driver.get("http://localhost:5001/identity/login");
        assertElementPresent("signUpLink");
        Thread.sleep(1000);
        elemFormLocator("signUpLink").click();
        assertElementPresent("signUpFormSubmitButton");
        elemFormLocator("usernameInput").sendKeys("TestUser01");
        elemFormLocator("emailInput").sendKeys("testuser01@user.com");
        Thread.sleep(1000);
        elemFormLocator("passwordInput").sendKeys("Password123!");
        elemFormLocator("confirmPasswordInput").sendKeys("Password123!");
        Thread.sleep(1000);
        elemFormLocator("signUpFormSubmitButton").click();
        assertElementPresent("loginFormSubmitButton");
        driver.quit();
    }

    @Test
    public void loginTest() throws InterruptedException {
        driver.get("http://localhost:5001/identity/login");
        elemFormLocator("usernameEmailInput").sendKeys("TestUser01");
        Thread.sleep(1000);
        elemFormLocator("passwordInput").sendKeys("Password123!");
        Thread.sleep(1000);
        elemFormLocator("loginFormSubmitButton").click();
        Thread.sleep(1000);
        assertElementPresent("garden_list_header");
        driver.quit();
    }


    private void assertElementPresent(String locator) {
        By by = new By.ById(locator);
        WebElement element = driver.findElement(by);
        assertThat(ExpectedConditions.presenceOfElementLocated(by)).withFailMessage("Element" + by.toString() + "not present");
    }

    private WebElement elemFormLocator(String locator) {

        By by = new By.ById(locator);
        return driver.findElement(by);
    }
}
