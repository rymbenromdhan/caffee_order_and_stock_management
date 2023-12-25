import java.io.*;
import java.time.*;
import java.util.*;

public class Transactions {
    private static int transactionId = 0;
    private Product product;
    public int quantity;
    private LocalDateTime currentDateTime;
    public LocalDate d;
    public TransactionType transactionType;
    public Float unitPrice;
    public Float purchasePrice;
    public String c;
    public enum TransactionType {S, P}
    public static ArrayList<Transactions> allTransactions = new ArrayList<>();
    public static Map<String, Transactions> transactionMap = new HashMap<>();

    public Transactions(String c, int quantity, LocalDate d, TransactionType transactionType, Float unitPrice ,  Float purchasePrice) {
        this.transactionId = ++transactionId;
        this.c = c;
        this.quantity = quantity;
        this.d = d;
        this.transactionType = transactionType;
        this.unitPrice = unitPrice;
        this.purchasePrice = purchasePrice ; 
        Transactions.transactionMap.put(String.valueOf(this.transactionId), this);
    }

    public Transactions(String c, int quantity, TransactionType transactionType, Float p , Float p1) {
        this.transactionId = ++transactionId;
        this.c = c;
        this.quantity = quantity;
        this.currentDateTime = LocalDateTime.now();
        this.transactionType =transactionType;
        this.unitPrice = p;
        this.purchasePrice = p1; 
        Transactions.transactionMap.put(String.valueOf(this.transactionId), this);
    }

    public static void recordTransaction(String p, int quantity, TransactionType tt, Float price , Float p1) {
        Product pp = Product.productMap.get(p);
        if (pp != null) {
            if (tt == TransactionType.S) {
                pp.stockQuantity -= quantity;
            } else {
                pp.stockQuantity += quantity;
            }
            Transactions t = new Transactions(p, quantity, tt, price , p1);
            Transactions.transactionMap.put(String.valueOf(t.transactionId), t);
        }
    }
    
    public String toString() {
        return "transaction " + this.transactionId + "product " + this.c + "quantity " + this.quantity + "date " + this.d + "type " + this.transactionType;
    }

    public static void export() {
        String path = "C:/Users/asus/Desktop/project/transactionlist.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Writing header
            writer.write("product,quantity,date,transactionType,unitPrice,purchasePrice");
            writer.newLine();

            // Writing each product
            for (Map.Entry<String, Transactions> entry : transactionMap.entrySet()) {
                Transactions t = entry.getValue();
                writer.write(t.c + "," +
                        t.quantity + "," +
                        t.d + "," + t.transactionType + "," + t.unitPrice + "," + t.purchasePrice 
                );
                writer.newLine();
            }
            System.out.println("HashMap exported to " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importData() {
        String path = "C:/Users/asus/Desktop/project/transaction.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");
                String p = attributes[0];
                int quantity = Integer.parseInt(attributes[1]);
                LocalDate date = LocalDate.parse(attributes[2]);
                String typeString = attributes[3];
                String objectType = attributes[4];
                Float unitPrice = Float.parseFloat(attributes[5]);
                Float purchasePrice = Float.parseFloat(attributes[6]);
                TransactionType transactionType = null;

                if ("S".equals(typeString)) {
                    transactionType = TransactionType.S;
                } else if ("P".equals(typeString)) {
                    transactionType = TransactionType.P;
                }
                Transactions t = new Transactions(p, quantity, date, transactionType, unitPrice , purchasePrice);
                allTransactions.add(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
