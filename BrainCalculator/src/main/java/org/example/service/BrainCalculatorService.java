package org.example.service;

import org.example.constants.OperatorConstants;
import org.example.model.WrongObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BrainCalculatorService {
    static Random random = new Random();

    /**
     * Simple Logic To return numbers based on difficulty level.
     */
    public static void calculate() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Difficulty Level " +
                "\n1. Easy (Single digit +,-,*)" +
                "\n2. Medium2 (Double Digit +,-)" +
                "\n3. Medium3 (Double Digit +,-, *)" +
                "\n4. Medium4 (Double Digit /)" +
                "\n5. Hard (all mix)");
        int difficultyLevelUserInput = input.nextInt();
        System.out.println("Enter Timer in minutes");
        int timerUserInput = input.nextInt();
        System.out.println("Enter Number Count(Like how many numbers you want in result");
        int numberCount = input.nextInt();



        Integer correctAnswerCount = 0;
        Integer totalProblemShown = 0;
        AtomicInteger digitCount = new AtomicInteger(0); //  it is used to get digit count, like 1 has a single digit, and 23 has 2 so on.

        ArrayList<String> operators = new ArrayList<>();
        ArrayList<WrongObject> wrongResults = new ArrayList<>();
        setOperators(difficultyLevelUserInput, operators, digitCount);

        long endTime = System.currentTimeMillis() + timerUserInput * 60 * 1000;
        while(System.currentTimeMillis() < endTime) {
            /**
             * I will pass
             * digitCount - How many digits should be present in the questions.
             * operators - How many oprators can be present
             * numberCount - How many numbers user want in result.
             *
             * @return - map of operator and array of integers.
             *
             * Example:
             * digitCount = 2, operators = ["+", "-"], numberCount = 3
             * return - Map("+", [23, 34, 45]), operator will be decide based on random.
             */
            Map<String, Integer[]> getNumbers = getNumbersBasedOnDifficulty(digitCount, operators, numberCount);
            // Now, I need to format these getNumbers List for user

            String operator = null;
            for (Map.Entry<String, Integer[]> entry: getNumbers.entrySet()) {
                operator = entry.getKey();
            }

            int actualAnswer = formatGetNumbers(getNumbers);
            int userAnswer = input.nextInt();
            if(actualAnswer == userAnswer) {
                correctAnswerCount++;
            } else {
                WrongObject wrongObject = new WrongObject();
                wrongObject.setId(wrongResults.size() + 1);
                wrongObject.setActualAnswer(actualAnswer);
                wrongObject.setNumbers(getNumbers.get(operator));
                wrongObject.setUserInputAnswer(userAnswer);
                wrongObject.setOperator(operator);
                wrongResults.add(wrongObject);
            }
            totalProblemShown++;
            try {
                Thread.sleep(1000); // small pause to avoid busy looping
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

        }
        System.out.println("Time is up! Exiting...\nCorrect Count is: "+ correctAnswerCount + " ot of total problem count: "+totalProblemShown);
        if(wrongResults.isEmpty()) {
            System.out.println("There is no wrong answers");
        } else {
            System.out.println("Wrong Ansert problem List is below");
            wrongResults.stream().sequential()
                    .forEach(wrongObject -> {
                        System.out.println("\n****************");
                        System.out.println("Count: "+wrongObject.getId() + "" +
                                " user Provided Answer: "+wrongObject.getUserInputAnswer() + "" +
                                " Actual Answer: "+wrongObject.getActualAnswer() + "" +
                                " Operator: "+ wrongObject.getOperator() + "" +
                                " Numbers array: "+ Arrays.stream(wrongObject.getNumbers()).toList());

                    });
        }
    }

    private static int formatGetNumbers(Map<String, Integer[]> getNumbers) {
        String operator = null;
        for (Map.Entry<String, Integer[]> entry: getNumbers.entrySet()) {
            operator = entry.getKey();
        }
        System.out.println("Numbers are: " + Arrays.stream(getNumbers.get(operator)).toList() + " operator is: "+ operator);
        Integer numbersArray[] = getNumbers.get(operator);
        int firstNumber = numbersArray[0];
        for (int i=1;i<numbersArray.length;i++) {
            int secondNumber = numbersArray[i];
            if(operator.equals("+")) {
                firstNumber = firstNumber + secondNumber;
            } else if(operator.equals("-")) {
                firstNumber = firstNumber - secondNumber;
            } else if(operator.equals("*")) {
                firstNumber = firstNumber * secondNumber;
            } else {
                firstNumber = firstNumber / secondNumber;
            }
        }
        return firstNumber;
    }

    private static HashMap<String, Integer[]> getNumbersBasedOnDifficulty(AtomicInteger digitCount, ArrayList<String> operators, int numberCount) {
        HashMap<String, Integer[]> result = new HashMap<>();
        String randomOperator = operators.get(random.nextInt(operators.size()));
        Integer[] numberArray = getNumbersArray(digitCount, numberCount);
        result.put(randomOperator, numberArray);
        return result;
    }

    private static Integer[] getNumbersArray(AtomicInteger digitCount, int numberCount) {
        Integer[] result = new Integer[numberCount];
        if(digitCount.get() == 1) {
            for (int i=0;i<numberCount;i++) {
                result[i] = random.nextInt(9) + 1;
            }
        } else if(digitCount.get() == 2) {
            for (int i=0;i<numberCount;i++){
                result[i] = random.nextInt(9,99) + 1;
            }
        } else {
            for (int i=0;i<numberCount;i++){
                result[i] = random.nextInt(0,99) + 1;
            }
        }
        return result;
    }

    private static void setOperators(int difficultyLevelUserInput, ArrayList<String> operators, AtomicInteger digitCount) {
        if(difficultyLevelUserInput == 1) {
            operators.addAll(List.of(OperatorConstants.PLUS, OperatorConstants.SUBTRACT, OperatorConstants.MULTIPLY));
            digitCount.set(1);
        } else if(difficultyLevelUserInput == 2) {
            operators.addAll(List.of(OperatorConstants.PLUS, OperatorConstants.SUBTRACT));
            digitCount.set(2);
        } else if(difficultyLevelUserInput == 3) {
            operators.addAll(List.of(OperatorConstants.PLUS, OperatorConstants.SUBTRACT, OperatorConstants.MULTIPLY));
            digitCount.set(2);
        } else if(difficultyLevelUserInput == 4) {
            operators.add(OperatorConstants.DIVIDE);
            digitCount.set(2);
        } else{
            operators.addAll(List.of(OperatorConstants.PLUS, OperatorConstants.SUBTRACT, OperatorConstants.MULTIPLY, OperatorConstants.DIVIDE));
            digitCount.set(2);
        }
    }
}
