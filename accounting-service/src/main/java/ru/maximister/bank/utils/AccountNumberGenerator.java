package ru.maximister.bank.utils;



import java.util.Random;

public class AccountNumberGenerator {

    public static String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();

        accountNumber.append(getRandomNumber(3));
        accountNumber.append(getRandomNumber(1));
        accountNumber.append(getRandomNumber(16));

        return accountNumber.toString();
    }

    private static String getRandomNumber(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
