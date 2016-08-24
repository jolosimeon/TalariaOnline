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
public class TransactionLineItem
{
    private int transactionId;
    private int productId;
    private int quantity;
    private double unitPrice;
    private double lineTotal;

    public TransactionLineItem(int transactionId, int productId, int quantity, double unitPrice, double lineTotal)
    {
        this.transactionId = transactionId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    public TransactionLineItem()
    {
        this.transactionId = -1;
        this.productId = -1;
        this.quantity = -1;
        this.unitPrice = 0.00;
        this.lineTotal = 0.00;
    }

    public int getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(int transactionId)
    {
        this.transactionId = transactionId;
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
        return unitPrice * quantity;
    }

    public void setLineTotal(double lineTotal)
    {
        this.lineTotal = lineTotal;
    }
    
    
    
}
