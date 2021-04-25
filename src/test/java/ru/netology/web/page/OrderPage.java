package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import ru.netology.web.data.DataGenerator.CardInfo;

import java.time.Duration;


public class OrderPage {
    private SelenideElement heading = $("h2").shouldHave(exactText("Путешествие дня"));
    private SelenideElement payment = $(byText("Купить"));
    private SelenideElement paymentDash = $(byText("Оплата по карте"));
    private SelenideElement credit = $(byText("Купить в кредит"));
    private SelenideElement creditDash = $(byText("Кредит по данным карты"));
    private SelenideElement cardNumber = $(".input [maxlength='19']");
    private SelenideElement monthExp = $("[placeholder='08']");
    private SelenideElement yearExp = $("[placeholder='22']");
    private SelenideElement holder = $$(".input__control").get(3);
    private SelenideElement cvc = $("[placeholder='999']");
    private SelenideElement submit = $(".form-field .button");
    private SelenideElement approve = $(byText("Операция одобрена Банком."));
    private SelenideElement decline = $(byText("Ошибка! Банк отказал в проведении операции."));

    public OrderPage() {
        heading.shouldBe(visible);
    }


    public void payment(String number, CardInfo card) {
        payment.click();
        paymentDash.shouldBe(visible, Duration.ofSeconds(3));
        cardNumber.setValue(number);
        monthExp.setValue(card.getMonth());
        yearExp.setValue(card.getYear());
        holder.setValue(card.getName());
        cvc.setValue(card.getCvc());
        submit.click();

    }

    public void credit(String number, CardInfo card) {
        credit.click();
        creditDash.shouldBe(visible, Duration.ofSeconds(3));
        cardNumber.setValue(number);
        monthExp.setValue(card.getMonth());
        yearExp.setValue(card.getYear());
        holder.setValue(card.getName());
        cvc.setValue(card.getCvc());
        submit.click();
    }

    public void approveTransaction() {
        approve.shouldBe(visible, Duration.ofSeconds(10));

    }

    public void declineTransaction() {
        decline.shouldBe(visible, Duration.ofSeconds(10));
    }


}
