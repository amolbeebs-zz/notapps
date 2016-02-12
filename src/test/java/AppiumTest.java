import static java.lang.ClassLoader.getSystemResourceAsStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

public class AppiumTest {
    Page page;
    private static String USER_NAME = "amolbeebs@mailinator.com123";
    private static String PASSWORD = "beebstest";

    @Before
    public void setup() throws IOException {
        Properties testRunnerProperties = new Properties();

            testRunnerProperties.load(getSystemResourceAsStream("test.properties"));
            for (String key : testRunnerProperties.stringPropertyNames()) {
                System.setProperty(key, testRunnerProperties.getProperty(key));
            }

        page = new Page();


    }

    @Test
    public void testICanViewNotificationsAfterSignIn(){

        page.openBeebs();
        page.viewNotifications();
        page.signinWithCredentials(USER_NAME,PASSWORD);
        page.viewNotifications();

    }

    @After
    public void tearDown(){
        page.quitDriver();
    }

}
