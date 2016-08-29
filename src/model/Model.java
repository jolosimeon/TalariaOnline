package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;


import com.mysql.jdbc.Connection;

//jspBeans here (not yet created)
import objects.User;
import objects.Product;
import objects.Transaction;
import objects.TransactionLineItem;
import objects.Review;
import objects.Sale;

public class Model
{

    private static DBConnection db;
    public static User getUser(String username, String password)
    {
        User user = null;
        String saltFromDB = null;
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT salt FROM user WHERE username = ? ";
            pstmt = connection.prepareStatement( query );
			pstmt.setString( 1, username);
			rs = pstmt.executeQuery( );
            if (rs.next())
            {
            	saltFromDB = rs.getString("salt");
            }
        } catch (Exception e)
        {
        	// LOG:
            Date now = Calendar.getInstance().getTime();
            log(username, "logged into the system.", now);
        	e.printStackTrace();
        }
        if(saltFromDB == null)
        	return user;
        
        byte[] salt =  Base64.getDecoder().decode(saltFromDB);
        
        String hashedPasswordBase64 = new Sha256Hash(password, salt, 1024).toBase64();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT * FROM user WHERE username = ? AND password = ? ";
            pstmt = connection.prepareStatement( query );
			pstmt.setString( 1, username); 
			pstmt.setString( 2, hashedPasswordBase64);
			rs = pstmt.executeQuery( );
            if (rs.next())
            {
                user = new User(rs.getString("username"), rs.getString("password"), rs.getInt("user_type"), rs.getString("fname"), rs.getString("minitial"), rs.getString("lname"), rs.getString("email"), rs.getString("billing_addr"), rs.getString("shipping_addr"), rs.getString("card_no"), rs.getInt("temppw_status"), rs.getTimestamp("temppw_timestamp"));
                Date now = Calendar.getInstance().getTime();
                //    	temp password set		 AND	expired
                if (user.getTemppw_status() == 1 && now.after(user.getTemppw_timestamp())){
                	user = null;
                	deleteExpiredAccount(username);
                }
            }
         // LOG:
            Date now = Calendar.getInstance().getTime();
            log(username, "logged into the system.", now);
        } catch (Exception e)
        {
        	// LOG:
            Date now = Calendar.getInstance().getTime();
            String logdesc = "";
            if (getUser(username) != null)
            	logdesc = "attempted and failed to log into the system: incorrect credentials.";
            else logdesc = "attempted and failed to log into the system: not an existing user.";
            log(username, "attempted and failed to log into the system:.", now);
            e.printStackTrace();
        }

        	return user;
    }
    
    public static User getUser(String username)
    {
        User user = null;
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT * FROM user WHERE username = ? ";
            pstmt = connection.prepareStatement( query );
			pstmt.setString( 1, username); 
			rs = pstmt.executeQuery();

            if (rs.next())
            {
                user = new User(rs.getString("username"), rs.getString("password"), rs.getInt("user_type"), rs.getString("fname"), rs.getString("minitial"), rs.getString("lname"), rs.getString("email"), rs.getString("billing_addr"), rs.getString("shipping_addr"), rs.getString("card_no"), rs.getInt("temppw_status"), rs.getTimestamp("temppw_timestamp"));
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }
    
    public static ArrayList<User> getAllUsers()
    {
    	// includes expired accounts
        ArrayList<User> users = new ArrayList<>();
        User user = null;
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT * FROM user";
            pstmt = connection.prepareStatement( query ); 
			rs = pstmt.executeQuery();

            while (rs.next())
            {
                user = new User(rs.getString("username"), rs.getString("password"), rs.getInt("user_type"), rs.getString("fname"), rs.getString("minitial"), rs.getString("lname"), rs.getString("email"), rs.getString("billing_addr"), rs.getString("shipping_addr"), rs.getString("card_no"), rs.getInt("temppw_status"), rs.getTimestamp("temppw_timestamp"));
				users.add(user);
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return users;
    }

    public static void addCustomerAccount(User c)
    {
    	RandomNumberGenerator rng = new SecureRandomNumberGenerator();
    	ByteSource salt = rng.nextBytes();
    	String hashedPasswordBase64 = new Sha256Hash(c.getPassword(), salt, 1024).toBase64();

        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;

            String query = "INSERT INTO `talaria_db`.`user` (`username`, `password`, `user_type`, `fname`, `minitial`, `lname`, `email`, `billing_addr`, `shipping_addr`, `card_no`, `salt`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement( query );
            pstmt.setString( 1, c.getUsername());
            pstmt.setString( 2, hashedPasswordBase64); 
            pstmt.setInt( 3, 1); 
            pstmt.setString( 4, c.getFirstName()); 
            pstmt.setString( 5, c.getMiddleInitial()); 
            pstmt.setString( 6, c.getLastName()); 
            pstmt.setString( 7, c.getEmail()); 
            pstmt.setString( 8, c.getBillingAddress()); 
            pstmt.setString( 9, c.getShippingAddress()); 
            pstmt.setString( 10, c.getCardNumber());
            pstmt.setString( 11, salt.toBase64());

            pstmt.executeUpdate();
            
         // LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "A customer account was created: " + c.getUsername() + " was added into the system.", now);
            
        } catch (Exception e)
        {
            // LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "System attempted to create a customer account: " + c.getUsername() + " was not added into the system.", now);
            e.printStackTrace();
        }
    }

    public static void addProductManagerAccount(User pm, String requester)
    {
    	if (checkAuthentication(requester, "createaccount")) {
    		// password hashing
        	RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        	ByteSource salt = rng.nextBytes();
        	String hashedPasswordBase64 = new Sha256Hash(pm.getPassword(), salt, 1024).toBase64();
        	
        	// temporary password timestamp
        	Calendar calendar = Calendar.getInstance();
        	calendar.add(Calendar.DATE, 1);
        	Date expirydate = calendar.getTime();
//        	Date expirydate = new Date(System.currentTimeMillis()+5*60*1000);
        	Timestamp expiry = new java.sql.Timestamp(expirydate.getTime());

            db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query = "INSERT INTO `talaria_db`.`user` (`username`, `password`, `user_type`, `fname`, `minitial`, `lname`, `email`, `salt`, `temppw_status`, `temppw_timestamp`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = connection.prepareStatement( query );
                pstmt.setString( 1, pm.getUsername());
                pstmt.setString( 2, hashedPasswordBase64); 
                pstmt.setInt( 3, 3); 
                pstmt.setString( 4, pm.getFirstName()); 
                pstmt.setString( 5, pm.getMiddleInitial()); 
                pstmt.setString( 6, pm.getLastName()); 
                pstmt.setString( 7, pm.getEmail());
                pstmt.setString( 8, salt.toBase64());
                pstmt.setInt(9, 1); // sets password to temporary
                pstmt.setTimestamp(10, expiry); // for password/account expiration, refer to this +24 hours

                pstmt.executeUpdate();
                
                // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Admin created a product manager account: " + pm.getUsername() + " was added into the system.", now);
            } catch (Exception e)
            {
                // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Admin attempted to create a product manager account: " + pm.getUsername() + " was not added into the system.", now);
                e.printStackTrace();
            }
    	} else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to create a product manager account - failed: not authorized to do this.", now);
    	}
    }

    public static void addAccountingManagerAccount(User am, String requester)
    {
    	if (checkAuthentication(requester, "createaccount")) {
    		// password hashing
        	RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        	ByteSource salt = rng.nextBytes();
        	String hashedPasswordBase64 = new Sha256Hash(am.getPassword(), salt, 1024).toBase64();
        	
        	// temporary password timestamp
        	Calendar calendar = Calendar.getInstance();
//        	calendar.add(Calendar.DATE, 1);
//        	Date expirydate = calendar.getTime();
        	Date expirydate = new Date(System.currentTimeMillis()+5*60*1000);
        	Timestamp expiry = new java.sql.Timestamp(expirydate.getTime());

            db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query = "INSERT INTO `talaria_db`.`user` (`username`, `password`, `user_type`, `fname`, `minitial`, `lname`, `email`, `salt`, `temppw_status`, `temppw_timestamp`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = connection.prepareStatement( query );
                pstmt.setString( 1, am.getUsername());
                pstmt.setString( 2, hashedPasswordBase64); 
                pstmt.setInt( 3, 4); 
                pstmt.setString( 4, am.getFirstName()); 
                pstmt.setString( 5, am.getMiddleInitial()); 
                pstmt.setString( 6, am.getLastName()); 
                pstmt.setString( 7, am.getEmail());
                pstmt.setString( 8, salt.toBase64());
                pstmt.setInt(9, 1); // sets password to temporary
                pstmt.setTimestamp(10, expiry); // for password/account expiration, refer to this +24 hours
                pstmt.executeUpdate();
                
                // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Admin created an accounting manager account; " + am.getUsername() + " was added into the system.", now);
                
            } catch (Exception e)
            {
            	// LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Admin created an accounting manager account; " + am.getUsername() + " was added into the system.", now);
                e.printStackTrace();
            }
    	} else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to create an accounting manager account - failed: not authorized to do this.", now);
    	}
    }

    public static void addProduct(Product p, String requester)
    {
    	if (checkAuthentication(requester, "addproduct")) {
    		db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query = "INSERT INTO product (prod_type, prod_name, prod_desc, prod_price, adder_username) VALUES (?, ?, ?, ?, ?)";
                pstmt = connection.prepareStatement( query );
                pstmt.setInt( 1, p.getType()); 
                pstmt.setString( 2, p.getName()); 
                pstmt.setString( 3, p.getDescription());
                pstmt.setDouble(4, p.getPrice());
                pstmt.setString( 5, p.getAdderUsername());  
                pstmt.executeUpdate();
             // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Product manager added a product: " + p.getName() + " was added into the system.", now);
            } catch (Exception e)
            {
            	// LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Product manager attempted to add a product: " + p.getName() + " was not added into the system.", now);
                e.printStackTrace();
            }
    	} else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to add a product - failed: not authorized to do this.", now);
    	}
    }

    public static void deleteProduct(int id, String requester)
    {
    	if (checkAuthentication(requester, "deleteproduct")) {
    		db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query = "DELETE FROM `talaria_db`.`product` WHERE `prod_id`= ? ";
                pstmt = connection.prepareStatement( query );
                pstmt.setInt( 1, id);  
                pstmt.executeUpdate();
                
                // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Product manager deleted a product: was removed from the system.", now);
                
            } catch (Exception e)
            {
                // LOG:
                Date now = Calendar.getInstance().getTime();
            	log("", "Product manager attempted to delete a product: does not exist in the system. or cannot be deleted.", now);
                e.printStackTrace();
            }
    	} else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to delete a product - failed: not authorized to do this.", now);
    	}
    }

    public static void editProduct(int type, String name, String description, Double price, int id, String requester)
    {
    	if (checkAuthentication(requester, "editproduct")) {
    		db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query = "UPDATE `talaria_db`.`product` SET `prod_type`= ?, `prod_name`= ?, `prod_desc`= ?, `prod_price`= ? WHERE `prod_id`= ? ";
                pstmt = connection.prepareStatement( query );
                pstmt.setInt( 1, type); 
                pstmt.setString( 2, name); 
                pstmt.setString( 3, description);
                pstmt.setDouble(4, price);
                pstmt.setInt( 5, id);  
                pstmt.executeUpdate();
                
             // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Product manager edited a product's details: product was edited in the system.", now);
            } catch (Exception e)
            {
                // LOG:
                Date now = Calendar.getInstance().getTime();
            	log("", "Product manager attempted to edit a product's details: does not exist in the system. or cannot be modified.", now);
                e.printStackTrace();
            }
    	} else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to edit a product - failed: not authorized to do this.", now);
    	}
    }
    
    public static ArrayList<Product> getAllProducts()
    {
        Product product = null;
        ArrayList<Product> product_list = new ArrayList<Product>();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT * FROM product";
            pstmt = connection.prepareStatement( query ); 
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                product = new Product(rs.getInt("prod_id"), rs.getInt("prod_type"), rs.getString("prod_name"), rs.getString("prod_desc"), rs.getDouble("prod_price"), rs.getString("adder_username"));
                product_list.add(product);
            }
         // LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "Product list was accessed.", now);
        } catch (Exception e)
        {
            // LOG:
            Date now = Calendar.getInstance().getTime();
        	log("", "Product list was attempted and failed to be accessed.", now);
            e.printStackTrace();
        }
        return product_list;
    }
    
    public static ArrayList<Product> getAllProductsByNameAndCateg(String name, int categ) {
    	Product product = null;
        ArrayList<Product> product_list = new ArrayList<Product>();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            if (categ == 0 && name.equals("")) {
	            return getAllProducts();
            }
            else if (name.equals("")) {
            	return getAllProductsByCategory(categ);
            }
            else if (categ == 0) {
            	System.out.println("yo");
            	String query = "SELECT * FROM product WHERE prod_name LIKE ? OR prod_desc LIKE ? ";
                pstmt = connection.prepareStatement( query );
                pstmt.setString( 1, "%" + name + "%");
                pstmt.setString( 2, "%" + name + "%"); 
                rs = pstmt.executeQuery();
            }
            else {
            	String query = "SELECT * FROM product WHERE (prod_name LIKE ? OR prod_desc LIKE ?) AND prod_type = ? ";
                pstmt = connection.prepareStatement( query );
                pstmt.setString( 1, "%" + name + "%");
                pstmt.setString( 2, "%" + name + "%");
                pstmt.setInt( 3, categ); 
                rs = pstmt.executeQuery();
            }

            while (rs.next())
            {
                product = new Product(rs.getInt("prod_id"), rs.getInt("prod_type"), rs.getString("prod_name"), rs.getString("prod_desc"), rs.getDouble("prod_price"), rs.getString("adder_username"));
                product_list.add(product);
            }
            
            // LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "Product list was accessed and arranged by name and category.", now);
        } catch (Exception e)
        {
        	// LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "Product list was attempted and failed to be accessed.", now);
            e.printStackTrace();
        }
        return product_list;
    }

    public static ArrayList<Product> getAllProductsByName(String name)
    {
        Product product = null;
        ArrayList<Product> product_list = new ArrayList<Product>();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT * FROM product WHERE prod_name = ? ";
            pstmt = connection.prepareStatement( query );
            pstmt.setString( 1, name); 
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                product = new Product(rs.getInt("prod_id"), rs.getInt("prod_type"), rs.getString("prod_name"), rs.getString("prod_desc"), rs.getDouble("prod_price"), rs.getString("adder_username"));
                product_list.add(product);
            }
         // LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "Product list was accessed and arranged by name.", now);
        } catch (Exception e)
        {
        	// LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "Product list was attempted and failed to be accessed.", now);
            e.printStackTrace();
        }
        return product_list;
    }

    public static Product getProductById(int id)
    {
        Product product = null;
//        ArrayList<Product> product_list = new ArrayList<Product>();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT * FROM product WHERE prod_id = ? ";
            pstmt = connection.prepareStatement( query );
            pstmt.setInt( 1, id); 
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                product = new Product(rs.getInt("prod_id"), rs.getInt("prod_type"), rs.getString("prod_name"), rs.getString("prod_desc"), rs.getDouble("prod_price"), rs.getString("adder_username"));
//                product_list.add(product);
            }
            
            query = "SELECT * FROM review WHERE prod_id = ? ORDER BY id DESC";
            pstmt = connection.prepareStatement( query );
            pstmt.setInt( 1, id);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
            	product.addReview(new Review(rs.getInt("prod_id"), rs.getInt("stars"), rs.getString("details"), rs.getString("username")));
            }
            // LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "Product list was accessed and arranged by id.", now);
        } catch (Exception e)
        {
        	// LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "Product list was attempted and failed to be accessed.", now);
            e.printStackTrace();
        }
//        return product_list;
        return product;
    }

    public static ArrayList<Product> getAllProductsByCategory(int category)
    {
        Product product = null;
        ArrayList<Product> product_list = new ArrayList<Product>();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT * FROM product WHERE prod_type = ? ";
            pstmt = connection.prepareStatement( query );
            pstmt.setInt( 1, category); 
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                product = new Product(rs.getInt("prod_id"), rs.getInt("prod_type"), rs.getString("prod_name"), rs.getString("prod_desc"), rs.getDouble("prod_price"), rs.getString("adder_username"));
                product_list.add(product);
            }
            // LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "Product list was accessed and arranged by category.", now);
        } catch (Exception e)
        {
        	// LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "Product list was attempted and failed to be accessed.", now);
            e.printStackTrace();
        }
        return product_list;
    }

    
    public static Transaction addTransaction(Transaction t, String requester)
    {
    	if (checkAuthentication(requester, "purchaseproduct")) {
    		db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query ="select MAX(trans_id) as last_id from transaction";
                pstmt = connection.prepareStatement( query );
                rs = pstmt.executeQuery();
                rs.next();
                String lastid = rs.getString("last_id");
                t.setId(Integer.valueOf(lastid)+1);
                query = "INSERT INTO `talaria_db`.`transaction` (`trans_id`, `username`, `total`) VALUES (?, ?, ?)";
                pstmt = connection.prepareStatement( query );
                pstmt.setInt( 1, t.getId());
                pstmt.setString( 2, t.getUsername()); 
                pstmt.setDouble( 3, t.getTotal()); 
                pstmt.executeUpdate();

                // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Customer proceeded to check out his/her items.", now);
            } catch (Exception e)
            {
                // LOG:
                Date now = Calendar.getInstance().getTime();
            	log("", "Customer failed to proceed to check out his/her items.", now);
                e.printStackTrace();
            }
    	} else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to purchase a product - failed: not allowed to do this.", now);
    	}
    	
    	return t;
    }

    public static void addTransactionLineItem(TransactionLineItem t, String requester)
    {
    	if (checkAuthentication(requester, "purchaseproduct")) {
    		db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query = "INSERT INTO `talaria_db`.`tlineitem` (`trans_id`, `prod_id`, `quantity`, `unit_price`, `line_total`) VALUES (?, ?, ?, ?, ?)";
                pstmt = connection.prepareStatement( query );
                pstmt.setInt( 1, t.getTransactionId());
                pstmt.setInt( 2, t.getProductId()); 
                pstmt.setInt( 3, t.getQuantity()); 
                pstmt.setDouble( 4, t.getUnitPrice()); 
                pstmt.setDouble( 5, t.getLineTotal()); 
                pstmt.executeUpdate();
             // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Customer added an item to his/her shopping cart.", now);
            } catch (Exception e)
            {
                // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Customer failed to add an item to his/her shopping cart.", now);
                e.printStackTrace();
            }
    	} else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to purchase a product - failed: not allowed to do this.", now);
    	}
    }

    public static void addReview(Review r, String requester)
    {
    	if (checkAuthentication(requester, "review")) {
    		db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query = "INSERT INTO `talaria_db`.`review` (`prod_id`, `stars`, `details`, `username`) VALUES (?, ?, ?, ?)";
                pstmt = connection.prepareStatement( query );
                pstmt.setInt( 1, r.getProductId());
                pstmt.setInt( 2, r.getStars()); 
                pstmt.setString( 3, r.getDetails()); 
                pstmt.setString( 4, r.getUsername()); 
                pstmt.executeUpdate();
                // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Customer revied an item he/she has already purchased.", now);
            } catch (Exception e)
            {
                // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "Customer tried to review an item he/she has not yet purchased or a product that no longer exists.", now);
                e.printStackTrace();
            }
    	} else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to purchase a product - failed: not allowed to do this.", now);
    	}
    }
    
    public static boolean boughtItem(String username, int prod_id) {
    	ArrayList<Transaction> transactions = getAllTransactionsByProduct(prod_id);
    	for (int i = 0; i < transactions.size(); i++)
    		if (transactions.get(i).getUsername().equals(username))
    			return true;
    	return false;
    }
    
    public static String getSalt(String username)
    {
    	db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        String salt = null;
    	try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT salt FROM user WHERE username = ? ";
            pstmt = connection.prepareStatement( query );
			pstmt.setString( 1, username); 
			rs = pstmt.executeQuery( );
            if (rs.next())
            {
                salt  = rs.getString("salt");
            }
            // LOG:
            Date now = Calendar.getInstance().getTime();
            log(username, "got salt.", now);
        } catch (Exception e)
        {
            // LOG:
            Date now = Calendar.getInstance().getTime();
            log(username, "failed to get salt.", now);
            e.printStackTrace();
        }
    	return salt;
    }
    
    public static boolean changePassword(String username, String oldpw, String newpw)
    {
    	User user = getUser(username);
    	byte[] salt =  Base64.getDecoder().decode(getSalt(username));
    	
    	RandomNumberGenerator rng = new SecureRandomNumberGenerator();
    	ByteSource newSalt = rng.nextBytes();
    	String oldHash = new Sha256Hash(oldpw, salt, 1024).toBase64();
    	String newHash = new Sha256Hash(newpw, newSalt, 1024).toBase64();
    	
    	if(user.getPassword().equals(oldHash))
    	{
	        db = new DBConnection();
	        java.sql.Connection connection = db.getConnection();
	        try
	        {
	            PreparedStatement pstmt;
	            String query = "UPDATE user SET password = ? , salt = ?, temppw_status = 0, temppw_timestamp = ? WHERE username = ?";
	            pstmt = connection.prepareStatement( query );
	            pstmt.setString( 1, newHash);
	            pstmt.setString(2, newSalt.toBase64());
	            pstmt.setTimestamp(3, null);
	            pstmt.setString( 4, username);
	            pstmt.executeUpdate();
	            // LOG:
	            Date now = Calendar.getInstance().getTime();
	            log(username, "changed his/her password.", now);
	        } catch (Exception e)
	        {
	            // LOG:
	            Date now = Calendar.getInstance().getTime();
	            log(username, "tried to change his/her password and failed.", now);
	            e.printStackTrace();
	        }
	        return true;
    	}
    	return false;
    }
    
    
    public static void assignTempPassword(String username, String newpw, String requester)
    {
    	if (checkAuthentication(username, "assignpassword")) {
    		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        	ByteSource newSalt = rng.nextBytes();
        	String newHash = new Sha256Hash(newpw, newSalt, 1024).toBase64();
        	
            db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            
         // temporary password timestamp
        	Calendar calendar = Calendar.getInstance();
        	calendar.add(Calendar.DATE, 1);
        	Date expirydate = calendar.getTime();
//        	Date expirydate = new Date(System.currentTimeMillis()+5*60*1000);
        	Timestamp expiry = new java.sql.Timestamp(expirydate.getTime());
            
            try
            {
                PreparedStatement pstmt;
                String query = "UPDATE user SET password = ?, salt = ?, temppw_status = 1, temppw_timestamp = ? WHERE username = ?";
                pstmt = connection.prepareStatement( query );
                pstmt.setString( 1, newHash);
                pstmt.setString( 2, newSalt.toBase64());
                pstmt.setTimestamp(3, expiry);
                pstmt.setString( 4, username);
                pstmt.executeUpdate();
                System.out.println("change is coming");
                // LOG:
                Date now = Calendar.getInstance().getTime();
                log(username, "was assigned a tempoary password.", now);
            } catch (Exception e)
            {
                // LOG:
                Date now = Calendar.getInstance().getTime();
                log(username, "was assigned a temporary password but failed.", now);
                e.printStackTrace();
            }
    	} else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to assign a temporary password - failed: not authorized to do this.", now);
    	}
    }

    

    public static ArrayList<Transaction> getAllTransactions()
    {
        Transaction transaction = null;
        ArrayList<Transaction> transaction_list = new ArrayList<Transaction>();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT transaction.trans_id, username, prod_id, quantity, unit_price, line_total, total FROM transaction, tlineitem WHERE transaction.trans_id = tlineitem.trans_id";
            pstmt = connection.prepareStatement( query );
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                transaction = new Transaction(rs.getInt("trans_id"), rs.getString("username"), rs.getInt("prod_id"), rs.getInt("quantity"), rs.getDouble("unit_price"), rs.getDouble("line_total"), rs.getDouble("total"));
                transaction_list.add(transaction);
            }
         // LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "All transactions were accessed for display.", now);
        } catch (Exception e)
        {
        	// LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "All transactions were attempted and failed to be displayed.", now);
            e.printStackTrace();
        }
        return transaction_list;
    }

    public static ArrayList<Transaction> getAllTransactionsByType(int type)
    {
        Transaction transaction = null;
        ArrayList<Transaction> transaction_list = new ArrayList<Transaction>();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT tr.trans_id, username, tl.prod_id, quantity, unit_price, line_total, total FROM transaction tr, tlineitem tl, product p WHERE tr.trans_id = tl.trans_id AND tl.prod_id = p.prod_id AND prod_type = ? ";
            pstmt = connection.prepareStatement( query );
            pstmt.setInt( 1, type);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                transaction = new Transaction(rs.getInt("trans_id"), rs.getString("username"), rs.getInt("prod_id"), rs.getInt("quantity"), rs.getDouble("unit_price"), rs.getDouble("line_total"), rs.getDouble("total"));
                transaction_list.add(transaction);
            }
         // LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "All " + type + " items were displayed.", now);
        } catch (Exception e)
        {
        	// LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "All " + type + " items failed to be displayed.", now);
            e.printStackTrace();
        }
        return transaction_list;
    }
    
    public static Transaction getLatestTransactionByUser(String username)
    {
        Transaction transaction = null;
//        ArrayList<Transaction> transaction_list = new ArrayList<Transaction>();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT tr.trans_id, username, tl.prod_id, quantity, unit_price, line_total, total FROM transaction tr, tlineitem tl, product p WHERE tr.trans_id = tl.trans_id AND tl.prod_id = p.prod_id AND username = ? ";
            pstmt = connection.prepareStatement( query );
            pstmt.setString( 1, username);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                transaction = new Transaction(rs.getInt("trans_id"), rs.getString("username"), rs.getInt("prod_id"), rs.getInt("quantity"), rs.getDouble("unit_price"), rs.getDouble("line_total"), rs.getDouble("total"));
//                transaction_list.add(transaction);
            }
         // LOG:
            Date now = Calendar.getInstance().getTime();
            log(username, "Latest transaction was accessed for display.", now);
        } catch (Exception e)
        {
        	// LOG:
            Date now = Calendar.getInstance().getTime();
            log(username, "Latest transaction was requested but failed to display.", now);
            e.printStackTrace();
        }
        return transaction;
    }
    
    public static ArrayList<TransactionLineItem> getTransactionLineItemsByTransId(int id)
    {
        TransactionLineItem tlineitem = null;
        ArrayList<TransactionLineItem> item_list = new ArrayList<TransactionLineItem>();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT * FROM tlineitem WHERE trans_id = ?";
            pstmt = connection.prepareStatement( query );
            pstmt.setInt( 1, id);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                tlineitem = new TransactionLineItem(rs.getInt("trans_id"), rs.getInt("prod_id"), rs.getInt("quantity"), rs.getDouble("unit_price"), rs.getDouble("line_total"));
                item_list.add(tlineitem);
            }
         // LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "Item " + id + " was accessed for display.", now);
        } catch (Exception e)
        {
        	// LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "Item " + id + " was attempted and failed to be accessed.", now);
            e.printStackTrace();
        }
        return item_list;
    }

    public static ArrayList<Transaction> getAllTransactionsByProduct(int productId)
    {
        Transaction transaction = null;
        ArrayList<Transaction> transaction_list = new ArrayList<Transaction>();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT tr.trans_id, username, tl.prod_id, quantity, unit_price, line_total, total FROM transaction tr, tlineitem tl, product p WHERE tr.trans_id = tl.trans_id AND tl.prod_id = p.prod_id AND p.prod_id = ? ";
            pstmt = connection.prepareStatement( query );
            pstmt.setInt( 1, productId);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                transaction = new Transaction(rs.getInt("trans_id"), rs.getString("username"), rs.getInt("prod_id"), rs.getInt("quantity"), rs.getDouble("unit_price"), rs.getDouble("line_total"), rs.getDouble("total"));
                transaction_list.add(transaction);
            }
         // LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "List of bought " + productId + " was displayed.", now);
        } catch (Exception e)
        {
        	// LOG:
            Date now = Calendar.getInstance().getTime();
            log("", "List of bought " + productId + " failed to display.", now);
            e.printStackTrace();
        }
        return transaction_list;
    }

    public static ArrayList<Sale> getAllTotalSalesPerProduct(String requester)
    {
    	Sale sale = null;
        ArrayList<Sale> sale_list = new ArrayList<Sale>();
    	if (checkAuthentication(requester, "viewsales")) {
            db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query = "SELECT tlineitem.prod_id, prod_name, SUM(quantity) AS total_quantity_sold, SUM(line_total) AS total_sales " +
                                "FROM product, tlineitem " +
                                "WHERE product.prod_id = tlineitem.prod_id " +
                                "GROUP BY tlineitem.prod_id " +
                                "ORDER BY total_sales desc";
                pstmt = connection.prepareStatement( query );
                rs = pstmt.executeQuery();

                while (rs.next())
                {
                    sale = new Sale(rs.getInt("prod_id"), rs.getString("prod_name"), rs.getInt("total_quantity_sold"), rs.getDouble("total_sales"));
                    sale_list.add(sale);
                }
             // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "List of total sales per product was displayed.", now);
            } catch (Exception e)
            {
            	// LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "List of total sales per product failed to display.", now);
                e.printStackTrace();
            }
    	} else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to view sales - failed: not allowed to do this.", now);
    	}
        return sale_list;
    }

    public static ArrayList<Sale> getTotalSalesPerProduct(int id, String requester)
    {
        Sale sale = null;
        ArrayList<Sale> sale_list = new ArrayList<Sale>();
        if (checkAuthentication(requester, "viewsales")) {
        	db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query = "SELECT tlineitem.prod_id, prod_name, SUM(quantity) AS total_quantity_sold, SUM(line_total) AS total_sales " +
                                "FROM product, tlineitem " +
                                "WHERE product.prod_id = tlineitem.prod_id AND prod_id = ? " +
                                "GROUP BY tlineitem.prod_id " +
                                "ORDER BY total_sales desc";
                pstmt = connection.prepareStatement( query );
                pstmt.setInt( 1, id);
                rs = pstmt.executeQuery();

                while (rs.next())
                {
                    sale = new Sale(rs.getInt("prod_id"), rs.getString("prod_name"), rs.getInt("total_quantity_sold"), rs.getDouble("total_sales"));
                    sale_list.add(sale);
                }
             // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "List of total sales of " + id + " was displayed.", now);
            } catch (Exception e)
            {
            	// LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "List of total sales of " + id + " failed to display.", now);
                e.printStackTrace();
            }
        } else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to view sales - failed: not allowed to do this.", now);
    	}
        return sale_list;
    }

    public static ArrayList<Sale> getAllTotalSalesPerType(String requester)
    {
        Sale sale = null;
        ArrayList<Sale> sale_list = new ArrayList<Sale>();
        if (checkAuthentication(requester, "viewsales")) {
        	db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query = "SELECT type.name, tlineitem.prod_id, prod_name, SUM(quantity) AS total_quantity_sold, SUM(line_total) AS total_sales" +
                                "FROM product, tlineitem, type" +
                                "WHERE product.prod_id = tlineitem.prod_id AND product.prod_type = type.id" +
                                "GROUP BY type.name, prod_id" + 
                                "ORDER BY total_quantity_sold desc";
                pstmt = connection.prepareStatement( query );
                rs = pstmt.executeQuery();

                while (rs.next())
                {
                    sale = new Sale(rs.getInt("prod_id"), rs.getString("prod_name"), rs.getInt("total_quantity_sold"), rs.getDouble("total_sales"));
                    sale_list.add(sale);
                }
             // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "List of total sales was displayed by type.", now);
            } catch (Exception e)
            {
            	// LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "List of total sales by type failed to display.", now);
                e.printStackTrace();
            }
        } else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to view sales - failed: not allowed to do this.", now);
    	}
        return sale_list;
    }

    public static ArrayList<Sale> getTotalSalesPerType(int type, String requester)
    {
        Sale sale = null;
        ArrayList<Sale> sale_list = new ArrayList<Sale>();
        if (checkAuthentication(requester, "viewsales")) {
        	db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                ResultSet rs;
                PreparedStatement pstmt;
                String query = "SELECT type.name, tlineitem.prod_id, prod_name, SUM(quantity) AS total_quantity_sold, SUM(line_total) AS total_sales" +
                                "FROM product, tlineitem, type" +
                                "WHERE product.prod_id = tlineitem.prod_id AND product.prod_type = type.id AND type.id = ?" +
                                "GROUP BY type.name, prod_id" + 
                                "ORDER BY total_quantity_sold desc";
                pstmt = connection.prepareStatement( query );
                pstmt.setInt( 1, type);
                rs = pstmt.executeQuery();

                while (rs.next())
                {
                    sale = new Sale(rs.getInt("prod_id"), rs.getString("prod_name"), rs.getInt("total_quantity_sold"), rs.getDouble("total_sales"));
                    sale_list.add(sale);
                }
             // LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "List of total sales for type " + type + " was displayed.", now);
            } catch (Exception e)
            {
            	// LOG:
                Date now = Calendar.getInstance().getTime();
                log("", "List of total sales for type " + type + " failed to display.", now);
                e.printStackTrace();
            }
        } else {
    		// LOG:
            Date now = Calendar.getInstance().getTime();
            log(requester, " attempted to view sales - failed: not allowed to do this.", now);
    	}
        return sale_list;
    }

    private static void deleteAllExpiredAccounts() {
    	ArrayList<User> users = getAllUsers();
    	boolean delete = false;
    	for (int i=0; i<users.size(); i++) {
    		Date now = Calendar.getInstance().getTime();
            //    	temp password set		 AND	expired
            if (users.get(i).getTemppw_status() == 1 && now.after(users.get(i).getTemppw_timestamp()))
            	delete = true;
            else delete = false;
            
            if (delete) {
            	User user = null;
            	db = new DBConnection();
                java.sql.Connection connection = db.getConnection();
                try
                {
                    PreparedStatement pstmt;
                    String query = "DELETE FROM `talaria_db`.`user` WHERE `username` = ? ";
                    pstmt = connection.prepareStatement( query );
        			pstmt.setString( 1, users.get(i).getUsername()); 
        			pstmt.executeUpdate();
        			// LOG:
                    log("", "All expired accounts are deleted.", now);
                } catch (Exception e)
                {
                	// LOG:
                    log("", "Failed to delete all expired accounts.", now);
                    e.printStackTrace();
                }
            }
    	}
    }
    
    private static void deleteExpiredAccount(String username) {
    	boolean delete = false;
    	Date now = Calendar.getInstance().getTime();
    	User user = getUser(username);
        //    	temp password set		 AND	expired
        if (user.getTemppw_status() == 1 && now.after(user.getTemppw_timestamp()))
        	delete = true;
        else delete = false;
        
        if (delete) {
        	db = new DBConnection();
            java.sql.Connection connection = db.getConnection();
            try
            {
                PreparedStatement pstmt;
                String query = "DELETE FROM `talaria_db`.`user` WHERE `username` = ? ";
                pstmt = connection.prepareStatement( query );
    			pstmt.setString( 1, username); 
    			pstmt.executeUpdate();
    			// LOG:
                log(username , "This expired account is deleted.", now);
            } catch (Exception e)
            {
            	// LOG:
                log(username, "Failed to delete this expired account.", now);
                e.printStackTrace();
            }
        }
    }
    
    public static boolean checkAuthentication(String username, String action) {
    	User user = getUser(username);
    	int idrole = user.getUserType();
    	String checkActivity = "";
    	
    	switch(action) {
    		case "purchase": checkActivity = "purchaseProduct"; break; 
    		case "review": checkActivity = "reviewProduct"; break;
    		case "addproduct": checkActivity = "addProduct"; break;
    		case "editproduct": checkActivity = "editProduct"; break;
    		case "deleteproduct": checkActivity = "deleteProduct"; break;
    		case "viewsales": checkActivity = "viewSales"; break;
    		case "createaccount": checkActivity = "createAccount"; break;
    		case "assignpassword" : checkActivity = "assignPassword"; break;
    		default: break;
    	}
    	
    	db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        int authenticate = 0;
    	try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "SELECT DISTINCT ? FROM user, authentication WHERE user_type = id and user_type = ?";
            pstmt = connection.prepareStatement( query );
			pstmt.setString( 1, checkActivity);
			pstmt.setInt(2, idrole);
			rs = pstmt.executeQuery( );
            if (rs.next())
            {
                authenticate = rs.getInt(checkActivity);
            }
            
         // LOG:
            Date now = Calendar.getInstance().getTime();
            log(username , ": Authenticating user - success!", now);
        } catch (Exception e)
        {
            // LOG:
            Date now = Calendar.getInstance().getTime();
            log(username, ": Authenticating user - failed.", now);
            e.printStackTrace();
        }
    	
    	if (authenticate == 1)
        	return true;
        else return false;
    }
    
    private static void log(String username, String action, Date timestamp) {
    	// TODO: if username is "", find username if logged in.
    	
    	String logcontent = username + " " + action;
    	Timestamp currentTimestamp = new java.sql.Timestamp(timestamp.getTime());
    	db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "INSERT INTO log(log_desc, log_time) VALUES (?, ?)";
            pstmt = connection.prepareStatement( query );
            pstmt.setString( 1, logcontent); 
            pstmt.setTimestamp( 2, currentTimestamp);
            pstmt.executeUpdate();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}