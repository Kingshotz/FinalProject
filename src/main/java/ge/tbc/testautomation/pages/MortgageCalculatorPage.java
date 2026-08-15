package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MortgageCalculatorPage {

    public final SelenideElement calculatorTab = $(byText("Calculator"));

    public final SelenideElement byAmountTab = $(byText("By Amount"));

    public final SelenideElement amountInput = $("input[placeholder='Income']");

    public final SelenideElement currencyGel = $(byText("GEL"));

    public final SelenideElement periodInputDesktop =
            $("input[type='number']");
    public final SelenideElement periodInputMobile =
            $("input[placeholder='Period']");

    public final SelenideElement monthlyPayment = $(".tbcx-pw-calculated-info__number");

    public final SelenideElement interestRateValue =
            $(byText("Interest rate")).parent().$(".tbcx-pw-calculated-info__rows-item-info");

    public final SelenideElement validationError =
            $("[class*='error'], [class*='invalid'], [class*='helper']");
}
