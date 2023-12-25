import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Pasteries extends Product {
   //class's own attributes
    public Boolean isHot = false; 
    public static Map<String, Pasteries> pasteryMap = new HashMap<>();

    //class constructor
    public Pasteries(String name, float price,  String category, int stockQuantity , 
             boolean isHot) {
        super(name, price, category, stockQuantity);
        this.isHot = isHot;
        Pasteries.pasteryMap.put(this.name, this);
        productList.add(this);
    }

    //import historical data 
    public static void importData() {
       String path = "C:/Users/asus/Desktop/project/pasteries.txt";
   
       try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
           reader.readLine();
           String line ;
   
           while ((line = reader.readLine() ) != null) {
               String[] attributes = line.split(","); 
               String name = attributes[0];
               float price = Float.parseFloat(attributes[1]);
               String category = attributes[2];
               Integer stockQuantity = Integer.parseInt(attributes[3]);
               boolean isHot = Boolean.parseBoolean(attributes[4]);
               Pasteries p = new Pasteries(name, price, category, stockQuantity,isHot);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    //getter of isHot, initial state is not Hot
    public Boolean getIsHot(){
        return this.isHot;
    }

    //setter of isHot, in case the customer wants to receive his order heated
    public void setIsHot(Pasteries pasteries, Boolean isHot){
        pasteries.isHot = isHot;
        if(isHot == true){
            System.out.println("The order needs to be heated before seving");
        } else {
            System.out.println("The order won't need to be heated before seving"); 
        }
    }
    
    public static void orderPasteries() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-What pastery to order and -what quantity? Please enter these attributes in this order, separated by a space");
        
        String pasteryName = scanner.next();
        if (pasteryMap.containsKey(pasteryName)) {
            Pasteries pasteries = pasteryMap.get(pasteryName);
            int quantity = scanner.nextInt();
            if (quantity < pasteries.stockQuantity) {
                pasteries.stockQuantity -= quantity;
                pasteries.toString();
                Float pur = (float) ((float) pasteries.price - 0.5) ; 
                Transactions.recordTransaction(pasteries.getName(), quantity, Transactions.TransactionType.S, pasteries.price , pur );
                System.out.println("The total will be: " + quantity * pasteries.price);
            } else {
                System.out.println("Insufficient quantity, you can order only up to " + pasteries.stockQuantity + " items");
            }
        } else {
            System.out.println("Invalid pastery name entered.");
        }
    }
    


    // overriding the toString() method
    public String toString() {
        if(isHot == true){
        return  "The " + name + " will be served hot " + " it costs dts" + price ;
    }
    else {
        return "The " + name + " will be served cold "   + " it costs dts" + price ;
    }
    }

     //export data 
    public static void export() {
        String path = "C:/Users/asus/Desktop/project/newpasteries.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Writing header
            writer.write("name,price,category,stockQuantity,isHot");
            writer.newLine();
    
            // Writing each product
            for (Map.Entry<String, Pasteries> entry : pasteryMap.entrySet()) {
                Pasteries p  = entry.getValue();
                writer.write(p.getName() + "," +
                        p.getPrice() + "," +
                        p.getCategory() + "," +
                        p.getStockQuantity() + "," +
                        p.getIsHot() );
                writer.newLine();
            }
            System.out.println("HashMap exported to " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}