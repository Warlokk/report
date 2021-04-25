package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.netology.web.page.OrderPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.web.data.DBHelper.*;
import static ru.netology.web.data.DataGenerator.*;

public class OrderTest {
    private final CardInfo card = getRandomCard();
    private final int amount = 45000;

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
    }

    @Test
    void shouldPaymentApprovedCard() {
        OrderPage page = new OrderPage();
        page.payment(approvedCard(), card);
        page.approveTransaction();
        assertEquals("APPROVED", getLastPaymentStatus());
        assertEquals(getLastOrderPaymentId(), getLastPaymentId());
        assertEquals(amount, getLastPaymentAmount());
    }

    @Test
    void shouldNotPaymentDeclinedCard() {
        OrderPage page = new OrderPage();
        page.payment(declinedCard(), card);
        page.declineTransaction();
    }

    @Test
    void shouldNotPaymentAnotherCard() {
        OrderPage page = new OrderPage();
        page.payment(anotherCard(), card);
        page.declineTransaction();
    }


    @Test
    void shouldCreditApprovedCard() {
        OrderPage page = new OrderPage();
        page.credit(approvedCard(), card);
        page.approveTransaction();
        assertEquals("APPROVED", getLastCreditStatus());
        assertEquals(getLastOrderCreditId(), getLastCreditId());

    }

    @Test
    void shouldNotCreditDeclinedCard() {
        OrderPage page = new OrderPage();
        page.credit(declinedCard(), card);
        page.declineTransaction();
    }

    @Test
    void shouldNotCreditAnotherCard() {
        OrderPage page = new OrderPage();
        page.credit(anotherCard(), card);
        page.declineTransaction();
    }

}
