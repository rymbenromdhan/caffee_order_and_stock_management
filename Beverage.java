import java.io.*;
import java.util.*;

public class Beverage extends Product{
    public int size = 1 ; 
    public boolean isSweet = false ; 
    public static Map<String, Beverage> beverageMap = new HashMap<>();
   

      //construcotr : add new beverage 
    public  Beverage(String name, float price, String category, int stockQuantity , int size , boolean isSweet) {
          super( name , price , category , stockQuantity ); 
          this.size = size; // these are some standard attributes
          this.isSweet = isSweet ; // these are standard attributes 
          Beverage.beverageMap.put(this.name, this);
          productList.add(this);
   }

   //import historical data 
   public static void importData() {
       String path = "C:\\Users\\asus\\Desktop\\project\\beverage.txt";
   
       try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
           reader.readLine();
           String line ;
   
           while ((line = reader.readLine() ) != null) {
               String[] attributes = line.split(","); 
               String name = attributes[0];
               float price = Float.parseFloat(attributes[1]);
               String category = attributes[2];
               Integer stockQuantity = Integer.parseInt(attributes[3]);
               Integer size = Integer.parseInt(attributes[4]);
               boolean isSweet = Boolean.parseBoolean(attributes[5]);
               Beverage b = new Beverage(name, price, category, stockQuantity, size, isSweet);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
   
   
    
     
    //getters 
     public int getSize(){
        return size ;
    }
    public boolean getSweeteness(){
        return isSweet ;
    }
    //setters 
     public void setSize(int size){
         this.size = size ;
    }
    public void setSweeteness(boolean sweet){
        this.isSweet = sweet ;
    }

   // order
    public static void orderBeverage(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("-what beverage to order and - what quantity ? please enter these attributes in this order ,seperated by a space");
        Beverage b = beverageMap.get(scanner.next());
        int quantity = scanner.nextInt();
         if ( quantity < b.stockQuantity){
              b.stockQuantity -= quantity ; 
              b.toString();
              Float pur = (float) ((float) b.price - 0.5) ; 
              Transactions.recordTransaction( b.getName() , quantity , Transactions.TransactionType.S , b.price , pur ); 
              System.out.println(" and the total will be"+ quantity*b.price);
         } else {
            System.out.println("Insufficient quantity , you can order only up to "+ b.stockQuantity + "items");
         }
    }

    // implementing toString
    public String toString(){
        String message ; 
        if( isSweet == true){
            message = "it is sweetened"; 
        } else {
            message = "it is not sweetened";
        }
        String s = "";
        if ( size == 1){
            s = "small";
        } else if ( size == 2){
            s = "medium";
        } else if ( size == 3){
            s = "large";
        }
        return "the product is :" + this.name + "," + message + ", it will be of a size" + s + ", and a unit price of " + price; 
    }

    //export data 
    public static void export() {
        String path = "C:/Users/asus/Desktop/project/newbeverage.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Writing header
            writer.write("name,price,category,stockQuantity,size,isSweet");
            writer.newLine();
    
            // Writing each product
            for (Map.Entry<String, Beverage> entry : beverageMap.entrySet()) {
                Beverage b = entry.getValue();
                writer.write(b.getName() + "," +
                        b.getPrice() + "," +
                        b.getCategory() + "," +
                        b.getStockQuantity() + "," +
                        b.getSize() + "," +
                        b.getSweeteness());
                writer.newLine();
            }
            System.out.println("data exported to " + path + "successfully ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
