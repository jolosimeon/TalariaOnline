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
public class Review
{
    private int productId;
    private int stars;
    private String details;
    private String username;

    public Review(int productId, int stars, String details, String username)
    {
        this.productId = productId;
        this.stars = stars;
        this.details = details;
        this.username = username;
    }

    public Review()
    {
        this.productId = -1;
        this.stars = -1;
        this.details = "";
        this.username = "";
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public int getStars()
    {
        return stars;
    }

    public void setStars(int stars)
    {
        this.stars = stars;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
    
    
    
    
    
}
