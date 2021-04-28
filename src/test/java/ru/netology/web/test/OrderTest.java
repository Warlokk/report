package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.page.OrderPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.web.data.DBHelper.*;
import static ru.netology.web.data.DataGenerator.*;


public class OrderTest {
    private CardInfo card;
    private CardInfo invalidCard;
    private OrderPage page;
    private final int amount = 45000;
    private static Boolean mySql = false; // set to "true" yo use "mySql"

    public static Boolean getMySql() {
        return mySql;
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());

    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        page = new OrderPage();
        card = getRandomCard();
    }

    @Nested
    @DisplayName("Payment functionality test")
    class PaymentTest {

        @Test
        void shouldPaymentApprovedCard() {
            page.payment(approvedCard(), card);
            page.approveTransaction();
            assertEquals("APPROVED", getLastPaymentStatus());
            assertEquals(getLastOrderPaymentId(), getLastPaymentId());
            assertEquals(amount, getLastPaymentAmount());
        }

        @Test
        void shouldNotPaymentDeclinedCard() {
            val lastOrderPaymentId = getLastOrderPaymentId();
            page.payment(declinedCard(), card);
            page.declineTransaction();
            assertEquals("DECLINED", getLastPaymentStatus());
            assertEquals(lastOrderPaymentId, getLastOrderPaymentId());
        }

        @Test
        void shouldNotPaymentAnotherCard() {
            val lastOrderPaymentId = getLastOrderPaymentId();
            page.payment(anotherCard(), card);
            page.declineTransaction();
            assertEquals(lastOrderPaymentId, getLastOrderPaymentId());
        }

        @Test
        void shouldNotPaymentInvalidYear() {
            val lastOrderPaymentId = getLastOrderPaymentId();
            invalidCard = new CardInfo(card.getName(), card.getMonth(), "20", card.getCvc());
            page.payment(approvedCard(), invalidCard);
            page.invalidYear();
            assertEquals(lastOrderPaymentId, getLastOrderPaymentId());
        }

        @Test
        void shouldNotPaymentInvalidMonth() {
            val lastOrderPaymentId = getLastOrderPaymentId();
            invalidCard = new CardInfo(card.getName(), "03", "21", card.getCvc());
            page.payment(approvedCard(), invalidCard);
            assertEquals(lastOrderPaymentId, getLastOrderPaymentId());
            page.invalidMonth();
        }

        @Test
        void shouldNotPaymentInvalidName() {
            val lastOrderPaymentId = getLastOrderPaymentId();
            invalidCard = new CardInfo("Иван Иванов", card.getMonth(), card.getYear(), card.getCvc());
            page.payment(approvedCard(), invalidCard);
            page.invalidName();
            assertEquals(lastOrderPaymentId, getLastOrderPaymentId());
        }

        @Test
        void shouldNotPaymentEmptyNumber() {
            val lastOrderPaymentId = getLastOrderPaymentId();
            page.payment("", card);
            page.emptyField();
            assertEquals(lastOrderPaymentId, getLastOrderPaymentId());
        }

        @Test
        void shouldNotPaymentEmptyName() {
            val lastOrderPaymentId = getLastOrderPaymentId();
            invalidCard = new CardInfo("", card.getMonth(), card.getYear(), card.getCvc());
            page.payment(approvedCard(), invalidCard);
            page.emptyField();
            assertEquals(lastOrderPaymentId, getLastOrderPaymentId());
        }

        @Test
        void shouldNotPaymentEmptyMonth() {
            val lastOrderPaymentId = getLastOrderPaymentId();
            invalidCard = new CardInfo(card.getName(), "", card.getYear(), card.getCvc());
            page.payment(approvedCard(), invalidCard);
            page.emptyField();
            assertEquals(lastOrderPaymentId, getLastOrderPaymentId());
        }

        @Test
        void shouldNotPaymentEmptyYear() {
            val lastOrderPaymentId = getLastOrderPaymentId();
            invalidCard = new CardInfo(card.getName(), card.getMonth(), "", card.getCvc());
            page.payment(approvedCard(), invalidCard);
            page.emptyField();
            assertEquals(lastOrderPaymentId, getLastOrderPaymentId());
        }

        @Test
        void shouldNotPaymentEmptyCvc() {
            val lastOrderPaymentId = getLastOrderPaymentId();
            invalidCard = new CardInfo(card.getName(), card.getMonth(), card.getMonth(), "");
            page.payment(approvedCard(), invalidCard);
            page.emptyField();
            assertEquals(lastOrderPaymentId, getLastOrderPaymentId());
        }
    }

    @Nested
    @DisplayName("Credit functionality test")
    public class CreditTest {

        @Test
        void shouldCreditApprovedCard() {

            page.credit(approvedCard(), card);
            page.approveTransaction();
            assertEquals("APPROVED", getLastCreditStatus());
            assertEquals(getLastOrderCreditId(), getLastCreditId());

        }

        @Test
        void shouldNotCreditDeclinedCard() {
            val lastOrderCreditId = getLastOrderCreditId();
            page.credit(declinedCard(), card);
            page.declineTransaction();
            assertEquals("DECLINED", getLastCreditStatus());
            assertEquals(lastOrderCreditId, getLastOrderCreditId());
        }

        @Test
        void shouldNotCreditAnotherCard() {
            val lastOrderCreditId = getLastOrderCreditId();
            page.credit(anotherCard(), card);
            page.declineTransaction();
            assertEquals(lastOrderCreditId, getLastOrderCreditId());
        }

        @Test
        void shouldNotCreditInvalidYear() {
            val lastOrderCreditId = getLastOrderCreditId();
            invalidCard = new CardInfo(card.getName(), card.getMonth(), "20", card.getCvc());
            page.credit(approvedCard(), invalidCard);
            page.invalidYear();
            assertEquals(lastOrderCreditId, getLastOrderCreditId());
        }

        @Test
        void shouldNotCreditInvalidMonth() {
            val lastOrderCreditId = getLastOrderCreditId();
            invalidCard = new CardInfo(card.getName(), "03", "21", card.getCvc());
            page.credit(approvedCard(), invalidCard);
            assertEquals(lastOrderCreditId, getLastOrderCreditId());
            page.invalidMonth();
        }

        @Test
        void shouldNotCreditInvalidName() {
            val lastOrderCreditId = getLastOrderCreditId();
            invalidCard = new CardInfo("Иван Иванов", card.getMonth(), card.getYear(), card.getCvc());
            page.credit(approvedCard(), invalidCard);
            page.invalidName();
            assertEquals(lastOrderCreditId, getLastOrderCreditId());
        }

        @Test
        void shouldNotCreditEmptyNumber() {
            val lastOrderCreditId = getLastOrderCreditId();
            page.credit("", card);
            page.emptyField();
            assertEquals(lastOrderCreditId, getLastOrderCreditId());
        }

        @Test
        void shouldNotCreditEmptyName() {
            val lastOrderCreditId = getLastOrderCreditId();
            invalidCard = new CardInfo("", card.getMonth(), card.getYear(), card.getCvc());
            page.credit(approvedCard(), invalidCard);
            page.emptyField();
            assertEquals(lastOrderCreditId, getLastOrderCreditId());
        }

        @Test
        void shouldNotCreditEmptyMonth() {
            val lastOrderCreditId = getLastOrderCreditId();
            invalidCard = new CardInfo(card.getName(), "", card.getYear(), card.getCvc());
            page.credit(approvedCard(), invalidCard);
            page.emptyField();
            assertEquals(lastOrderCreditId, getLastOrderCreditId());
        }

        @Test
        void shouldNotCreditEmptyYear() {
            val lastOrderCreditId = getLastOrderCreditId();
            invalidCard = new CardInfo(card.getName(), card.getMonth(), "", card.getCvc());
            page.credit(approvedCard(), invalidCard);
            page.emptyField();
            assertEquals(lastOrderCreditId, getLastOrderCreditId());
        }

        @Test
        void shouldNotCreditEmptyCvc() {
            val lastOrderCreditId = getLastOrderCreditId();
            invalidCard = new CardInfo(card.getName(), card.getMonth(), card.getMonth(), "");
            page.credit(approvedCard(), invalidCard);
            page.emptyField();
            assertEquals(lastOrderCreditId, getLastOrderCreditId());
        }
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
}
