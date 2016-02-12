import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Page {
    private WebDriver driver = Driver.getInstance();
    private String URL = System.getProperty("test.url");


    public void viewNotifications() {
        WebElement notificationsBell;
        notificationsBell = driver.findElement(By.id("notification-link"));

        notificationsBell.click();
    }


    public void openBeebs() {

        driver.get(URL);
        System.out.println();
    }


    public void signinWithCredentials(String ID, String password) {
        siginButton().click();
        enterID(ID);
        enterPassword(password);
        submitCredentials();
    }

    private void submitCredentials() {
        WebElement submitButton = driver.findElement(By.id("bbcid_submit_button"));
        submitButton.click();
    }

    private WebElement siginButton() {

        WebElement siginButton = driver.findElement(By.cssSelector(".notifications-wide-spread .notification-sign-in > a"));
        return siginButton;
    }

    private void enterID(String ID) {

        WebElement emailAddressField = driver.findElement(By.id("bbcid_unique"));
        emailAddressField.sendKeys(ID);
    }

    private void enterPassword(String password) {

        WebElement passwordField= driver.findElement(By.id("bbcid_password"));
        passwordField.sendKeys(password);

    }

    public void takeScreenshot() {

        Driver.takeScreenshot(driver);

    }

    public void quitDriver(){
        Driver.quitDriver();
    }
}
