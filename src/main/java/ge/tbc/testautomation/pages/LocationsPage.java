package ge.tbc.testautomation.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LocationsPage {

    public final SelenideElement cityDropdown = $(byText("Select a city")).closest("div");
    public final SelenideElement cityFilterInput = $("input[placeholder*='Filter regions']");

    public final SelenideElement locationSearchInput =
            $("input[placeholder*='Specify the desired location']");

    public final SelenideElement tabAll = $(byText("All"));
    public final SelenideElement tabAtms = $(byText("ATMs"));

    public final ElementsCollection resultsItems =
            $$(".tbcx-pw-atm-branches-section__list-item");

    public final SelenideElement mapWrapper =
            $(".tbcx-pw-atm-branches-section__map-wrapper");
    public final SelenideElement mapContainer = $(".gm-style");

    public SelenideElement cityOption(String cityName) {
        return $(byText(cityName));
    }
}