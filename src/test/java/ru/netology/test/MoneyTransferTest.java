package ru.netology.test;


import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;

import static org.junit.jupiter.api.Assertions.*;


public class MoneyTransferTest {
    int balance1;
    int balance2;
    int endBalance1;
    int endBalance2;
    int sum;
    DashboardPage dashboardPage;

    @BeforeEach
    void SetUp() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        balance1 = dashboardPage.getFirstBalance();
        balance2 = dashboardPage.getSecondBalance();


    }

    @Test
    @DisplayName("Перевод денег сo второй карты на первую")
    void shouldTransferMoneyFromSecondToFirstCard() {
        sum = 200;
        val page = dashboardPage.click(dashboardPage.card1);
        val card = DataHelper.getSecondCard().getNumber();
        val dashboardPage2 = page.successful(Integer.toString(sum), card);
        endBalance1 = dashboardPage2.getFirstBalance();
        endBalance2 = dashboardPage2.getSecondBalance();

        assertEquals(balance1 + sum, endBalance1);
        assertEquals(balance2 - sum, endBalance2);
    }

    @Test
    @DisplayName("Перевод денег с первой карты на вторую")
    void shouldTransferMoneyFromFirstToSecondCard() {
        sum = 200;
        val page = dashboardPage.click(dashboardPage.card2);
        val card = DataHelper.getFirstCard().getNumber();
        val dashboardPage2 = page.successful(Integer.toString(sum), card);
        endBalance1 = dashboardPage2.getFirstBalance();
        endBalance2 = dashboardPage2.getSecondBalance();

        assertEquals(balance1 - sum, endBalance1);
        assertEquals(balance2 + sum, endBalance2);
    }


}

