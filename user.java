import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.* ;

import javax.naming.AuthenticationException; 
public class user {
    public int userid = 1 ; 
    public final String userName ; 
    public String password ; 
    public role userRole ; 
    public enum role {N, M} ; //user can be either manager or a "normal" user 
    public static ArrayList<user> allUsers = new ArrayList<>(); 
    public static Map<String, user> userMap = new HashMap<>();

    //CONSTRUCTOR
    public user(String userName , String password , role role ){
            this.userid = userid++ ; 
            this.userName = userName ;          
            this.password = password ;
            this.userRole = role;
            user.userMap.put(this.userName, this);
            allUsers.add(this);
   }
    //import historical data 
    public static void importData() {
        String path = "C:/Users/asus/Desktop/project/user.txt";
    
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.readLine(); // Skip the header
            String line;
    
            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");
                String userName = attributes[0];
                String password = attributes[1];
                int roleValue = Integer.parseInt(attributes[2]);
                role userRole;
    
                if (roleValue == 1) {
                    userRole = role.M;
                } else if (roleValue == 2) {
                    userRole = role.N;
                } else {
                    throw new IllegalArgumentException("Invalid role value in the file");
                }
    
                user u = new user(userName, password, userRole);
                user.userMap.put(userName, u);
                allUsers.add(u);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     // create a new user 
     public static void accessControl() {
        Scanner in = new Scanner(System.in);
        System.out.println("add the new username");
        String username = in.next();
        System.out.println("enter a password for "+ username);
        String password = in.next();
        System.out.println("enter the role for "+ username);
        System.out.println("Please type (1) for a manager role. \n Please type (2) for a normal role");
        int roleType = in.nextInt();
        role role = user.role.N ; 
        while (roleType == 1 || roleType == 2){
             if (roleType == 1){
                  role = user.role.M;
                 roleType = 0 ;
             }
             else if (roleType == 2){
                  role = user.role.N;
                 roleType = 0 ;
            } else {
                System.out.println("invalid role type , please try again");
            }
        } 
        user user = new user(username, password, role);

    }
     // getters and setters 
     public String getUserName(){
        return this.userName ; 
     }
     private String getUserPassword(){
        return this.password ; 
     }
     public  role getUserRole(){
        return this.userRole ; 
     }
     public void setUserRole(role role ){
        if (this.getUserRole() == user.role.M){
         this.userRole = role ; 
         System.out.println("the user's role is changed to " + this.userRole );
        }
     }
     // authentication fct 
     public static user authentication() throws AuthenticationException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your username");
        String name = scanner.next();
        System.out.println("Please enter your password");
        String pass = scanner.next();
        user authenticatedUser = null;
        int i = 0;
    
        for (user user : allUsers) {
            if (user.userName.equalsIgnoreCase(name) && user.password.equals(pass)) {
                System.out.println("Welcome back, " + user.userName + "!");
                authenticatedUser = user;
                break;
            } else if (user.userName.equalsIgnoreCase(name) && !(user.password.equals(pass))) {
                while (i < 3) {
                    i++;
                    System.out.println("Wrong password, try again");
                    authentication();
                }
            }
        }
    
        if (authenticatedUser != null) {
            return authenticatedUser;
        } else {
            throw new AuthenticationException("Authentication failed");
        }
    }
    
  
        // checking authorization fct 
        public boolean authorization(){
            if (this.getUserRole() == user.role.M){
                return true ; 
         }  else {
                return false ;
        }
    }

        //change password
        public static void changePassword(){
            Scanner scanner = new Scanner(System.in);
            System.out.println(" Please enter the username");
            String name = scanner.next() ; 
            System.out.println(" Please enter the corresponding password");
            String pass = scanner.next() ;
            for( user user : allUsers){
                  if((user.userName.equalsIgnoreCase(name)) && (user.password.equals(pass))){
                    System.out.println(" Please enter your new password");
                    String p1 = scanner.next() ; 
                    System.out.println(" Please confirm your new password");
                    String p2 = scanner.next() ; 
                    if ( p1.equals(p2)){
                        user.password = p1 ;
                    } else {
                        System.out.println("password mismatch");
                        changePassword();
                    }
                } 
        }
        System.out.println("password changed successfully");
     }

     //delete users 
     public static void deleteUser() {
       
            int out;
            do {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter the desired username");
                String name = scanner.next();
                out = 1; // Initialize out here
    
                Iterator<user> iterator = allUsers.iterator();
                while (iterator.hasNext()) {
                    user user = iterator.next();
                    if (user.userName.equalsIgnoreCase(name)) {
                        iterator.remove();
                        userMap.remove(user.getUserName());
                        out = 0;
                        System.out.println("User deleted successfully");
                        break; // Break the loop once the user is found and deleted
                    }
                }
    
                if (out == 1) {
                    System.out.println("User not found");
                }
            } while (out == 1);
       
    }
    
    

    //export current users : 
    public static void export() {
        String path = "C:/Users/asus/Desktop/project/newusers.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Writing header
            writer.write("userName,password,role");
            writer.newLine();
    
            // Writing each product
            for (Map.Entry<String, user> entry : userMap.entrySet()) {
                user u = entry.getValue();
                writer.write(u.getUserName() + "," +
                        u.getUserPassword() + "," +
                        u.getUserRole()
                        );
                writer.newLine();
            }
            System.out.println("HashMap exported to " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //string output
    public String toString(){
        return " the user is "+ this.userName + " the user password length is " + this.password.length() + "this users role is" + this.userRole ; 
    }
}
