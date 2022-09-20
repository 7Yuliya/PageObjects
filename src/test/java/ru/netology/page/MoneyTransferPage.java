package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class MoneyTransferPage {
        private SelenideElement sumField = $("div[data-test-id=amount] input");
        private SelenideElement accountField = $("span[data-test-id=from] input");
        private SelenideElement button = $("button[data-test-id=action-transfer]");
        private SelenideElement errorNotification = $("[data-test-id = error-notification]");

        public DashboardPage successful(String sum, String card) {
                sumField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                sumField.setValue(sum);
                accountField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                accountField.setValue(card);
                sleep(5000);
                button.click();
                return new DashboardPage();
        }

        public void unsuccessful(String sum, String card) {
                sumField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                sumField.setValue(sum);
                errorNotification.shouldBe(visible);
        }
}

