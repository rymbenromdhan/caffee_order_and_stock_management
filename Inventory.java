import java.util.*;

public class Inventory {
    static Set<String> processedProducts = new HashSet<>();
    static Set<String> pProducts = new HashSet<>();

    //constructor
    public Inventory(){
        
    }

    // uniform update of quantity accross a category
    public static void updateQuantity() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter the \n purchase quantity \n and the category concerned");
        int purchaseQuantity = scanner.nextInt();
        String category = scanner.next();
        Iterator<Product> iterator = Product.productList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            // Check if this product has already been processed
            if (pProducts.contains(product.getName())) {
                continue;
            }else if (category.equals(product.getCategory())) {
                product.setStockQuantity(product , product.getStockQuantity() + purchaseQuantity );
                pProducts.add(product.getName());
            }
        }
        // Clear the processed products for the next update
        pProducts.clear();
        System.out.println("Products of category " + category + " have been updated by  " + purchaseQuantity );
    }
        

        
        
    
    // unifrom update of price across a category
    public static void updatePrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the price update and the category concerned");
        float priceUpdate = scanner.nextFloat();
        String category = scanner.next();

        Iterator<Product> iterator = Product.productList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();

            // Check if this product has already been processed
            if (processedProducts.contains(product.getName())) {
                continue;
            }else if (category.equals(product.getCategory())) {
                product.setPrice(product , product.getPrice() + priceUpdate);
                processedProducts.add(product.getName());
            }
        }

        // Clear the processed products for the next update
        processedProducts.clear();

        System.out.println("Products of category " + category + " have been updated by dt " + priceUpdate);
    }
    
    // delete of a category 
    public static void deleteCategory() {
       Scanner scanner = new Scanner(System.in);
       System.out.println("please enter the category concerned");
       String category = scanner.next();
       Product.productList.removeIf(product -> category.equals(product.getCategory()));
       Iterator<Map.Entry<String, Beverage>> beverageIterator = Beverage.beverageMap.entrySet().iterator();
       while (beverageIterator.hasNext()) {
           Map.Entry<String, Beverage> entry = beverageIterator.next();
           if (entry.getValue().getCategory().equals(category)) {
               beverageIterator.remove();
           }
       }
   
       // Remove from pastry map
       Iterator<Map.Entry<String, Pasteries>> pastryIterator = Pasteries.pasteryMap.entrySet().iterator();
       while (pastryIterator.hasNext()) {
           Map.Entry<String, Pasteries> entry = pastryIterator.next();
           if (entry.getValue().getCategory().equals(category)) {
               pastryIterator.remove();
           }
       }


               }
        
    
    // display
    public static void displayInventory() {
        System.out.println("Current Inventory:");
        for (Product product : Product.productList) {
            System.out.println(product);
        }
    }
}
