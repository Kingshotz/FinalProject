package ge.tbc.testautomation.steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.pages.MortgageCalculatorPage;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class MortgageCalculatorSteps {
    private final MortgageCalculatorPage page = new MortgageCalculatorPage();

    public MortgageCalculatorSteps openPage() {
        open("/en/loans/mortgage");
        CommonSteps.acceptIfPresent();
        return this;
    }

    public MortgageCalculatorSteps openCalculator() {
        page.calculatorTab.shouldBe(visible).click();
        page.byAmountTab.shouldBe(visible);
        return this;
    }

    public MortgageCalculatorSteps enterAmount(String amount) {
        page.amountInput.shouldBe(visible).click();
        page.amountInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        page.amountInput.setValue(amount);
        return this;
    }

    public MortgageCalculatorSteps setPeriod(String years, boolean isMobile) {
        var periodInput = isMobile ? page.periodInputMobile : page.periodInputDesktop;
        periodInput.shouldBe(visible).click();
        periodInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        periodInput.setValue(years);
        return this;
    }

    public MortgageCalculatorSteps selectCurrencyGel() {
        page.currencyGel.shouldBe(visible).click();
        return this;
    }

    public MortgageCalculatorSteps assertMonthlyPaymentIsPositiveNumber() {
        page.monthlyPayment.shouldBe(visible);
        double value = parseMonthlyPayment();
        Assert.assertTrue(value > 0,
                "Monthly payment should be a positive number but was: " + value);
        return this;
    }

    public double parseMonthlyPayment() {
        String raw = page.monthlyPayment.shouldBe(visible).getText();
        String cleaned = raw.replaceAll("[^0-9.]", "");
        Assert.assertFalse(cleaned.isEmpty(),
                "Monthly payment field is not numeric. Raw text: '" + raw + "'");
        return Double.parseDouble(cleaned);
    }

    public MortgageCalculatorSteps assertValidationErrorShown() {
        page.validationError.shouldBe(visible, java.time.Duration.ofSeconds(5))
                .shouldHave(Condition.not(Condition.empty));
        return this;
    }

    public MortgageCalculatorSteps assertCalculatorStillUsable() {
        page.amountInput.shouldBe(visible).shouldBe(Condition.enabled);
        return this;
    }
}
