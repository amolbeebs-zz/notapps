import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Driver {
    private static String CHOICE_OF_DRIVER = System.getProperty("driver");
    private static WebDriver webDriver;
    private static String PLATFORM_NAME= System.getProperty("platform.name");
    private static String BROWSER_NAME= System.getProperty("browser.name");
    private static String PLATFORM_VERSION= System.getProperty("platform.version");
    private static String DEVICE_NAME= System.getProperty("device.name");
    private static String LAUNCH_TIMEOUT= System.getProperty("launch.timeout");
    private static String NEW_COMMAND_TIMEOUT= System.getProperty("new.command.timeout");
    private static String APPIUM_PORT = System.getenv("APPIUM_PORT");
    private static String APPIUM_URL= System.getProperty("appium.url")+":"+APPIUM_PORT+"/wd/hub";
    private static int IMPLICIT_WAIT=Integer.parseInt(System.getProperty("implicit.wait")) ;
    private static int PAGE_LOAD_TIMEOUT = Integer.parseInt(System.getProperty("page.load.timeout"));
    private static String SAFARI_ALLOW_POPUPS= System.getProperty("safari.allow.popups");
    private static String SAFARI_IGNORE_FRAUD_WARNING= System.getProperty("safari.ignore.fraud.warning");
    private static String FULL_RESET= System.getProperty("full.reset");
    private static String AUTO_ACCEPT_ALERTS= System.getProperty("auto.accept.alerts");




    public static WebDriver getInstance() {
        return initialiseDriver();
    }


    private static WebDriver initialiseDriver() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT, LAUNCH_TIMEOUT);  //ms
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, NEW_COMMAND_TIMEOUT); //sec

        capabilities.setCapability("safariIgnoreFraudWarning", SAFARI_IGNORE_FRAUD_WARNING);
        capabilities.setCapability("safariAllowPopups", SAFARI_ALLOW_POPUPS);
        capabilities.setCapability("fullReset", FULL_RESET);  // for iOS only

        capabilities.setCapability("autoAcceptAlerts", AUTO_ACCEPT_ALERTS);
//		capabilities.setCapability("orientation", "PORTRAIT");
//		capabilities.setCapability("showIOSLog", true);
//		capabilities.setCapability("noReset", true);


        try {
            webDriver = new IOSDriver(new URL(APPIUM_URL), capabilities);
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


    public static void quitDriver(){

        if(!hasQuit(webDriver)){
            webDriver.quit();
        }
    }

    public static boolean hasQuit(WebDriver driver) {
        return ((RemoteWebDriver)driver).getSessionId() == null;
    }


}
