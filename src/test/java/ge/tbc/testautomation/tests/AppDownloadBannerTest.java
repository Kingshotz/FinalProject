package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.AppDownloadBannerSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppDownloadBannerTest extends BaseTest {

    private final AppDownloadBannerSteps banner = new AppDownloadBannerSteps();

    @Test(description = "App download banner shows QR (desktop) or direct link (mobile), never both")
    public void bannerAdaptsDownloadMechanismToPlatform() {
        banner.openHome();

        if (isMobile()) {
            banner.assertMobileShowsButtonNotQr();
            String href = banner.downloadButtonHref();
            Assert.assertTrue(href != null && href.contains("onelink.me"),
                    "Expected a smart-link to the app store, got: " + href);
        } else {
            banner.assertDesktopShowsQrNotButton();
        }
    }
}
