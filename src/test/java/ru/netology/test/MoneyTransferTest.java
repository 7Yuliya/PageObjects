package ru.netology.test;


import lombok.val;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.MoneyTransferPage;


import static com.codeborne.selenide.Selenide.open;

import static org.junit.jupiter.api.Assertions.*;


public class MoneyTransferTest {
    int balanceFirstCard;
    int balanceSecondCard;
    int endBalanceFirstCard;
    int endBalanceSecondCard;
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


    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {

        sum = 200;
        balanceFirstCard = dashboardPage.getCardBalance("1");
        balanceSecondCard = dashboardPage.getCardBalance("2");
        dashboardPage.getMoneyTransferOnFirstCard();
        val moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.successful(DataHelper.getCardInfo("2"), "200");
        endBalanceFirstCard = dashboardPage.getCardBalance("1");
        endBalanceSecondCard = dashboardPage.getCardBalance("2");

        assertEquals(balanceFirstCard + sum, endBalanceFirstCard);
        assertEquals(balanceSecondCard - sum, endBalanceSecondCard);
    }


    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {

        sum = 200;
        balanceFirstCard = dashboardPage.getCardBalance("1");
        balanceSecondCard = dashboardPage.getCardBalance("2");
        dashboardPage.getMoneyTransferOnSecondCard();
        val moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.successful(DataHelper.getCardInfo("1"), "200");
        endBalanceFirstCard = dashboardPage.getCardBalance("1");
        endBalanceSecondCard = dashboardPage.getCardBalance("2");

        assertEquals(balanceFirstCard - sum, endBalanceFirstCard);
        assertEquals(balanceSecondCard + sum, endBalanceSecondCard);
    }


    @Test
    public void shouldReloadBalance() {
        balanceFirstCard = dashboardPage.getCardBalance("1");
        balanceSecondCard = dashboardPage.getCardBalance("2");
        dashboardPage.reloadBalance();
        endBalanceFirstCard = dashboardPage.getCardBalance("1");
        endBalanceSecondCard = dashboardPage.getCardBalance("2");

        assertEquals(balanceFirstCard, endBalanceFirstCard);
        assertEquals(balanceSecondCard, endBalanceSecondCard);


    }

    @Test
    public void shouldCancelBalance() {
        balanceFirstCard = dashboardPage.getCardBalance("1");
        balanceSecondCard = dashboardPage.getCardBalance("2");
        dashboardPage.getMoneyTransferOnFirstCard();
        dashboardPage.cancelBalance();
        endBalanceFirstCard = dashboardPage.getCardBalance("1");
        endBalanceSecondCard = dashboardPage.getCardBalance("2");

        assertEquals(balanceFirstCard, endBalanceFirstCard);
        assertEquals(balanceSecondCard, endBalanceSecondCard);

    }


    @Test
    public void shouldNotTransferMoneyMoreThanCardBalance() {

        String sumAboveBalance = String.valueOf(dashboardPage.getCardBalance("1") + 200);
        endBalanceFirstCard = dashboardPage.getCardBalance("1");
        endBalanceSecondCard = dashboardPage.getCardBalance("2");
        dashboardPage.getMoneyTransferOnFirstCard();
        var moneyTransferPage = new MoneyTransferPage();
        moneyTransferPage.successful(DataHelper.getCardInfo("1"), sumAboveBalance);
        moneyTransferPage.unsuccessful();
    }

}

