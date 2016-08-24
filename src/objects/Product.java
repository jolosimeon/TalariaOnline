/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author Kingston
 */
public class Product
{
    private int id;
    private int type;
    private String name;
    private String description;
    private Double price;
    private String adderUsername;

    public Product(int id, int type, String name, String description, Double price, String adderUsername)
    {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.adderUsername = adderUsername;
    }

    public Product()
    {
        this.id = -1;
        this.type = -1;
        this.name = "";
        this.description = "";
        this.price = 0.00;
        this.adderUsername = "";
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public String getAdderUsername()
    {
        return adderUsername;
    }

    public void setAdderUsername(String adderUsername)
    {
        this.adderUsername = adderUsername;
    }
    
    
    
    
}
