package edu.gatech.seclass.grocerylistmanager;

/**
 * Created by Chithra Shetty on 10/10/16.
 */
public class GroceryItem extends Item{
    boolean checkOff;
    double quantityAmount;
    String quantityUnit;

    public GroceryItem(){
        super();
    }

    public GroceryItem(int itemID, String itemName, String itemType, double quantityAmount, String quantityUnit, boolean checkOff )
    {
        super(itemID, itemName, itemType );
        this.checkOff = checkOff;
        this.quantityAmount = quantityAmount;
        this.quantityUnit = quantityUnit;
    }

    public boolean getCheckOff(){
        return this.checkOff;
    }

    public double getQuantityAmount(){
        return this.quantityAmount;
    }

    public String getQuantityUnit(){
        return this.quantityUnit;
    }

    public void setCheckOff(boolean checkOff){
        this.checkOff = checkOff;
    }

    public void setQuantityAmount(double quantityAmount){
        this.quantityAmount = quantityAmount;
    }

    public void setQuantityUnit(String quantityUnit){
        this.quantityUnit = quantityUnit;
    }

}
