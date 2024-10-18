package de.uulm.verteilte_systeme;

import java.io.IOException;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Stack<Integer> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();
        EchoClient client = new EchoClient("vs.lxd-vs.uni-ulm.de", 5678);

        try {
            client.connect();
        } catch (IOException e) {
            System.out.println("Could not connect to server: " + e.getMessage());
            return;
        }

        String response = client.sendMessage("Please provide a new expression!");
        System.out.println("Server response: " + response);

        for (int i = 0; i < response.length(); i++) {
            String currentChar = String.valueOf(response.charAt(i));

            if (isNumeric(currentChar)) {
                numbers.push(Integer.parseInt(currentChar));
            } else if (isOperator(currentChar)){
                if (!hasPriority(currentChar)){
                    operators.push(currentChar);
                }else {
                    int a = numbers.pop();
                    int b = Integer.parseInt(String.valueOf(response.charAt(i + 1)));
                    numbers.push(calculate(a, b, currentChar));
                    i ++;
                }
            }
        }

        while (!operators.isEmpty()){
            int a = numbers.pop();
            int b = numbers.pop();
            numbers.push(calculate(a, b, operators.pop()));
        }

        System.out.println("Result: " + numbers.peek());

        String resultString = String.format("%s=%d", response,numbers.peek());

        response = client.sendMessage(resultString);

        System.out.println("Server response: " + response);
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

    public static boolean hasPriority(String op){
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