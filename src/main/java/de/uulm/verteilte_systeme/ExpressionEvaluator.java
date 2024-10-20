package de.uulm.verteilte_systeme;

import java.util.Stack;

public class ExpressionEvaluator {

    public static int evaluateExpression(String expression) {
        Stack<Integer> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            String currentChar = String.valueOf(expression.charAt(i));

            if (isNumeric(currentChar)) {
                numbers.push(Integer.parseInt(currentChar));
            } else if (isOperator(currentChar)) {
                if (!hasPriority(currentChar)) {
                    operators.push(currentChar);
                } else {
                    int a = numbers.pop();
                    int b = Integer.parseInt(String.valueOf(expression.charAt(i + 1)));
                    numbers.push(calculate(a, b, currentChar));
                    i++;
                }
            }
        }

        while (!operators.isEmpty()) {
            int a = numbers.pop();
            int b = numbers.pop();
            numbers.push(calculate(a, b, operators.pop()));
        }

        return numbers.pop();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isOperator(String strOp) {
        if (strOp == null) {
            return false;
        }
        return strOp.equals("+") || strOp.equals("*");
    }

    public static boolean hasPriority(String op) {
        return op.equals("*");
    }

    public static int calculate(int a, int b, String operator) {
        return switch (operator) {
            case "+" -> a + b;
            case "*" -> a * b;
            default -> 0;
        };
    }
}
