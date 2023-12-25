import java.time.LocalDate;
import java.util.*;

import javax.naming.AuthenticationException;


class project{
    public static void product(user u){
    Scanner scanner = new Scanner(System.in);
    boolean cond1 = false;
    while(cond1==false){
        System.out.println("What type of product you will add? \n Please press (1) if you would like to add a new beverage\n Please press (2) if you are going to add a new Pasterie \n" + //
      " Please press (3) if you are going to order a Beverage \n Please press (4) to order a Pastery \n" + //
      " Please press (5) to know more about a beverage \n Please press (6) to know more about a pastery \n Please press (7) to export the current data");
        int prod = scanner.nextInt();
        switch (prod){
            case 1:
                cond1 = true;
                System.out.println("please enter the \n name \n price \n category \n stockquantity \n size \n and sweetness \n  of the new beverage ");
                String name  = scanner.next();
                Float price = scanner.nextFloat();
                String category = scanner.next();
                int stockQuantity = scanner.nextInt();
                int size = scanner.nextInt();
                boolean sweet = scanner.nextBoolean();
                Beverage b = new Beverage(name ,price , category , stockQuantity , size , sweet);
                System.out.println("Beverage added succesffully ! ");
                break;
            case 2:
                cond1 = true;
                System.out.println("please enter the \n name \n price \n category \n stockquantity \n and temperature \n  of the new pastery ");
                name  = scanner.next();
                price = scanner.nextFloat();
                category = scanner.next();
                stockQuantity = scanner.nextInt();
                boolean temperature = scanner.nextBoolean();
                Pasteries p = new Pasteries(name ,price , category , stockQuantity , temperature);
                System.out.println("pastery added successfully !");
                break;
            case 3 :
                cond1 = true;
                Beverage.orderBeverage();
                break;
            case 4 : 
                cond1 = true ; 
                Pasteries.orderPasteries();
                break;
            case 5 : 
                cond1 = true ; 
                System.out.println("what beverage you want to know about more ? please enter its name");
                String n = scanner.next();;
                b = Beverage.beverageMap.get(n);
                System.out.println(b.toString());
                break;
            case 6 : 
                cond1=true;
                System.out.println("what beverage you want to know about more ? please enter its name");
                n = scanner.next();;
                p = Pasteries.pasteryMap.get(n);
                System.out.println(p.toString());
                break;
            case 7 : 
                cond1=true;
                System.out.println("press 1 to export beverages data , press 2 to export pasteries data");
                int i = scanner.nextInt();
                if ( i == 1 ){
                    Beverage.export();
                } else if ( i == 2){
                    Pasteries.export();
                } else {
                    System.out.println("Invalid character entered");
                }
                break;
            default:
                cond1 = false ;
                System.out.println("invalid choice, please try again!");
                break;
            }
        }
    }

    public static void Inventory(user u){
        Scanner scanner = new Scanner(System.in);
        boolean cond = false;
        while (cond == false){
            System.out.println("What type of action? \n Press (1) to update price \n Press (2) to update quantity \n " + //
                    " Press (3) to delete category");
            int inv = scanner.nextInt();
            switch (inv){
                case 1:
                    cond = true;
                    Inventory.updatePrice();
                    break;
                case 2:
                    cond = true;
                    Inventory.updateQuantity();
                    break;
                case 3:
                    cond = true;
                    Inventory.deleteCategory();
                    break;
                default:
                    System.out.println("invalid choice, please try again!");
                    break;
                }            
            }
        }

    public static void user(user u){
        Scanner scanner = new Scanner(System.in);
        boolean cond2 = false;
        while (cond2 == false){
        System.out.println("What type of action? \n Press (1) if you would like to add a new user \n Press (2) if you are going to change your password \n Press (3) if you are going to delete a user \n" + //
                " Press (4) to export current data ");
        int prod = scanner.nextInt();
        switch (prod){
            case 1:
                cond2 = true;
                user.accessControl();
                break;
            case 2:
                cond2 = true;
                user.changePassword();
                break;
            case 3:
                cond2 = true;
                user.deleteUser();
                break;
            case 4:
                cond2 = true;
                user.export();
                break;
            default:
                System.out.println("invalid choice, please try again!");
                break;
            }            
        }

    }
    
    public static void transaction(user u){
        Scanner scanner = new Scanner(System.in);
        boolean cond3 = false;
        while (cond3 == false){
        System.out.println("What type of action? \n Press (1) for  Beverages \n Press (2) for  Pasteries \n" + //
                "  Press(3) for exporting current  data");
        int order = scanner.nextInt();
        switch (order){
            case 1:
                cond3 = true;
                System.out.println("please enter the beverage name ,quantity , date , and the transaction's type");
                String b = scanner.next();
                int quantity = scanner.nextInt();
                LocalDate d = LocalDate.parse(scanner.next());
                Transactions.TransactionType t = null;
                 
                String transactionTypeInput = scanner.next();
                if (transactionTypeInput.equals("S")) {
                    t = Transactions.TransactionType.S;
                } else if (transactionTypeInput.equals("P")) {
                    t = Transactions.TransactionType.P;
                } else {
                   System.out.println("Invalid type of transaction");
                }
                Float price = Beverage.beverageMap.get(b).getPrice();
                Float pr = (float) (price - 0.5) ; 
                Transactions tt = new Transactions(b , quantity , d , t , price , pr );
                break;
            case 2:
                cond3 = true;
                System.out.println("please enter the pastery name ,quantity , date , and the transaction's type");
                String p = scanner.next();
                quantity = scanner.nextInt();
                d = LocalDate.parse(scanner.next());
                Transactions.TransactionType t1 = null;
                transactionTypeInput = scanner.next();
                if (transactionTypeInput.equals("S")) {
                    t1 = Transactions.TransactionType.S;
                } else if (transactionTypeInput.equals("P")) {
                    t1 = Transactions.TransactionType.P;
                } else {
                   System.out.println("Invalid type of transaction");
                }
                price = Pasteries.pasteryMap.get(p).getPrice();
                Float p1 = (float) (price - 0.5) ; 
                Transactions tt1 = new Transactions(p , quantity , d , t1 , price , p1);
                break;
            case 3 : 
                cond3 = true ; 
                Transactions.export();
                break;
            default:
                System.out.println("invalid choice, please try again!");
                break;
            }            
        }
    }
    
    public static void sales(user u){
        Scanner scanner = new Scanner(System.in);
        boolean cond4 = false;
        while (cond4 == false){
        System.out.println("What type of action? \n Press (1) for generating sales records \n Press (2) for getting purchases reports \n " + //
                " \n Press (3) for getting popular products \n Press (4) for getting Inventory Status \n Press (5) to export the reports ");
        int sale = scanner.nextInt();
        switch (sale){
            case 1:
                cond4 = true;
                Sales.generateSalesReport();
                break;
            case 2:
                cond4 = true;
                Sales.generatePurchaseReport();
                break;
            case 3:
                cond4 = true;
                Sales.getPopularProducts();
                break;
            case 4 : 
                cond4 = true ; 
                Sales.getInventoryStatus();
                break;
            case 5 : 
               cond4 = true ; 
                Sales.export();
                break;
            default:
                System.out.println("invalid choice, please try again!");
                break;
            }            
        }
    }
    
    public static void main(String[] args) throws AuthenticationException{
        System.out.println("welcome back to the café system ! ");
        Beverage.importData();
        Pasteries.importData();
        user.importData();
        Transactions.importData();
        user u = user.authentication();
        if(u.getUserRole() == user.role.M){
           Scanner scanner = new Scanner(System.in);
           int s = 0 ; 
           while( s != 6) {
             System.out.println("select the class that you wish to perform action on \n 1.Product \n 2.Inventory \n 3.Transactions \n 4.Sales \n 5.users \n press 6 to exit the system");
             s = scanner.nextInt() ; 
             switch (s) {
                 case 1:
                     product(u);
                     break;
                 case 2:
                     Inventory(u);
                     break;
                 case 3:
                     transaction(u);
                     break;
                 case 4:
                     sales(u);
                     break;
                 case 5:
                     user(u);
                     break;
                 case 6 :
                     break;
                 default : 
                     System.out.println("invalid parameter , please try again");
                 }
            }
        
        }
        else if(u.getUserRole() == user.role.N){
            Scanner scanner = new Scanner(System.in);
            int c =  0 ;
            while (c != 4 ){
                System.out.println("What type of action? \n Press (1) to change your password " + 
               " \n Press (2) to order beverage \n Press (3) to order pasteries \n Press (4) to quit ");
                c = scanner.nextInt();
                switch (c){
                    case 1:
                        user.changePassword();
                        break;
                    case 2:
                        Beverage.orderBeverage();
                        break;
                    case 3:
                        Pasteries.orderPasteries();
                        break;
                    case 4 : 
                        break;
                    default:
                        System.out.println("invalid choice, please try again!");
                        break;
            }            
        }
        }
        System.out.println("see you later, " + u.userName + " ! ");
}
}