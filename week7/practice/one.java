import java.util.*;

abstract class PaymentMethod {
    private final String transactionId;
    private static int transactionCounter = 1000;

    public PaymentMethod() {
        transactionCounter++;
        transactionId = "TXN-" + transactionCounter;
    }

    public abstract String processPayment(double amount);

    public String processPayment(double amount, String note) {
        return processPayment(amount) + " (" + note + ")";
    }

    public String getTransactionId() {
        return transactionId;
    }
}

class CreditCardPayment extends PaymentMethod {
    private String cardNumberLastFour;

    public CreditCardPayment(String cardNumberLastFour) {
        super();
        this.cardNumberLastFour = cardNumberLastFour;
    }

    @Override
    public String processPayment(double amount) {
        return "Charged $" + String.format("%.1f", amount)
                + " to card ending " + cardNumberLastFour
                + " - Txn " + getTransactionId();
    }
}

class CashPayment extends PaymentMethod {

    public CashPayment() {
        super();
    }

    @Override
    public String processPayment(double amount) {
        return "Received $" + String.format("%.1f", amount)
                + " in cash - Txn " + getTransactionId();
    }
}

public class one {

    static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }

    public static void main(String[] args) {

        CreditCardPayment cc =
                new CreditCardPayment("4471");

        System.out.println(cc.processPayment(250.0));

        CashPayment cash =
                new CashPayment();

        System.out.println(cash.processPayment(40.0));

        System.out.println(
                cc.processPayment(250.0, "Birthday gift")
        );

        PaymentMethod ref = cc;

        printConfirmation(ref, 250.0);
    }
}