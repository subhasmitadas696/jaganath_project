package com.csmtech.SJTA.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class MathCaptcha {
    private final Random random = new Random();

    public String generateCaptcha() {
        int number1 = random.nextInt(10);
        int number2 = random.nextInt(10);
        int result = number1 + number2;

        return number1 + " + " + number2 + " = ?";
    }

    public boolean validateCaptcha(int answer, int expected) {
        return answer == expected;
    }
}
