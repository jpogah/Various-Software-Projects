package edu.gatech.seclass.grocerylistmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by shettycm on 10/13/16.
 */

public class GroceryListManager {
    public static DBInterface DBConn;
    private Map<String,GroceryList> groceryLists;
    private Map<Integer, Item> allItems;
    private GroceryList currentList;
    private int nextItemID = 0;
    private int nextListID = 0;

    public GroceryListManager() {
//<<<<<<< HEAD
        groceryLists = new HashMap<String,GroceryList>();

//=======
       // groceryLists = new ArrayList<>();
        currentList = new GroceryList();
        //GroceryList.DBConn = DBConn;
//>>>>>>> f3a771db4ae1f057652a2f299f7365abaedcdedc
    }

    public int getNextItemID (){
        return nextItemID;
    }

    public int getNextListID (){
        return nextListID;
    }

    public void setNextItemID (int nextItemIDFromDatabase){
        nextItemID = nextItemIDFromDatabase;
    }

    public void setNextListID (int nextListIDFromDatabase){
        nextListID = nextListIDFromDatabase;
    }

    /**
     * This method is called from grocery list addNewItem to update the allItems when new item is created.
     */
    public void UpdateAllItems() {
        this.allItems = DBConn.getAllItemFromDB();
    }

    //This method is called
    public void initialize(){
        allItems = DBConn.getAllItemFromDB();
        groceryLists = DBConn.getAllGroceryItemListFromDB();
    }

    public void setCurrentList(String listName){

        this.currentList = groceryLists.get(listName);

    }

    public Item getItemWithNameAndType(String itemName, String itemType){
        for (Map.Entry<Integer,Item> mapping : allItems.entrySet())
        {
            if(mapping == null)
                System.out.println("It is NULL!");
            Item tempItem = mapping.getValue();
            if(tempItem.getItemName().equals(itemName) && tempItem.getItemType().equals(itemType)){
                return tempItem;
            }
            //System.out.println(mapping.getKey());
            //listOfGroceryLists.get(mapping.getKey()).populateList(mapping.getValue());
        }

        return null;
    }
    public GroceryList getCurrentList(){
        return currentList;
    }
//<<<<<<< HEAD
    public Map<String,GroceryList> getAllLists() {
        //to do:Call DB method to fetch all groceryList
//=======
//    public List<GroceryList> getAllLists() {
//>>>>>>> f3a771db4ae1f057652a2f299f7365abaedcdedc
        return this.groceryLists;
    }

    public boolean createList(String name) {
        if (groceryLists.containsKey(name)) {
            //TODO: display error telling user that the name of the list already exists
            return false;
        } else {

            int listID = nextListID;
            nextListID++;
            // to do: get new list id from DBInterface
            // create a new list
            GroceryList groceryList = new GroceryList(listID, name);
            groceryLists.put(name, groceryList);
            currentList = groceryList;
            DBConn.addNewListToDB(currentList);
        }

        return true;
    }

    public ArrayList<Item> searchItems(String searchCriteria)
    {
        ArrayList<Item> results = new ArrayList<Item>();
        for (Map.Entry<Integer,Item> mapping : allItems.entrySet()) {
            if(mapping == null)
                System.out.println("It is NULL!");
            Item tempItem = mapping.getValue();
            if(tempItem.getItemName().toLowerCase().contains(searchCriteria.toLowerCase())){
                results.add(tempItem);
            }
        }
        return results;
    }

    /**
     * This method checks if the requested list name already exist.
     * If yes, it returns false, else returns true indicating new list
     * is created
    // * @param name
     * @return true or false
     */
//    public boolean createList(String name) {
//        int listID = 0;
//
//        // Check if list with same name exist. if not create new or else false(
//        // indicating that list exist with that name)
//        for(GroceryList temp : groceryLists) {
//            if(temp.getListName().equals(name)){
//                return false;
//            }
//            // logic to generate new list id, which will be max(all listID)+1
//            listID = temp.getListID() > listID ? temp.getListID() : listID;
//        }
//        GroceryList groceryList = new GroceryList(listID+1, name);
//        groceryLists.add(groceryList);
//        currentList = groceryList;
//        DBConn.addNewListToDB(currentList);
//>>>>>>> 02f3bbcf59bd6f24df230e346ab0b0a3d8282c48
//
//        return true;

    public Item getITem(int itemID){
        return this.allItems.get(itemID);
    }


    public void renameList(String listName, String newName) {


    /**
     * This method checks if the new name exists. If yes, it returns false else returns true
     * indicating list name is changed.
     * @param listName
     * @param newName
     * @return true or false
     */
//    public boolean renameList(String listName, String newName) {
//        boolean result = true;
//        // Check if list with same name exist. if not rename th list or else false(
//        // indicating that list exist with that name)
//        for(GroceryList temp : groceryLists) {
//            if (temp.getListName().equals(newName)) {
//                return false;
//            }
//        }
//        DBConn.renameListInDB(listName, newName);
//>>>>>>> 02f3bbcf59bd6f24df230e346ab0b0a3d8282c48
       // GroceryList tempGroceryList = this.groceryLists.get(listName);
        //tempGroceryList.setListName(newName);
//        this.groceryLists.put(newName, tempGroceryList);
//

//        for (Map.Entry<String,GroceryList> mapping : groceryLists.entrySet()) {
//
//            System.out.println(mapping.getKey());
//            }
//        System.out.println(newName);
//        System.out.println(listName);
        GroceryList tempGroceryList = this.groceryLists.remove(listName);
        tempGroceryList.setListName(newName);
        groceryLists.put(newName,tempGroceryList);
        DBConn.renameListInDB(newName, listName);
//        for(GroceryList list : groceryLists) {
//            if(list.getListName().equals(listName)) {
//                list.setListName(newName);
//            }
//        }
//        currentList.setListName(newName);

        //return true;
    }

    /**
     * This method deletes the list with the given name.
     * @param listName
     */
    public void deleteList(String listName) {
        this.groceryLists.remove(listName);
        DBConn.deleteListFromDB(listName);
//        Iterator<GroceryList> iterator = groceryLists.iterator();
//        while (iterator.hasNext()) {
//            GroceryList tempList = iterator.next();
//            if (tempList.getListName().equals(listName)) {
//                iterator.remove();
//                break;
//            }
//        }


    }

    /**
     * This method selects the list with name provided and returns the
     * selected list.
     * @param listName
     * @return selected grocery list
     */
    public GroceryList selectList(String listName) {
        GroceryList selectList = null;
//        for(GroceryList list : groceryLists) {
//            if(list.getListName().equals(listName)) {
//                selectList = list;
//            }
//        }
        currentList = this.groceryLists.get(listName);
        return selectList;
    }


    public Map<Integer,Item> getAllItems() {
        //List<Item> items = DBConn.getAllItemFromDB();

        return this.allItems;
    }
//    // May not be required
//    public List<Item> getAllItems() {
//        List<Item> items = DBConn.getAllItemFromDB();
//        return items;
//>>>>>>> 02f3bbcf59bd6f24df230e346ab0b0a3d8282c48
//    }

}