package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CommonPage {
    public static final SelenideElement acceptAllButton = $(byText("Accept All"));
}
