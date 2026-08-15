package ge.tbc.testautomation.steps;

import com.codeborne.selenide.Condition;
import ge.tbc.testautomation.pages.CommonPage;

import java.time.Duration;

public class CommonSteps {
    public static void acceptIfPresent() {
        try {
            CommonPage.acceptAllButton.shouldBe(Condition.visible, Duration.ofSeconds(6)).click();
        } catch (Exception ignored) { }
    }
}
