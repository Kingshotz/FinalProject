package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.ValidInputsDataProvider;
import org.testng.annotations.*;

import ge.tbc.testautomation.steps.MortgageCalculatorSteps;

public class MortgageCalculatorTest extends BaseTest {

    private final MortgageCalculatorSteps calculator = new MortgageCalculatorSteps();

    @Test(dataProvider = "validInputs", dataProviderClass = ValidInputsDataProvider.class,
            description = "Valid/boundary amount+term produce a positive numeric monthly payment")
    public void calculatesMonthlyPaymentForValidInputs(String amount, String term) {
        calculator.openPage()
                .openCalculator()
                .selectCurrencyGel()
                .enterAmount(amount)
                .setPeriod(term, isMobile())
                .assertMonthlyPaymentIsPositiveNumber();
    }
}