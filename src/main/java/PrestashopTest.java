import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by dmitriy.galich on 04.04.2017.
 */
public class PrestashopTest {

    private static final String PRESTASHOP_URL = "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/";
    private static final String USER_LOGIN = "webinar.test@gmail.com";
    private static final String USER_PASSWORD = "Xcg7299bnSmMuRLp9ITw";

    private static final By LOGIN_FIELD_LOCATOR = By.id("email");
    private static final By PASSWORD_FIELD_LOCATOR = By.id("passwd");
    private static final By LOGIN_BUTTON_LOCATOR = By.className("ladda-button");
    private static final By AVATAR_SMALL_LOCATOR = By.className("employee_avatar_small");
    private static final By ELEMENT_WITH_TITLE_CLASS_LOCATOR = By.className("title");
    private static final By HEADER_LOGOUT_BUTTON_LOCATOR = By.id("header_logout");

    private static final String HEADER_LOGO_XPATH = ".//*[@id='header_logo']";
    private static final String PART_OF_MENU_ELEMENT_XPATH = "//span[contains(text(), \"";

    public static void main(String[] args) {
        WebDriver driver = WebDriverFactory.initWebDriver("chrome");

        //case A
        driver.get(PRESTASHOP_URL);
        loginOnPrestashop(driver);
        driver.findElement(AVATAR_SMALL_LOCATOR).click();
        driver.findElement(HEADER_LOGOUT_BUTTON_LOCATOR).click();


        //case B
        String pageTitle;
        driver.get(PRESTASHOP_URL);
        loginOnPrestashop(driver);
        waitForPageLoad(driver, HEADER_LOGO_XPATH);

        List<String> menuItemNamesList = getMenuItemNamesList(driver.findElements(ELEMENT_WITH_TITLE_CLASS_LOCATOR));
        for (String s : menuItemNamesList) {
            String currentXpath = PART_OF_MENU_ELEMENT_XPATH + s + "\")]";
            WebElement webElement = driver.findElement(By.xpath(currentXpath));
            webElement.click();
            waitForPageLoad(driver, currentXpath);
            pageTitle = getPageTitle(driver);
            System.out.println(pageTitle);
            refreshPage(driver);
            System.out.println(pageTitle.equals(getPageTitle(driver)));

        }
        driver.close();
    }

    private static void loginOnPrestashop(WebDriver driver) {
        driver.findElement(LOGIN_FIELD_LOCATOR).sendKeys(USER_LOGIN);
        driver.findElement(PASSWORD_FIELD_LOCATOR).sendKeys(USER_PASSWORD);
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
    }

    private static String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    private static void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    private static void waitForPageLoad(WebDriver driver, String locator) {
        WebElement expectedElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));

    }

    private static List<String> getMenuItemNamesList(List<WebElement> webElementList) {
        List<String> menuItemNamesList = new ArrayList<>();
        for (WebElement e : webElementList) {
            if ((e.getText().replace(" ", "").length() > 0)) {
                menuItemNamesList.add(e.getText());
            }
        }
        return menuItemNamesList;
    }


}

