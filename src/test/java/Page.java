import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
    private WebDriver driver = Driver.getInstance();
    private String URL = System.getProperty("test.url");


    public void viewNotifications() {
        System.out.println("I am in view notifications");
        WebElement notificationsBell;

        notificationsBell = driver.findElement(By.cssSelector("[class^='js-notification-link']"));
        notificationsBell.click();
        waitForPageLoad();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement okGetMeStartedButton1;
        okGetMeStartedButton1 = driver.findElement(By.cssSelector("div[id='start-onboarding']"));
        okGetMeStartedButton1.click();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitForPageLoad();
        takeScreenshot();
    }


    public void openBeebs() {
        System.out.println("I am in setting URL");
        driver.get(URL);
        System.out.println("URL used.."+URL);
    }


    public void signinWithCredentials(String ID, String password) {
        //click on signin btn
        System.out.println("I am in signingin");
        WebElement idctaSignin;
        idctaSignin = driver.findElement(By.id("idcta-link"));
        idctaSignin.click();
        waitForPageLoad();

        System.out.println("I am in signingin :::"+driver.getCurrentUrl());
        enterID(ID);
        enterPassword(password);

        waitForPageLoad();
    }

    private void submitCredentials() {
        System.out.println("I am in submit cred");
        WebElement submitButton = driver.findElement(By.id("bbcid_submit_button"));
        submitButton.click();
    }

    private WebElement siginButton() {

        WebElement siginButton = driver.findElement(By.cssSelector(".notifications-wide-spread .notification-sign-in > a"));
        return siginButton;
    }

    private void enterID(String ID) {

        WebElement emailAddressField = driver.findElement(By.id("username-input"));
        emailAddressField.sendKeys(ID);
    }

    private void enterPassword(String password) {

        WebElement passwordField= driver.findElement(By.id("password-input"));
        passwordField.sendKeys(password);
        passwordField.submit();

    }

    public void takeScreenshot() {

        Driver.takeScreenshot(driver);

    }

    public void waitForPageLoad() {

        Wait<WebDriver> wait = new WebDriverWait(driver, 30);
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                System.out.println("Current Window State       : "
                        + String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
                return String
                        .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                        .equals("complete");
            }
        });
    }
}