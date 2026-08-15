package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.steps.LocationsSteps;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class FilterLocationsTest extends BaseTest {

    private final LocationsSteps locations = new LocationsSteps();

    @Test(description = "Scenario 1: filter locations by city and ATM type")
    public void filterByCityAndAtmType() {
        locations.openPage()
                .waitForResultsLoaded()
                .selectCity("Batumi")
                .waitForResultsLoaded()
                .assertResultsNotEmpty()
                .filterByAtms()
                .waitForResultsLoaded()
                .assertResultsNotEmpty()
                .assertMapRendered();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        closeWebDriver();
    }
}