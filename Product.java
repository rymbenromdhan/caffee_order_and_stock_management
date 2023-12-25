import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Product{
    public static int id = 1; 
    public String name;
    public Float price;
    public String category ; 
    public static ArrayList<Product> productList = new ArrayList<>();
    public int stockQuantity;
    public static Map<String, Product> productMap = new HashMap<>();
    // Constructor : add new product
    public Product(String name, float price, String category, int stockQuantity) {
        this.id = ++id;
        this.name = name;
        this.price = price;
        this.category = category ;  
        this.stockQuantity = stockQuantity ; 
        productList.add(this);
    }
    
    //getters for each attribute
    public int getId(){
        return id ;
    }
    public String getName(){
        return name ;
    }
    public String getCategory(){
        return category ;
    }
    public Float getPrice(){
        return price ;
    }

     public int getStockQuantity() { 
        return stockQuantity;
    }

    //setters for each attribute
     public void setName(Product p , String name){
         p.name = name ;
         System.out.println("the object's name is changed to " + p.name);
    }
     public void setCategory(Product p , String category ){
         p.category = category ;
         System.out.println("the object's category is changed to " + p.category);
    }
     public void setPrice(Product p , Float price){
         p.price = price ;
         //System.out.println("the object's price is changed to " + p.price);
    }
     public void setStockQuantity(Product p , int Quantity) { 
        p.stockQuantity = Quantity ; 
        Float pur = (float) ((float) p.getPrice() - 0.5) ; 
        Transactions.recordTransaction(p.getName(), Quantity, Transactions.TransactionType.P , p.getPrice() ,pur  );
        //System.out.print("the current stock of this product is" + p.stockQuantity);
    }
    
    // to string method : will be used in sub classes 
    public abstract String toString();

    //delete a product 
    public void deleteProduct(ArrayList<Product> productMap, Product P) {
        productList.remove(P);
    }

    //exporting 
    public static void export() {
        String path = "C:/Users/asus/Desktop/project/productlist.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Map.Entry<String, Product> entry : productMap.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
            System.out.println("HashMap exported to " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}