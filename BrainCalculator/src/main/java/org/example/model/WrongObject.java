package org.example.model;

import java.util.ArrayList;

public class WrongObject {

    private int id;
    private String operator;
    private Integer[] numbers;
    private int userInputAnswer;
    private int actualAnswer;

    public void setNumbers(Integer[] numbers) {
        this.numbers = numbers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setUserInputAnswer(int userInputAnswer) {
        this.userInputAnswer = userInputAnswer;
    }

    public void setActualAnswer(int actualAnswer) {
        this.actualAnswer = actualAnswer;
    }



    public int getId() {
        return id;
    }

    public String getOperator() {
        return operator;
    }

    public Integer[] getNumbers() {
        return numbers;
    }

    public int getUserInputAnswer() {
        return userInputAnswer;
    }

    public int getActualAnswer() {
        return actualAnswer;
    }




}
