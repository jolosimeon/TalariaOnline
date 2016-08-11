package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Model.DBConnection;

import com.mysql.jdbc.Connection;

import jspBeans.Item;
import jspBeans.Notification;
import jspBeans.Order;
import jspBeans.Organization;
import jspBeans.User;

public class Model
{

    private static DBConnection db;

    public static User getUser(String user_id, String password)
    {
        User user = null;
        db = new DBConnection();
        db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement statement;
            String query = "SELECT * FROM user WHERE user_id = '" + user_id + "' AND password = '" + password + "'";
            statement = db.getConnection().prepareStatement(query);
            rs = statement.executeQuery();
            if (rs.next())
            {
                user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("user_type"), rs.getInt("manager_id"), rs.getString("password"));
            }
            
            if (user == null)
            {
            query = "SELECT * FROM user WHERE email = '" + user_id + "' AND password = '" + password + "'";
            statement = db.getConnection().prepareStatement(query);
            rs = statement.executeQuery();
            if (rs.next())
            {
                user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("user_type"), rs.getInt("manager_id"), rs.getString("password"));
            }
            }
            
            if (user != null && user.getManager_id() != 0)
            {
                query = "SELECT o.org_id, org_name, password FROM manager m, organization o WHERE m.manager_id = '" + user.getManager_id() + "' AND o.org_id = m.org_id";
                statement = db.getConnection().prepareStatement(query);
                rs = statement.executeQuery();
                while (rs.next())
                {
                    user.addOrg(new Organization(rs.getInt("org_id"), rs.getString("org_name"), rs.getString("password")));
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }
    
    public static User getUser(String user_id)
    {
        User user = null;
        db = new DBConnection();
        db.getConnection();
        try
        {
            ResultSet rs;
            PreparedStatement statement;
            String query = "SELECT * FROM user WHERE user_id = '" + user_id + "'";
            statement = db.getConnection().prepareStatement(query);
            rs = statement.executeQuery();
            if (rs.next())
            {
                user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("user_type"), rs.getInt("manager_id"), rs.getString("password"));
            }
            
            if (user == null)
            {
            query = "SELECT * FROM user WHERE email = '" + user_id  + "'";
            statement = db.getConnection().prepareStatement(query);
            rs = statement.executeQuery();
            if (rs.next())
            {
                user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("user_type"), rs.getInt("manager_id"), rs.getString("password"));
            }
            }
            if (user != null && user.getManager_id() != 0)
            {
                query = "SELECT o.org_id, o.org_name, o.password FROM manager m, organization o WHERE m.manager_id = '" + user.getManager_id() + "' AND o.org_id = m.org_id";
                statement = db.getConnection().prepareStatement(query);
                rs = statement.executeQuery();
                while (rs.next())
                {
                    user.addOrg(new Organization(rs.getInt("org_id"), rs.getString("org_name"), rs.getString("password")));
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }
}
