package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Data
public class DataGenerator {

    @Value
    public static class CardInfo {
        private String name;
        private String month;
        private String year;
        private String cvc;
    }

    public static CardInfo getRandomCard() {
        val faker = new Faker();
        val number = faker.business().creditCardNumber();
        String month = LocalDate.now().plusMonths(2).format(DateTimeFormatter.ofPattern("MM"));
        String year = LocalDate.now().plusYears(3).format(DateTimeFormatter.ofPattern("yy"));
        val cvc = faker.number().digits(3);
        val name = faker.name().firstName() + " " + faker.name().lastName();
        return new CardInfo(name, month, year, cvc);
    }

    public static String approvedCard() {
        return "4444 4444 4444 4441";
    }

    public static String declinedCard() {
        return "4444 4444 4444 4442";
    }

    public static String anotherCard() {
        return new Faker().business().creditCardNumber();
    }

}
