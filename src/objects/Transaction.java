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
public class Transaction
{
    private int id;
    private String username;
    private int productId;
    private int quantity;
    private double unitPrice;
    private double lineTotal;
    private Double total;

    public Transaction(int id, String username, Double total)
    {
        this.id = id;
        this.username = username;
        this.total = total;
    }

    public Transaction(int id, String username, int productId, int quantity, double unitPrice, double lineTotal, Double total)
    {
        this.id = id;
        this.username = username;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
        this.total = total;
    }

    public Transaction()
    {
        this.id = -1;
        this.username = "";
        this.total = 0.00;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public double getLineTotal()
    {
        return lineTotal;
    }

    public void setLineTotal(double lineTotal)
    {
        this.lineTotal = lineTotal;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Double getTotal()
    {
        return total;
    }

    public void setTotal(Double total)
    {
        this.total = total;
    }
    
    
    
    
}
