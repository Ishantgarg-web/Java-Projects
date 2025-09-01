package org.example;

import org.example.service.BrainCalculatorService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        BrainCalculatorService brainCalculatorService = new BrainCalculatorService();
        brainCalculatorService.calculate();
    }
}