import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Driver {
    private static WebDriver webDriver;
    private static String HIVE_RESULTS_FOLDER = System.getenv("HIVE_RESULTS");
    private static int IMPLICIT_WAIT = 20;
    private static int PAGE_LOAD_TIMEOUT = 60;
    private static String SAFARI_ALLOW_POPUPS = System.getProperty("safari.allow.popups");
    private static String SAFARI_IGNORE_FRAUD_WARNING = System.getProperty("safari.ignore.fraud.warning");
    private static String FULL_RESET = System.getProperty("full.reset");
    private static String AUTO_ACCEPT_ALERTS = System.getProperty("auto.accept.alerts");

    //    launch.timeout=300000
//    new.command.timeout=300
//    appium.url=http://0.0.0.0
//    implicit.wait=10
//    page.load.timeout=60
//    safari.allow.popups=true
//    safari.ignore.fraud.warning=true
//    full.reset=false
//    auto.accept.alerts=true
    public static WebDriver getInstance() {
        return initialiseDriver();
    }

    private static WebDriver initialiseDriver() {
        String UDID = System.getenv("DEVICE_TARGET");
        String DEVICE_NAME = System.getenv("DEVICE_NAME");
        String APPIUM_URL = "http://0.0.0.0" + ":" + System.getenv("APPIUM_PORT") + "/wd/hub";
        System.setProperty("browserCapabilities",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");
        //String browserCapabilities = System.getProperty("browserCapabilities");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("--user-agent=" + browserCapabilities);
        //options.addArguments("-incognito");
        // options.addArguments("--disable-popup-blocking");
        //desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        //Andriod Android
        desiredCapabilities.setCapability("platformName", "ios");
        desiredCapabilities.setCapability("os_version", "10.11.5");
        desiredCapabilities.setCapability("browserName", "Safari");
        desiredCapabilities.setCapability("deviceName", DEVICE_NAME);
        desiredCapabilities.setCapability("udid", UDID);
        desiredCapabilities.setCapability("new.command.timeout", "300");
        desiredCapabilities.setCapability("auto.accept.alerts", true);
        desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        try {
            System.out.println("--------->>>" + APPIUM_URL);
            webDriver = new RemoteWebDriver(new URL(APPIUM_URL), desiredCapabilities);
            webDriver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            webDriver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return webDriver;
    }

    // close the driver
    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                quitDriver();
            }
        });
    }

    public static void quitDriver() {
        if (!hasQuit(webDriver)) {
            takeScreenshot(webDriver);
            webDriver.quit();
        }
    }

    public static boolean hasQuit(WebDriver driver) {
        return ((RemoteWebDriver) driver).getSessionId() == null;
    }

    public static void takeScreenshot(WebDriver driver) {
        WebDriver driverForScreenshot = new Augmenter().augment(driver);
        File file = ((TakesScreenshot) driverForScreenshot).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(HIVE_RESULTS_FOLDER + "/" + "Screenshot.jpg"));
            driverForScreenshot.quit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}