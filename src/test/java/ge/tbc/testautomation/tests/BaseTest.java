package ge.tbc.testautomation.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class BaseTest {

    private static volatile boolean configured = false;

    private static final ThreadLocal<String> platformHolder = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void configureSharedSettings() {
        if (configured) return;
        synchronized (BaseTest.class) {
            if (configured) return;

            Configuration.baseUrl = "https://tbcbank.ge";
            Configuration.browser = "chrome";
            Configuration.pageLoadStrategy = "eager";
            Configuration.timeout = 15_000;
            Configuration.pageLoadTimeout = 30_000;

            configured = true;
        }
    }
    @BeforeMethod(alwaysRun = true)
    @Parameters("platform")
    public void setPlatformWindowSize(@Optional("desktop") String platformParam) {
        String platform = System.getProperty("platform", platformParam).toLowerCase();
        platformHolder.set(platform);

        Dimension size = "mobile".equals(platform)
                ? new Dimension(390, 844)
                : new Dimension(1440, 900);

        Selenide.open("about:blank");
        WebDriverRunner.getWebDriver().manage().window().setSize(size);

        System.out.println("[Selenide] thread=" + Thread.currentThread().getId()
                + " platform=" + platform + " size=" + size);
    }

    protected boolean isMobile() {
        return "mobile".equals(platformHolder.get());
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriverRunner.closeWebDriver();
        }
        platformHolder.remove();
    }
}