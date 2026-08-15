package ge.tbc.testautomation.steps;

import ge.tbc.testautomation.pages.AppDownloadBannerPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class AppDownloadBannerSteps {

    private final AppDownloadBannerPage page = new AppDownloadBannerPage();

    public AppDownloadBannerSteps openHome() {
        open("/en");
        CommonSteps.acceptIfPresent();
        return this;
    }

    public AppDownloadBannerSteps assertDesktopShowsQrNotButton() {
        page.qrContainer.shouldBe(visible);
        page.downloadButton.shouldNotBe(visible);
        return this;
    }

    public AppDownloadBannerSteps assertMobileShowsButtonNotQr() {
        page.downloadButton.shouldBe(visible);
        page.qrContainer.shouldNotBe(visible);
        return this;
    }

    public String downloadButtonHref() {
        return page.downloadButton.getAttribute("href");
    }
}
