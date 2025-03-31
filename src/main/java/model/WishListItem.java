package model;

public class WishListItem {
private int itemId;
private String itemName;
private String itemDescription;
private double itemPrice;
private String itemLink;

public WishListItem(int itemId,String itemName,String itemDescription,double itemPrice,String itemLink){
    this.itemId = itemId;
    this.itemName = itemName;
    this.itemDescription = itemDescription;
    this.itemPrice = itemPrice;
    this.itemLink=itemLink;
}
public WishListItem(){

}
//--------Getters----------
    public int getItemId(){
    return itemId;
    }
    public String getItemName(){
    return itemName;
    }
    public String getItemDescription(){
    return itemDescription;
    }
    public double getItemPrice(){
    return itemPrice;
    }
    public String getItemLink(){
    return itemLink;
    }
    //-----------------Setters----------
    public void setItemId(int itemId){
    this.itemId = itemId;
    }
    public void setItemName (String itemName){
    this.itemName = itemName;
    }
    public void setItemDescription (String itemDescription){
    this.itemDescription = itemDescription;
    }
    public void setItemPrice (double itemPrice){
this.itemPrice = itemPrice;
    }
    public void getItemLink(String itemLink){
    this.itemLink = itemLink;
    }


}
