package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.LocationsSteps;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class LocationsSearchTest extends BaseTest {

    private final LocationsSteps locations = new LocationsSteps();

    @Test()
    public void searchStreetNarrowsResults() {
        locations.openPage()
                .waitForResultsLoaded()
                .selectCity("Batumi")
                .waitForResultsLoaded()
                .searchLocation("Rustaveli")
                .assertResultsNotEmpty()
                .assertAllResultContains("Rustaveli")
                .assertMapRendered()
                .clearSearch()
                .waitForResultsLoaded()
                .assertResultsNotEmpty();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        closeWebDriver();
    }
}
