package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

import com.mysql.jdbc.Connection;

/*http://www.java2s.com/Code/Jar/s/Downloadshiroall121jar.htm*/
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;


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
                user = new User(rs.getString("username"), rs.getString("password"), rs.getInt("user_type"), rs.getString("fname"), rs.getString("minitial"), rs.getString("lname"), rs.getString("email"), rs.getString("billing_addr"), rs.getString("shipping_addr"), rs.getString("card_no"));
            }
        } catch (Exception e)
        {
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
                user = new User(rs.getString("username"), rs.getString("password"), rs.getInt("user_type"), rs.getString("fname"), rs.getString("minitial"), rs.getString("lname"), rs.getString("email"), rs.getString("billing_addr"), rs.getString("shipping_addr"), rs.getString("card_no"));
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }
    
    public static ArrayList<User> getAllUsers()
    {
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
                user = new User(rs.getString("username"), rs.getString("password"), rs.getInt("user_type"), rs.getString("fname"), rs.getString("minitial"), rs.getString("lname"), rs.getString("email"), rs.getString("billing_addr"), rs.getString("shipping_addr"), rs.getString("card_no"));
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void addProductManagerAccount(User pm)
    {
    	RandomNumberGenerator rng = new SecureRandomNumberGenerator();
    	ByteSource salt = rng.nextBytes();
    	String hashedPasswordBase64 = new Sha256Hash(pm.getPassword(), salt, 1024).toBase64();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "INSERT INTO `talaria_db`.`user` (`username`, `password`, `user_type`, `fname`, `minitial`, `lname`, `email`, `salt`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement( query );
            pstmt.setString( 1, pm.getUsername());
            pstmt.setString( 2, hashedPasswordBase64); 
            pstmt.setInt( 3, 3); 
            pstmt.setString( 4, pm.getFirstName()); 
            pstmt.setString( 5, pm.getMiddleInitial()); 
            pstmt.setString( 6, pm.getLastName()); 
            pstmt.setString( 7, pm.getEmail());
            pstmt.setString( 8, salt.toBase64());
            pstmt.executeUpdate();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void addAccountingManagerAccount(User am)
    {
    	RandomNumberGenerator rng = new SecureRandomNumberGenerator();
    	ByteSource salt = rng.nextBytes();
    	String hashedPasswordBase64 = new Sha256Hash(am.getPassword(), salt, 1024).toBase64();
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement pstmt;
            String query = "INSERT INTO `talaria_db`.`user` (`username`, `password`, `user_type`, `fname`, `minitial`, `lname`, `email`, `salt`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement( query );
            pstmt.setString( 1, am.getUsername());
            pstmt.setString( 2, hashedPasswordBase64); 
            pstmt.setInt( 3, 4); 
            pstmt.setString( 4, am.getFirstName()); 
            pstmt.setString( 5, am.getMiddleInitial()); 
            pstmt.setString( 6, am.getLastName()); 
            pstmt.setString( 7, am.getEmail());
            pstmt.setString( 8, salt.toBase64());
            pstmt.executeUpdate();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void addProduct(Product p)
    {
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(int id)
    {
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void editProduct(int type, String name, String description, Double price, int id)
    {
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
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
            
        } catch (Exception e)
        {
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
            
        } catch (Exception e)
        {
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
            
        } catch (Exception e)
        {
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
            
        } catch (Exception e)
        {
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return product_list;
    }

    
    public static Transaction addTransaction(Transaction t)
    {
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }

    public static void addTransactionLineItem(TransactionLineItem t)
    {
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void addReview(Review r)
    {
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static boolean boughtItem(String username, int prod_id) {
    	ArrayList<Transaction> transactions = getAllTransactionsByProduct(prod_id);
    	for (int i = 0; i < transactions.size(); i++)
    		if (transactions.get(i).getUsername().equals(username))
    			return true;
    	return false;
    }
    
    public static void changePassword(String username, String oldpw, String newpw)
    {
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            PreparedStatement pstmt;
            String query = "UPDATE user SET password = ? WHERE username = ?";
            pstmt = connection.prepareStatement( query );
            pstmt.setString( 1, newpw);
            pstmt.setString( 2, username);
            pstmt.executeUpdate();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void changePassword(String username, String newpw)
    {
        db = new DBConnection();
        java.sql.Connection connection = db.getConnection();
        try
        {
            PreparedStatement pstmt;
            String query = "UPDATE user SET password = ? WHERE username = ?";
            pstmt = connection.prepareStatement( query );
            pstmt.setString( 1, newpw);
            pstmt.setString( 2, username);
            pstmt.executeUpdate();
            System.out.println("change is coming");
        } catch (Exception e)
        {
            e.printStackTrace();
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
            
        } catch (Exception e)
        {
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
            
        } catch (Exception e)
        {
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
            
        } catch (Exception e)
        {
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
            
        } catch (Exception e)
        {
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return transaction_list;
    }

    public static ArrayList<Sale> getAllTotalSalesPerProduct()
    {
        Sale sale = null;
        ArrayList<Sale> sale_list = new ArrayList<Sale>();
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sale_list;
    }

    public static ArrayList<Sale> getTotalSalesPerProduct(int id)
    {
        Sale sale = null;
        ArrayList<Sale> sale_list = new ArrayList<Sale>();
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sale_list;
    }

    public static ArrayList<Sale> getAllTotalSalesPerType()
    {
        Sale sale = null;
        ArrayList<Sale> sale_list = new ArrayList<Sale>();
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sale_list;
    }

    public static ArrayList<Sale> getTotalSalesPerType(int type)
    {
        Sale sale = null;
        ArrayList<Sale> sale_list = new ArrayList<Sale>();
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
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sale_list;
    }

}