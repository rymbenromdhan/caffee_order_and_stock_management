
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;




public class Sales {
    private static Map<Product, Integer> productSalesData = new HashMap<>();
    public static void getPopularProducts() {                    
        for (Product product : Product.productList) {
            productSalesData.put(product, 0);
         }
        for (Transactions t : Transactions.allTransactions) {
             for (Map.Entry<Product, Integer> entry : productSalesData.entrySet()) {
               if (t.transactionType.equals(Transactions.TransactionType.S)) {
                  Product key = entry.getKey();
                  if (key.getName().equalsIgnoreCase(t.c)) {
                      entry.setValue(entry.getValue() + t.quantity);
                    }
                }
        }
           }
         List<Map.Entry<Product, Integer>> entryList = new ArrayList<>(productSalesData.entrySet());
         entryList.sort(Map.Entry.<Product, Integer>comparingByValue().reversed());
         System.out.println("Best sellers ");
         System.out.println("-----------------------------------------------------------");
         for (Map.Entry<Product, Integer> entry : entryList) {
            if (entry.getValue() != 0) {
                
                System.out.println("Product: " + entry.getKey().getName() + " | Count: " + entry.getValue());
            }
           
        }
         System.out.println("-----------------------------------------------------------");
        }
    public static void getInventoryStatus() {      
                          
        for (Product product : Product.productList) {
            productSalesData.put(product, 0);
         }


        for (Product prd : Product.productList) {
             for (Map.Entry<Product, Integer> entry : productSalesData.entrySet()) {
                 Product key = entry.getKey();
                 if (key.getName().equalsIgnoreCase(prd.name)) {
                     entry.setValue(prd.stockQuantity);
                    }
                }
           }


         List<Map.Entry<Product, Integer>> entryList = new ArrayList<>(productSalesData.entrySet());
         entryList.sort(Map.Entry.<Product, Integer>comparingByValue().reversed());

        System.out.println("Inventory Status");

         for (Map.Entry<Product, Integer> entry : entryList) {
                System.out.println("-----------------------------------------------------------");
                System.out.println("Product: " + entry.getKey().getName() + ", Stock Quantity: " + entry.getValue());
                System.out.println("-----------------------------------------------------------");

            
        }
        }
    public static void generateSalesReport() {
            System.out.println("Sales Report:");
            System.out.println("-----------------------------------------------------------");
            System.out.printf("| %-20s | %-10s | %-10s |\n", "Product", "Quantity", "Revenue");
            System.out.println("-----------------------------------------------------------");
            Float total = (float) 0.0;
            int tquantity = 0;
            Float revenue = (float) 0;
            Map<String, Integer> productQuantityMap = new HashMap<>();
            Map<String, Float> productRevenueMap = new HashMap<>();
            for (Transactions t : Transactions.allTransactions) {
                if (t.transactionType.equals(Transactions.TransactionType.S)) {
                    String productName = t.c;
                    int quantity = t.quantity;
                    tquantity += quantity;
                 productQuantityMap.put(productName, productQuantityMap.getOrDefault(productName, 0) + quantity);
                 productRevenueMap.put(productName, productRevenueMap.getOrDefault(productName, 0f) + calculateProductRevenue(quantity, t.unitPrice));
                }
            }
            for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
                String productName = entry.getKey();
                int quantity = entry.getValue();
                revenue = productRevenueMap.get(productName);
                total += revenue;
                System.out.printf("| %-20s | %-10d | $%-9.2f |\n", productName, quantity, revenue);
                System.out.println("-----------------------------------------------------------");
            }
        
            System.out.println("Total revenue: dt" + total );
            System.out.println("average revenue per sale : dt" + total / tquantity  );

    
        }
    public static void generatePurchaseReport() {
        System.out.println("Purchases Report:");
            System.out.println("-----------------------------------------------------------");
            System.out.printf("| %-20s | %-10s | %-10s |\n", "Product", "Quantity", "costs");
            System.out.println("-----------------------------------------------------------");
            Float total = (float) 0.0;
            Float cost = (float) 0;
            Map<String, Integer> productQuantMap = new HashMap<>();
            Map<String, Float> productcostMap = new HashMap<>();
            for (Transactions t : Transactions.allTransactions) {
                if (t.transactionType.equals(Transactions.TransactionType.P)) {
                    String productName = t.c;
                    int quantity = t.quantity;
                 productQuantMap.put(productName, productQuantMap.getOrDefault(productName, 0) + quantity);
                 productcostMap.put(productName, productcostMap.getOrDefault(productName, 0f) + calculateProductRevenue(quantity, t.purchasePrice));
                }
            }
            for (Map.Entry<String, Integer> entry : productQuantMap.entrySet()) {
                String productName = entry.getKey();
                int quantity = entry.getValue();
                cost = productcostMap.get(productName);
                total += cost;
                System.out.printf("| %-20s | %-10d | $%-9.2f |\n", productName, quantity, cost);
                System.out.println("-----------------------------------------------------------");
            }
        
            System.out.println("Total cost: dt" + total );

    
        }
    private static Float calculateProductRevenue(int q , Float p) {
           return p*q ;
        }
    public static void export(){
        String path = "C:/Users/asus/Desktop/project/salesreport.txt";
try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
    writer.write("Purchases Report:");
    writer.newLine();
    writer.write("-----------------------------------------------------------");
    writer.newLine();
    writer.write("Product, Quantity, costs");
    writer.newLine();
    writer.write("-----------------------------------------------------------");
    Float total = (float) 0.0;
    Float cost = (float) 0;
    Map<String, Integer> productQuantMap = new HashMap<>();
    Map<String, Float> productcostMap = new HashMap<>();
    for (Transactions t : Transactions.allTransactions) {
        if (t.transactionType.equals(Transactions.TransactionType.P)) {
            String productName = t.c;
            int quantity = t.quantity;
            productQuantMap.put(productName, productQuantMap.getOrDefault(productName, 0) + quantity);
            productcostMap.put(productName, productcostMap.getOrDefault(productName, 0f) + calculateProductRevenue(quantity, t.purchasePrice));
        }
    }
    for (Map.Entry<String, Integer> entry : productQuantMap.entrySet()) {
        String productName = entry.getKey();
        int quantity = entry.getValue();
        cost = productcostMap.get(productName);
        total += cost;
        writer.newLine();
        writer.write(productName + " " + quantity + " " + cost);
        writer.newLine();
        writer.write("-----------------------------------------------------------");
    }
    writer.newLine();
    writer.write("Total cost: dt" + total);
    writer.newLine();
    writer.newLine();
    writer.write("Sales Report:");
    writer.newLine();
    writer.write("-----------------------------------------------------------");
    writer.newLine();
    writer.write("Product, Quantity, Revenue");
    writer.newLine();
    writer.write("-----------------------------------------------------------");
    writer.newLine();
    Float total1 = (float) 0.0;
    int tquantity = 0;
    Float revenue = (float) 0;
    Map<String, Integer> productQuantityMap = new HashMap<>();
    Map<String, Float> productRevenueMap = new HashMap<>();
    for (Transactions t : Transactions.allTransactions) {
        if (t.transactionType.equals(Transactions.TransactionType.S)) {
            String productName = t.c;
            int quantity = t.quantity;
            tquantity += quantity;
            productQuantityMap.put(productName, productQuantityMap.getOrDefault(productName, 0) + quantity);
            productRevenueMap.put(productName, productRevenueMap.getOrDefault(productName, 0f) + calculateProductRevenue(quantity, t.unitPrice));
        }
    }
    for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
        String productName = entry.getKey();
        int quantity = entry.getValue();
        revenue = productRevenueMap.get(productName);
        total1 += revenue;
        writer.write(productName +" " + quantity + " " + revenue);
        writer.newLine();
        writer.write("-----------------------------------------------------------");
        writer.newLine();
    }
    writer.newLine();
    writer.write("Total revenue: dt " + total1);
    writer.newLine();
    writer.write("average revenue per sale : dt " + total1 / tquantity);
    writer.newLine();
     for (Product product : Product.productList) {
            productSalesData.put(product, 0);
         }
    for (Transactions t : Transactions.allTransactions) {
             for (Map.Entry<Product, Integer> entry : productSalesData.entrySet()) {
               if (t.transactionType.equals(Transactions.TransactionType.S)) {
                  Product key = entry.getKey();
                  if (key.getName().equalsIgnoreCase(t.c)) {
                      entry.setValue(entry.getValue() + t.quantity);
                    }
                }
        }
           }
    List<Map.Entry<Product, Integer>> entryList = new ArrayList<>(productSalesData.entrySet());
    entryList.sort(Map.Entry.<Product, Integer>comparingByValue().reversed());
    writer.write("Best sellers ");
    writer.newLine();
    writer.write("-----------------------------------------------------------");
    for (Map.Entry<Product, Integer> entry : entryList) {
        if (entry.getValue() != 0) {
            writer.newLine();
             writer.write("Product: " + entry.getKey().getName() + " | Count: " + entry.getValue());
            }
           
        }
    writer.newLine();
    writer.write("-----------------------------------------------------------");
    for (Product product : Product.productList) {
            productSalesData.put(product, 0);
         }


    for (Product prd : Product.productList) {
        for (Map.Entry<Product, Integer> entry : productSalesData.entrySet()) {
              Product key = entry.getKey();
              if (key.getName().equalsIgnoreCase(prd.name)) {
                     entry.setValue(prd.stockQuantity);
                    }
                }
           }


    entryList = new ArrayList<>(productSalesData.entrySet());
    entryList.sort(Map.Entry.<Product, Integer>comparingByValue().reversed());
    writer.newLine();
    writer.write("Inventory Status");

    for (Map.Entry<Product, Integer> entry : entryList) {
            writer.newLine();
            writer.write("-----------------------------------------------------------");
            writer.newLine();
            writer.write("Product: " + entry.getKey().getName() + ", Stock Quantity: " + entry.getValue());
            writer.newLine();
            writer.write("-----------------------------------------------------------");

            
        }
    


    
} catch (IOException e) {
    e.printStackTrace();
}

        
   

   }

}  



    

