package ge.tbc.testautomation.data;

import org.testng.annotations.DataProvider;

public final class ValidInputsDataProvider {
    private ValidInputsDataProvider() {
    }
    @DataProvider(name = "validInputs")
    public static Object[][] validInputs() {
        return new Object[][]{
                {"50000", "10"},
                {"2", "1"},
                {"500000", "20"},
        };
    }
}
