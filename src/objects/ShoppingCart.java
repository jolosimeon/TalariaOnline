package objects;

import java.util.ArrayList;

public class ShoppingCart {
	private ArrayList<TransactionLineItem> products;
	
	public ShoppingCart() {
		products = new ArrayList<>();
	}
	
	public ArrayList<TransactionLineItem> getProducts() {
		return products;
	}
	
    public void setProducts(ArrayList<TransactionLineItem> products) {
    	this.products = products;
    }
    
    public void empty() {
      products.clear();
    }
    
    public void add(TransactionLineItem np)
    {
     for(int i = 0; i < products.size(); i++)
     {
    	 TransactionLineItem product = products.get(i);
      if(np.getProductId() == product.getProductId()) // already in the cart?
      {
       product.setQuantity(product.getQuantity() + np.getQuantity()); 
       return;
      }
     }
     products.add(np); // no, add it as a new item
    }
    //
    // remove an item with a given id from the shopping cart
    //
    public void remove(int id)
    {
     for(int i = 0; i < products.size(); i++)
     {
      TransactionLineItem product = products.get(i);
      if(id == product.getProductId()) // item in the cart?
      {
       products.remove(i); // remove it
       break;
      }
     }
    
    }
    
    public Double getTotal() {
    	Double total = 0.0;
    	for (int i  = 0; i < products.size(); i++)
    		total += products.get(i).getLineTotal();
    	return total;
    }
    
}
