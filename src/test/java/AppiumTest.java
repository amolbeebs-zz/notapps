import static java.lang.ClassLoader.getSystemResourceAsStream;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

public class AppiumTest {
    Page page;


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
        page.signinWithCredentials("amolbeebs@mailinator.com","beebstest123");
        page.viewNotifications();

    }


}
