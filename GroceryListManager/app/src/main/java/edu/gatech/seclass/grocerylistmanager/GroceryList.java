package edu.gatech.seclass.grocerylistmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Chithra Shetty on 10/10/16.
 */
public class GroceryList {

    private int listID;
    private String listName;
    private List<GroceryItem> itemList;
//<<<<<<< HEAD
//=======
//    private DBInterface DBConn;
//>>>>>>> f3a771db4ae1f057652a2f299f7365abaedcdedc

    public GroceryList(int listID, String listName)
    {
        this.listID = listID;
        this.listName = listName;
        this.itemList = new ArrayList<GroceryItem>();
        //DBConn = GroceryListManager.DBConn;
    }

    public GroceryList(){}

    public int getListID(){
        return  this.listID;
    }

    public String getListName(){
        return this.listName;
    }

    public List<GroceryItem> getGroceryItemList(){
        return this.itemList;
    }

//    public List<GroceryItem> getAllGroceryItemList( int listID ) {
//        List<GroceryItem > items = new ArrayList<GroceryItem>();
//        List<GroceryItem> tempArray = new ArrayList<GroceryItem>();
//        tempArray = DBConn.getAllGroceryItemListFromDB();
//        for (GroceryItem item:tempArray) {
//
//            items.add(item);
//        }
//        return items;
//    }

    /**
     * Grocery List manager can change only the list name.
     * Other properties can not be changed through setter
     * @param listName
     */
    public void setListName(String listName){
        this.listName = listName;
    }

    /**
     *
     * @return
     */
    public Map<String,List<Item>> getHierarchicalList()
    {
        Map<String,List<Item>> records = null;
        //Map<String,List<String>> records = DBConn.getUpdatedTypeItemList();
        return records;
    }


    /**
     * This method returns array of (itemName,itemType). If array has one element and item name matches means,
     * user searched item is present in database. If array has more than one element, then there
     * are multiple matches for the item name user is searching in database.
     *
     * @param itemName
     * @return
     */
    public String[][] searchItem(String itemName)
    {
        String[][] temp=null;
        //return DBConn.searchItemInDB(itemName);
        return temp;
    }


    /**
     * This method adds the new item to the database with the existing item type.
     *
     *
     */
    public void addNewItem(GroceryItem item)
    {
        GroceryListManager listManager;
        listManager = GroceryListManagerSingleton.getInstance();
        listManager.DBConn.addNewITemToDB(item.getItemID(), item.getItemType(), item.getItemName());
        this.itemList.add(item);
        // This method will update allItems in Grocery list manager
        listManager.UpdateAllItems();
    }

    /**
     * This method is used to add item to the current grocery list along with adding it to the list in database.
     *
     * @param itemID
     * @param itemName
     * @param itemType
     * @param quantityAmount
     * @param quantityUnit
     */
    public void addItemToList(int itemID, String itemName, String itemType, double quantityAmount, String quantityUnit)
    {
        GroceryItem groceryItem = new GroceryItem(itemID, itemName, itemType, quantityAmount, quantityUnit, false);
        this.itemList.add(groceryItem);

        GroceryListManager listManager = GroceryListManagerSingleton.getInstance();
        listManager.DBConn.addGroceryItemToListInDB(this.listID, this.listName, itemID, convertToInt(false), quantityAmount, quantityUnit);
    }

    /**
     * This method is used to change the quantity of the item identified by item ID in currrent list
     * and saving this info in database list identified by listID
     * @param itemID
     * @param newQuantityAmount
     * @param newQuantityUnit
     */
    public void changeQuantity(int itemID, double newQuantityAmount, String newQuantityUnit )
    {
        for (GroceryItem temp : this.itemList) {
            if (temp.getItemID() == itemID) {
                temp.setQuantityAmount(newQuantityAmount);
                temp.setQuantityUnit(newQuantityUnit);
                GroceryListManager listManager = GroceryListManagerSingleton.getInstance();
                listManager.DBConn.changeGroceryItemQuantityInDB(this.listID, itemID, newQuantityAmount, newQuantityUnit);
                break;
            }
        }

    }

    /**
     * This method is used to delete item from the current list and save this in database list identified by listID.
     * @param itemID
     */
    public void deleteItemFromList(int itemID)
    {
        for (Iterator<GroceryItem> iter = this.itemList.listIterator(); iter.hasNext(); ) {
            GroceryItem temp = iter.next();
            if (temp.getItemID() == itemID) {
                iter.remove();
                GroceryListManager listManager = GroceryListManagerSingleton.getInstance();
                listManager.DBConn.deleteGroceryItemFromListInDB(this.listID, itemID);
                break;
            }
        }
    }

    /**
     * This method is used to checkOff item in the current list and update this info in the database list
     * identified by listID.
     * @param itemID
     * @param checkOff
     */
    public void checkOffItem(int itemID, boolean checkOff)
    {
////<<<<<<< HEAD
////<<<<<<< HEAD
//
//        GroceryItem tempItem = this.itemList.get(itemID);
//        tempItem.setCheckOff(checkOff);
//        GroceryListManager listManager = GroceryListManagerSingleton.getInstance();
//        listManager.DBConn.checkoffGroceryItemInDB(this.listID, itemID, convertToInt(checkOff));
////=======
        for (GroceryItem value : itemList) {
            if(value.getItemID() == itemID) {
                value.setCheckOff(checkOff);
        GroceryListManager listManager = GroceryListManagerSingleton.getInstance();
        listManager.DBConn.checkoffGroceryItemInDB(this.listID, itemID, convertToInt(checkOff));
            }
        }
//
//>>>>>>> 02f3bbcf59bd6f24df230e346ab0b0a3d8282c48
//=======
//        for (GroceryItem value : itemList) {
//            if(value.getItemID() == itemID) {
//                value.setCheckOff(checkOff);
//                DBConn.checkoffGroceryItemInDB(this.listID, itemID, convertToInt(checkOff));
//                break;
//            }
//        }
//
//>>>>>>> f3a771db4ae1f057652a2f299f7365abaedcdedc
    }

    /**
     * This method is used to check off all items in the database and save this info in the database
     * identified by list ID
     * @param checkOff
     */
    public void checkOffAllItems(boolean checkOff)
    {
        for (GroceryItem value : itemList) {
            value.setCheckOff(checkOff);
        }
        GroceryListManager listManager = GroceryListManagerSingleton.getInstance();
        listManager.DBConn.checkoffAllGroceryItemsInDB(this.listID, convertToInt(checkOff));
    }

    public int convertToInt(boolean myBoolean){

        int result = (myBoolean) ? 1 : 0 ;
        return result;
    }


    public void populateList(List<GroceryItem> items) {
        this.itemList = items;
    }
    public ArrayList<Item> getAllItemForItemType(String itemType) {
        ArrayList<Item> itemList = new ArrayList<>();
        GroceryListManager listManager = GroceryListManagerSingleton.getInstance();
        Map<Integer,Item> items = listManager.getAllItems();

        for (Map.Entry<Integer, Item> mapping : items.entrySet()) {
            Item tempItem = mapping.getValue();
            if (tempItem.getItemType().equals(itemType)) {
                itemList.add(tempItem);
            }
        }
//        for( Item temp : items){
//            if(temp.getItemType().equals(itemType)){
//                itemList.add(temp);
//            }
//        }

        return itemList;
    }

}
