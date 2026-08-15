package ge.tbc.testautomation.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.pages.CommonPage;
import ge.tbc.testautomation.pages.LocationsPage;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class LocationsSteps {

    private final LocationsPage page = new LocationsPage();

    public LocationsSteps openPage() {
        open("/en/atms&branches");
        CommonSteps.acceptIfPresent();
        return this;
    }

    public LocationsSteps waitForResultsLoaded() {
        page.resultsItems.shouldHave(
                CollectionCondition.sizeGreaterThan(0),
                Duration.ofMillis(Constants.LOAD_TIMEOUT_MS));
        return this;
    }

    public LocationsSteps selectCity(String cityName) {
        page.cityDropdown.shouldBe(visible).click();
        page.cityFilterInput.shouldBe(visible).setValue(cityName);
        page.cityOption(cityName).shouldBe(visible).click();
        return this;
    }

    public LocationsSteps filterByAtms() {
        page.tabAtms.shouldBe(visible).click();
        return this;
    }

    public LocationsSteps filterByAll() {
        page.tabAll.shouldBe(visible).click();
        return this;
    }

    public LocationsSteps searchLocation(String query) {
        page.locationSearchInput.shouldBe(visible).setValue(query);
        return this;
    }

    public LocationsSteps clearSearch() {
        page.locationSearchInput.shouldBe(visible).sendKeys(Keys.CONTROL + "a");
        page.locationSearchInput.sendKeys(Keys.DELETE);
        return this;
    }

    public LocationsSteps assertResultsNotEmpty() {
        page.resultsItems.shouldHave(CollectionCondition.sizeGreaterThan(0));
        return this;
    }

    public LocationsSteps assertAllResultContains(String text) {
        page.resultsItems.shouldHave(CollectionCondition.texts(text));
        return this;
    }

    public LocationsSteps assertMapRendered() {
        page.mapWrapper.shouldBe(visible);
        page.mapContainer.shouldBe(visible);
        return this;
    }
}