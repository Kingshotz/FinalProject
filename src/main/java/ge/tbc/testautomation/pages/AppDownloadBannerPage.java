package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AppDownloadBannerPage {

    public final SelenideElement section = $("[class*='app-download-banner-section']");
    public final SelenideElement qrContainer =
            $(".tbcx-pw-app-download-banner-section__qr-container");
    public final SelenideElement downloadButton =
            $(".tbcx-pw-app-download-banner-section__download-button");
}
