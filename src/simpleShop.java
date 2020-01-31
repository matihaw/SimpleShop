import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;
public class simpleShop {


    public static String scanString(Scanner scanner) {
        try {
            return scanner.next();
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static int scanInt(Scanner scanner) {
        try {
            return scanner.nextInt();
        }catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }

    public static float scanFloat(Scanner scanner) {
        try {
            return scanner.nextFloat();
        }catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }
    
    public static boolean matchToTextPattern(String text) {
    	Pattern pattern = Pattern.compile("[a-zA-Z]+");
    	Matcher matcher = pattern.matcher(text);
    	if(matcher.matches()) {
    		return true;
    	}else {
    		System.out.println("Type only charakters!");
			return false;
		}
    	
    	
    }
    public static void main(String [] args) {
    	
        Scanner scanner = new Scanner(System.in);
        Milk milk = new Milk("Milk" , 10, 1.0f);
        Bread bread = new Bread("Bread",2,0.5f);
        Water water = new Water("Water", 1, 1.5f);
        List<Product> products = new ArrayList<Product>();
        List<Product> shoppingBasket = new ArrayList<Product>();
        products.add(milk);
        products.add(bread);
        products.add(water);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conUser = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/simpleShop","root","");
            String sqlQuery = "SELECT * FROM users; ";
            Statement getUsers = conUser.createStatement();
            boolean mainLoop = true;
            boolean isLoggedIn = false;
            int userPermissions=0;
            int userID=-1;
            while(mainLoop) {
                System.out.println("Menu");
                if(!isLoggedIn) {
                    System.out.println("1-Log in");
                }else {
                    System.out.println("1-Log out");
                }
                if(userPermissions==1) {
                    System.out.println("2-Buy something");
                    System.out.println("3-Add/Remove/Edit product");
                    System.out.println("4-Add/Remove/Edit user");
                    System.out.println("5-Open your shopping basket");
                    System.out.println("6-Quit");
                }
                else if(isLoggedIn && userPermissions==0) {
                    System.out.println("2-Show products");
                    System.out.println("3-Open your shopping basket");
                    System.out.println("4-Quit");
                }

                switch (scanInt(scanner)) {
                    case 1:     ///log in/out
                        if(!isLoggedIn) {
                            System.out.print("username: ");
                            String userName = scanString(scanner);
                            System.out.println();
                            System.out.print("password: ");
                            String userPasswd = scanString(scanner);
                            ResultSet rs = getUsers.executeQuery(sqlQuery);
                            while(rs.next()) {
                                if(rs.getString(2).equals(userName) && rs.getString(3).equals(userPasswd)) {
                                    userID =rs.getInt(1);
                                    userPermissions =rs.getInt(4);
                                    System.out.println("zalogowano");
                                    isLoggedIn = true;
                                    break;
                                }
                            }
                        }else {
                            isLoggedIn = false;
                        }
                        break;
                    case 2:     ///Show products
                        System.out.println("Do you want to buy?");
                        System.out.println("Yes/No");
                        if(scanString(scanner).toLowerCase().equals("yes")){
                            while (true){
                                System.out.println("Type \"Nothing\" to stop adding products");
                                System.out.println("Products:");
                                for(Product i : products){
                                    System.out.println(i.getName() + " price: " + i.getPrice());
                                }
                                System.out.println("What do you want to buy?");
                                String producToAddToBasket = scanString(scanner);
                                if(producToAddToBasket.toLowerCase().equals("nothing")){
                                    break;
                                }else {
                                    System.out.print("Type amount of this product: ");
                                    int amountOfProduct = scanInt(scanner);
                                    for (Product i : products) {
                                        if (i.getName().toLowerCase().equals(producToAddToBasket.toLowerCase())) {
                                            for(int j=0;j<amountOfProduct;j++){
                                                shoppingBasket.add(i);
                                            }
                                            System.out.println("\"" + producToAddToBasket+"\" " +" added succesfuly");
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case 3:     /// Add/Remove/Edit product or show shopping basket
                        if(userPermissions==0) {
                            float price = 0f;
                            for(Product i :  shoppingBasket){
                                System.out.println(i.getName() + " price: " + i.getPrice());
                                price += i.getPrice();
                                conUser.close();
                                mainLoop=false;
                                scanner.close();
                            }
                            System.out.println("Price for everything: " + price);
                        break;
                        } else {
                            System.out.println("1-Add product");
                            System.out.println("2-Edit product");
                            System.out.println("3-Remove product");
                            System.out.println("4-Show product");
                            switch (scanInt(scanner)) {
                                case 1:         ///add product
                                    System.out.println("Product type Milk/Bread/Water/Diffrent");
                                    switch (scanString(scanner).toLowerCase()) {
                                        case "milk":
                                        	System.out.print("Type name: ");
                                        	String name = scanString(scanner);
                                        	System.out.print("\n"+"Type price: ");
                                        	float price = scanFloat(scanner);
                                        	System.out.print("\n"+"Type capacity/weight: ");
                                        	float cap=scanFloat(scanner);
                                        	if(matchToTextPattern(name)) {
                                        		Milk  newMilk = new Milk(name, price, cap);
                                        		products.add(newMilk);
                                        	}
                                            break;
                                        case "bread":
                                        	System.out.print("Type name: ");
                                        	String nameBread = scanString(scanner);
                                        	System.out.print("\n"+"Type price: ");
                                        	float priceBread = scanFloat(scanner);
                                        	System.out.print("\n"+"Type capacity/weight: ");
                                        	float capBread=scanFloat(scanner);
                                        	if(matchToTextPattern(nameBread)) {
	                                        	Bread  newBread = new Bread(nameBread, priceBread, capBread);
	                                        	products.add(newBread);
                                        	}
                                            break;
                                        case "water":
                                        	System.out.print("Type name: ");
                                        	String nameWater = scanString(scanner);
                                        	System.out.print("\n"+"Type price: ");
                                        	float priceWater = scanFloat(scanner);
                                        	System.out.print("\n"+"Type capacity/weight: ");
                                        	float capWater=scanFloat(scanner);
                                        	if(matchToTextPattern(nameWater)) {
	                                        	Water  newWater = new Water(nameWater, priceWater, capWater);
	                                        	products.add(newWater);
                                        	}
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case 2:         ///Edit product
                                    System.out.println("Products:");
                                    for (Product i : products) {
                                        System.out.println(i.getName() + " price: " + i.getPrice());
                                    }
                                    System.out.println("Select product to edit");
                                    String productToEdit = scanString(scanner).toLowerCase();
                                    System.out.print("What do you want to edit? ");
                                    System.out.println("1:Price");
                                    System.out.println("2:Name");
                                    System.out.println("3:Capacity/Weight");
                                    int selector = scanInt(scanner);
                                    for (Product i : products) {
                                        if (i.getName().toLowerCase().equals(productToEdit)) {
                                            switch (selector) {
                                                case 1:
                                                    System.out.println("Type new price");
                                                    i.setPrice(scanInt(scanner));
                                                    break;
                                                case 2:
                                                    System.out.println("Type new name");
                                                    i.setName(scanString(scanner));
                                                    break;
                                                case 3:
                                                    System.out.println("Type new capacity/weight");
                                                    if (i.getName().toLowerCase().equals("bread")) {
                                                        bread.setWeight(scanFloat(scanner));
                                                    }
                                                    if (i.getName().toLowerCase().equals("water")) {
                                                        water.setCapacity(scanFloat(scanner));
                                                    }
                                                    if (i.getName().toLowerCase().equals("milk")) {
                                                        milk.setCapacity(scanFloat(scanner));
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    }
                                    break;
                                case 3:	///remove
                                	System.out.println("Products:");
                                	int iterator =0;
                                    for (Product i : products) {
                                        System.out.println(iterator +"-" + i.getName() + " price: " + i.getPrice());
                                        iterator++;
                                    }
                                    System.out.print("Type product's id to remove: ");
                                    int index = scanInt(scanner);
                                    if(index<0 || index>iterator) {
                                    	System.out.println("Bad index");
                                    }else {
                                    	products.remove(index);
	                                    System.out.println("\n Product have been removed succesfuly");
	                                    }
                                    break;
                                case 4:
                                    System.out.println("Products:");
                                    for (Product i : products) {
                                        System.out.println(i.getName() + " price: " + i.getPrice());
                                    }
                                    break;
                            }
                        }
                        break;
                    case 4:             ////Add/Remove/Edit user or quit as user

                        if(userPermissions!=1){
                            conUser.close();
                            mainLoop=false;
                            scanner.close();
                        } else {

                            System.out.println("1-Add user");
                            System.out.println("2-Remove user");
                            System.out.println("3-Edit user");
                            System.out.println("4-Show users");
                            switch (scanInt(scanner)) {
                                case 1:         ////Add user
                                    PreparedStatement insertUser = conUser.prepareStatement("INSERT INTO `users`(`Username`, `Password`, `Is_Admin`) VALUES (?,?,?)");
                                    System.out.print("username: ");
                                    insertUser.setString(1, scanString(scanner));    ///userName
                                    System.out.println(" ");
                                    System.out.print("password: ");
                                    insertUser.setString(2, scanString(scanner));   ///userPasswd
                                    System.out.println(" ");
                                    System.out.println("Permissions");
                                    System.out.println("1-Administrator");
                                    System.out.println("0-Normal user");
                                    insertUser.setInt(3, scanInt(scanner));            ///userPermission
                                    if (!insertUser.execute()) {
                                        System.out.println("User added succesful");
                                    } else {
                                        System.out.println("Adding user failed");
                                    }
                                    break;
                                case 2:         ///Remove user
                                    System.out.print("ID ");
                                    System.out.print("Username ");
                                    System.out.print("password ");
                                    System.out.println("Permisions");
                                    ResultSet rs = getUsers.executeQuery(sqlQuery);
                                    while (rs.next()) {
                                        System.out.print(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
                                        System.out.println(" ");
                                    }
                                    PreparedStatement removeUser = conUser.prepareStatement("DELETE FROM `users` WHERE ID = ?");
                                    System.out.print("Type id: ");
                                    int idToRemove = scanInt(scanner);
                                    if (idToRemove == userID) {
                                        System.out.println("You cant remove this user");
                                    } else {
                                        removeUser.setInt(1, idToRemove);
                                        removeUser.executeUpdate();
                                        System.out.println("User has been removed succesfully");
                                    }
                                    break;
                                case 3:         ////Edit user

                                    break;
                                case 4:             ////Show users
                                    System.out.print("ID ");
                                    System.out.print("Username ");
                                    System.out.print("password ");
                                    System.out.println("Permisions");
                                    rs = getUsers.executeQuery(sqlQuery);
                                    while (rs.next()) {
                                        System.out.print(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
                                        System.out.println(" ");
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }

                        break;
                    case 5: ////basket as admin
                        float price = 0f;
                        String allProducts= "";
                        for(Product i :  shoppingBasket){
                            System.out.println(i.getName() + " price: " + i.getPrice());
                            price += i.getPrice();
                            allProducts = (new StringBuilder()).append(i.getName());  
                        }
                        System.out.println("Price for everything: " + price);
                        System.out.println("Do you want to buy it: (YES/NO)");
                        if(scanString(scanner).toLowerCase().equals("yes")) {
                            PreparedStatement insertBill = conUser.prepareStatement("INSERT INTO `usersbuyhistory`(`user_Id`, `data`, `products`,`price`) VALUES (?,?,?,?)");
                            insertBill.setInt(1, userID);
                            Date date = new Date();
                            insertBill.setDate(2, (java.sql.Date) date);
                            insertBill.setString(3, x);
                        }
                        break;
                    case 6: ////quit as admin
                        if(userPermissions==1) {
                            mainLoop=false;
                            conUser.close();
                            scanner.close();
                        }
                        break;
                    default:
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }



    }
}
