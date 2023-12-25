import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;

import javax.naming.AuthenticationException;

public class welcome {
    public static void main(String[] args) throws AuthenticationException{
        System.out.println("hello");
       // Transactions t = new Transactions("chocoCroissant1",14,(LocalDate)"2023-10-15",Transactions.TransactionType.P);
        //Scanner scanner = new Scanner(System.in);
        //System.out.println("enter the user that u want to change their pwd");
        //String y = scanner.next();
        //user x  = user.userMap.get(y);
        //x.changePassword();
        user.importData();
        user u = user.authentication() ; 
        System.out.println(u);
        for (Entry<String, Transactions> entry : Transactions.transactionMap.entrySet()) {
            String key = entry.getKey();
            Transactions value = entry.getValue();

            System.out.println("Key: " + key + ", Value: " + value.toString());
        }
        Transactions.export();
       
    }
}

    
    

