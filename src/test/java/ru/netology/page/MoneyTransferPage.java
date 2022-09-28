package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class MoneyTransferPage {
    private SelenideElement sumField = $("div[data-test-id=amount] input");
    private SelenideElement accountField = $("span[data-test-id=from] input");
    private SelenideElement button = $("button[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id = error-notification]");


    public DashboardPage successful(DataHelper.CardInfo from, String sum) {

        sumField.setValue(sum);
        accountField.setValue(from.getNumber());
        button.click();
        return new DashboardPage();
    }

    public void unsuccessful() {

        errorNotification.shouldBe(visible);
    }


}

