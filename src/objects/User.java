/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.Date;

/**
 *
 * @author Kingston
 */
public class User
{
    private String username;
    private String password;
    private int userType;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String email;
    private String billingAddrss;
    private String shippingAddress;
    private String cardNumber;
   
    private int temppw_status;
    private Date temppw_timestamp;

    public User(String username, String password, int userType, String firstName, String middleInitial, String lastName, String email, String billingAddrss, String shippingAddress, String cardNumber, int temppw_status, Date temppw_timestamp)
    {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.email = email;
        this.billingAddrss = billingAddrss;
        this.shippingAddress = shippingAddress;
        this.cardNumber = cardNumber;
        
        this.temppw_status = temppw_status;
        this.temppw_timestamp = temppw_timestamp;
    }

    public User()
    {
        this.username = "";
        this.password = "";
        this.userType = -1;
        this.firstName = "";
        this.middleInitial = "";
        this.lastName = "";
        this.email = "";
        this.billingAddrss = "";
        this.shippingAddress = "";
        this.cardNumber = "";
        this.temppw_status = 0;
        this.temppw_timestamp = null;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getUserType()
    {
        return userType;
    }

    public void setUserType(int userType)
    {
        this.userType = userType;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleInitial()
    {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial)
    {
        this.middleInitial = middleInitial;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getBillingAddress()
    {
        return billingAddrss;
    }

    public void setBillingAddrss(String billingAddrss)
    {
        this.billingAddrss = billingAddrss;
    }

    public String getShippingAddress()
    {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

	public int getTemppw_status() {
		return temppw_status;
	}

	public void setTemppw_status(int temppw_status) {
		this.temppw_status = temppw_status;
	}

	public Date getTemppw_timestamp() {
		return temppw_timestamp;
	}

	public void setTemppw_timestamp(Date temppw_timestamp) {
		this.temppw_timestamp = temppw_timestamp;
	}
    
    
}
