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
public class Sale
{
    private int productId;
    private String productName;
    private int totalQuantitySold;
    private double totalSales;

    public Sale(int productId, String productName, int totalQuantitySold, double totalSales)
    {
        this.productId = productId;
        this.productName = productName;
        this.totalQuantitySold = totalQuantitySold;
        this.totalSales = totalSales;
    }

    public Sale()
    {
        this.productId = -1;
        this.productName = "";
        this.totalQuantitySold = -1;
        this.totalSales = 0.00;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public int getTotalQuantitySold()
    {
        return totalQuantitySold;
    }

    public void setTotalQuantitySold(int totalQuantitySold)
    {
        this.totalQuantitySold = totalQuantitySold;
    }

    public double getTotalSales()
    {
        return totalSales;
    }

    public void setTotalSales(double totalSales)
    {
        this.totalSales = totalSales;
    }
    
    
    
}
