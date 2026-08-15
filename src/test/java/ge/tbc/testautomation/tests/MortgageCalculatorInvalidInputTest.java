package ge.tbc.testautomation.tests;

import org.testng.annotations.*;

import ge.tbc.testautomation.steps.MortgageCalculatorSteps;

public class MortgageCalculatorInvalidInputTest extends BaseTest {

    private final MortgageCalculatorSteps calculator = new MortgageCalculatorSteps();

    @Test(description = "Invalid input (amount = 0) shows a validation error and does not crash")
    public void handlesInvalidAmountGracefully() {
        calculator.openPage()
                .openCalculator()
                .selectCurrencyGel()
                .enterAmount("0")
                .assertValidationErrorShown()
                .assertCalculatorStillUsable();
    }
}
