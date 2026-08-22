package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Properties properties = new Properties();

    public static String getProperty(String key) {
        try {
            if (properties.isEmpty()) {
                properties.load(new FileInputStream("src/test/resources/config.properties"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return properties.getProperty(key);
    }

    public static void initDriver() {
        WebDriver drv = new ChromeDriver();
        drv.manage().window().maximize();
        drv.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.set(drv);
    }

    public static WebDriver getDriver() { return driver.get(); }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}