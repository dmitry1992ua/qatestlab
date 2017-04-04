import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by dmitriy.galich on 04.04.2017.
 */
public class WebDriverFactory {

    public static WebDriver initWebDriver(String browserName) {
        WebDriver driver;
        switch (browserName) {
            case "chrome" :
                String property = System.getProperty("user.dir") + "/drivers/chromedriver.exe";
                System.setProperty("webdriver.chrome.driver", property);
                driver = new ChromeDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return driver;
    }
}
