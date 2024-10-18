package de.uulm.verteilte_systeme;

public class Main {
    public static void main(String[] args) {
        EchoClient client = new EchoClient("vs.lxd-vs.uni-ulm.de", 5678, 5000);
        client.connect();

        String response = client.sendMessage("Please provide a new expression!");
        System.out.println("Server response: " + response);

        int result = ExpressionEvaluator.evaluateExpression(response);
        System.out.println("Result: " + result);

        String resultString = String.format("%s=%d", response, result);
        response = client.sendMessage(resultString);
        System.out.println("Server response: " + response);

        client.disconnect();
    }
}