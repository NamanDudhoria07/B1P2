import java.util.*;

interface PricingPlan {
    double getPrice(double originalPrice);
}

class DayScholarPlan implements PricingPlan {
    public double getPrice(double originalPrice) {
        return originalPrice;
    }
}

class HostellerPlan implements PricingPlan {
    public double getPrice(double originalPrice) {
        return originalPrice * 0.90;
    }
}

class StaffPlan implements PricingPlan {
    public double getPrice(double originalPrice) {
        return originalPrice * 0.80;
    }
}

class Transaction {
    private double amount;
    private String type;
    private String description;

    public Transaction(double amount, String type, String description) {
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getSignedAmount() {
        if (type.equals("CREDIT")) {
            return amount;
        }
        return -amount;
    }
}

class Purchase {
    private String itemName;
    private double chargedAmount;
    private boolean refunded;

    public Purchase(String itemName, double chargedAmount) {
        this.itemName = itemName;
        this.chargedAmount = chargedAmount;
        this.refunded = false;
    }

    public String getItemName() {
        return itemName;
    }

    public double getChargedAmount() {
        return chargedAmount;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void markRefunded() {
        refunded = true;
    }
}

class SmartCard {
    private String cardId;
    private PricingPlan plan;
    private double balance;
    private List<Transaction> transactions;
    private List<Purchase> purchases;
    private boolean blocked;

    public SmartCard(String cardId, PricingPlan plan) {
        this.cardId = cardId;
        this.plan = plan;
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.purchases = new ArrayList<>();
        this.blocked = false;
    }

    public void topUp(double amount) {
        if (blocked) {
            System.out.println("Top-up rejected: Card " + cardId + " is blocked.");
            return;
        }

        if (amount < 100) {
            System.out.println("Top-up rejected: Minimum top-up is ₹100.00.");
            return;
        }

        if (balance + amount > 5000) {
            System.out.println("Top-up rejected: Maximum balance is ₹5000.00.");
            return;
        }

        balance += amount;

        transactions.add(
            new Transaction(amount, "CREDIT", "Top-up")
        );

        System.out.printf(
            "%s topped up with ₹%.2f. Balance: ₹%.2f.%n",
            cardId, amount, balance
        );
    }

    public Purchase purchase(String itemName, double originalPrice) {
        if (blocked) {
            System.out.println("Purchase failed: Card " + cardId + " is blocked.");
            return null;
        }

        double chargedAmount = plan.getPrice(originalPrice);

        if (balance < chargedAmount) {
            System.out.printf(
                "Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                chargedAmount, balance
            );
            return null;
        }

        balance -= chargedAmount;

        transactions.add(
            new Transaction(chargedAmount, "DEBIT", itemName)
        );

        Purchase purchase = new Purchase(itemName, chargedAmount);
        purchases.add(purchase);

        System.out.printf(
            "%s purchased for ₹%.2f. Balance: ₹%.2f.%n",
            itemName, chargedAmount, balance
        );

        return purchase;
    }

    public void refund(Purchase purchase) {
        if (purchase == null) {
            System.out.println("Refund rejected: Invalid purchase.");
            return;
        }

        if (purchase.isRefunded()) {
            System.out.println(
                "Refund rejected: " + purchase.getItemName()
                + " has already been refunded."
            );
            return;
        }

        double refundAmount = purchase.getChargedAmount();

        balance += refundAmount;

        transactions.add(
            new Transaction(refundAmount, "CREDIT", "Refund")
        );

        purchase.markRefunded();

        System.out.printf(
            "Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",
            refundAmount,
            purchase.getItemName(),
            balance
        );
    }

    public void block() {
        blocked = true;
        System.out.println("Card " + cardId + " blocked.");
    }

    public void unblock() {
        blocked = false;
        System.out.println("Card " + cardId + " unblocked.");
    }

    public void miniStatement() {
        System.out.print("Mini-statement for " + cardId + ": ");

        double sum = 0;

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);

            double signedAmount = transaction.getSignedAmount();
            sum += signedAmount;

            if (i > 0) {
                System.out.print(", ");
            }

            System.out.printf(
                "%s%.2f",
                signedAmount >= 0 ? "+" : "-",
                Math.abs(signedAmount)
            );
        }

        System.out.printf(" = ₹%.2f.%n", balance);
    }
}

public class five {
    public static void main(String[] args) {

        SmartCard card =
            new SmartCard("C-2045", new HostellerPlan());

        card.topUp(500);

        Purchase vegThali =
            card.purchase("Veg Thali", 120);

        card.purchase("Cold Coffee", 60);

        card.purchase("items worth ₹400", 400);

        card.refund(vegThali);

        card.refund(vegThali);

        card.miniStatement();
    }
}