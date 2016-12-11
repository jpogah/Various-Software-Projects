package edu.gatech.seclass.grocerylistmanager;

/**
 * Created by Chithra Shetty on 10/10/16.
 */
public class Item {
    private int itemID;
    private String itemName;
    private String itemType;

    public Item(){}

    public Item(int itemID, String itemName, String itemType) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemType = itemType;
    }

    public void setItemID(int itemID){
        this.itemID = itemID;
    }

    public int getItemID(){
        return this.itemID;
    }

    public String getItemName(){
        return this.itemName;
    }

    public String getItemType(){
        return this.itemType;
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    public void setItemType(String itemType){
        this.itemType = itemType;
    }
}
