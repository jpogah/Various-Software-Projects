package edu.gatech.seclass.grocerylistmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by Mohammad on 10/10/2016.
 */
public class DBInterface extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 19;
    private static final String DATABASE_NAME = "groceryListManagerDB.db";
    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_LIST_CONTENTS = "listContents";

    public static final String COLUMN_ITEMS_ID = "itemID";
    public static final String COLUMN_ITEMS_TYPE = "itemType";
    public static final String COLUMN_ITEMS_NAME = "itemName";

    public static final String COLUMN_LISTS_CONTENTS_ID = "listID";
    public static final String COLUMN_LISTS_CONTENTS_ITEMID = "itemID";
    public static final String COLUMN_LISTS_CONTENTS_QUANTITYAMOUNT = "quantityAmount";
    public static final String COLUMN_LISTS_CONTENTS_QUANTITYUNIT = "quantityUnit";
    public static final String COLUMN_LISTS_CONTENTS_CHECKOFF = "checkoff";
    public static final String COLUMN_LISTS_CONTENTS_NAME = "listName";

    public static final String COLUMN_LISTS_ID = "listID";
    public SQLiteDatabase myDB;


    public DBInterface(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myDB = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        myDB = db;
        System.out.println("I am here");
        String CREATE_ITEMS_TABLE = "CREATE TABLE " +
                TABLE_ITEMS + "("
                + COLUMN_ITEMS_ID + " INTEGER PRIMARY KEY," + COLUMN_ITEMS_TYPE
                + " TEXT," + COLUMN_ITEMS_NAME
                + " TEXT, " + "UNIQUE(" + COLUMN_ITEMS_TYPE + "," + COLUMN_ITEMS_NAME + ") ON CONFLICT ROLLBACK)";

        myDB.execSQL(CREATE_ITEMS_TABLE);

        String CREATE_LISTS_CONTENTS_TABLE = "CREATE TABLE " +
                TABLE_LIST_CONTENTS + "("
                + COLUMN_LISTS_CONTENTS_ID + " INTEGER,"  + COLUMN_LISTS_CONTENTS_NAME + " TEXT," + COLUMN_LISTS_CONTENTS_ITEMID + " INTEGER," +
                COLUMN_LISTS_CONTENTS_QUANTITYAMOUNT + " REAL," + COLUMN_LISTS_CONTENTS_QUANTITYUNIT
                + " TEXT," + COLUMN_LISTS_CONTENTS_CHECKOFF
                + " INTEGER, " + "PRIMARY KEY ( " + COLUMN_LISTS_CONTENTS_ID + ", " + COLUMN_LISTS_CONTENTS_ITEMID + "))";


        myDB.execSQL(CREATE_LISTS_CONTENTS_TABLE);


        dummyInitialize();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        myDB.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        myDB.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST_CONTENTS);
        onCreate(myDB);
    }

    public void addNewListToDB(GroceryList myList) {
//<<<<<<< HEAD

        ContentValues values = new ContentValues();
        values.put(COLUMN_LISTS_CONTENTS_ID, myList.getListID());
        values.put(COLUMN_LISTS_CONTENTS_NAME ,myList.getListName());
        values.put(COLUMN_LISTS_CONTENTS_ITEMID, -1);
        values.put(COLUMN_LISTS_CONTENTS_CHECKOFF, 0);
        values.put(COLUMN_LISTS_CONTENTS_QUANTITYAMOUNT, 0);
        values.put(COLUMN_LISTS_CONTENTS_QUANTITYUNIT, "0");

        //SQLiteDatabase db = this.getWritableDatabase();

        if(myDB == null)
            System.out.println("Whoa");
        myDB.insert(TABLE_LIST_CONTENTS, null, values);


        // List<GroceryItem> itemList = myList.getItemList();

//        if ( myList.getItemList()==null ||myList.getItemList().isEmpty()){
//            throw new  NullPointerException("GroceryList cannot be empty or null");
//        }
//
//            for (GroceryItem item : itemList) {
//                addGroceryItemToListInDB(myList.getListID(),myList.getListName(),item.getItemID(),
//                        CommonMethodsUtils.convertToInteger(item.getCheckOff()), item.getQuantityAmount(),
//                item.getQuantityUnit());
//            }
//=======
//        // to do: this method should create new list in DB. No need
//        // of any validation here as its taken care at GroceryListManager class
//
//        List<GroceryItem> itemList = myList.getGroceryItemList();
//            for (GroceryItem item : itemList) {
//               addGroceryItemToListInDB(myList.getListID(),myList.getListName(),item.getItemID(),
//                        CommonMethodsUtils.convertToInteger(item.getCheckOff()), item.getQuantityAmount(),
//                item.getQuantityUnit());
//           }
//>>>>>>> 02f3bbcf59bd6f24df230e346ab0b0a3d8282c48

    }

    public void renameListInDB(String newListName, String currentListName) {
        // to do: Check in DB if the new name already exist
        ContentValues values = new ContentValues();
        values.put(COLUMN_LISTS_CONTENTS_NAME , newListName);

        //SQLiteDatabase db = this.getWritableDatabase();
        myDB.update(TABLE_LIST_CONTENTS, values, COLUMN_LISTS_CONTENTS_NAME + "= ?", new String[] { currentListName });

    }
    public void deleteListFromDB(String listName) {
        // to do: Should also delete all the Grocery items belonging to the list
        //SQLiteDatabase db = this.getWritableDatabase();

        myDB.delete(TABLE_LIST_CONTENTS,COLUMN_LISTS_CONTENTS_NAME + "= ?", new String[] { listName });

    }

    public void addGroceryItemToListInDB(int listID,String listname, int itemID, int checkoff, double quantityAmount, String quantityUnit) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LISTS_CONTENTS_ID, listID);
        values.put(COLUMN_LISTS_CONTENTS_NAME ,listname);
        values.put(COLUMN_LISTS_CONTENTS_ITEMID, itemID);
        values.put(COLUMN_LISTS_CONTENTS_CHECKOFF, checkoff);
        values.put(COLUMN_LISTS_CONTENTS_QUANTITYAMOUNT, quantityAmount);
        values.put(COLUMN_LISTS_CONTENTS_QUANTITYUNIT, quantityUnit);

        //SQLiteDatabase db = this.getWritableDatabase();

        myDB.insert(TABLE_LIST_CONTENTS, null, values);


    }


    public void addNewITemToDB(int itemID, String itemType, String itemName) {

        System.out.println("Yo");
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEMS_ID, itemID);
        values.put(COLUMN_ITEMS_TYPE, itemType);
        values.put(COLUMN_ITEMS_NAME, itemName);

        //SQLiteDatabase db = this.getWritableDatabase();

        myDB.insert(TABLE_ITEMS, null, values);

    }

    public Item getItemFromDB(int itemID){
        //SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = myDB.query(TABLE_ITEMS, new String[] { COLUMN_ITEMS_ID,
                        COLUMN_ITEMS_NAME, COLUMN_ITEMS_TYPE }, COLUMN_ITEMS_ID + "=?",
                new String[] { String.valueOf(itemID) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return item;

    }


    public void changeGroceryItemQuantityInDB(int listID, int itemID, double quantityAmount, String quantityUnit) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LISTS_CONTENTS_QUANTITYAMOUNT, quantityAmount);
        values.put(COLUMN_LISTS_CONTENTS_QUANTITYUNIT, quantityUnit);

        //SQLiteDatabase db = this.getWritableDatabase();
        myDB.update(TABLE_LIST_CONTENTS, values, COLUMN_LISTS_CONTENTS_ID + "= ? AND " + COLUMN_LISTS_CONTENTS_ITEMID + " = ? ", new String[] { listID + "", itemID + "" });

    }

    public void deleteGroceryItemFromListInDB(int listID, int itemID) {

        //SQLiteDatabase db = this.getWritableDatabase();
        myDB.delete(TABLE_LIST_CONTENTS, COLUMN_LISTS_CONTENTS_ID + "= ? AND " + COLUMN_LISTS_CONTENTS_ITEMID + " = ? ", new String[] { listID + "", itemID + "" });

    }

    public void checkoffGroceryItemInDB(int listID, int itemID, int checkoff) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LISTS_CONTENTS_CHECKOFF, checkoff);

        //SQLiteDatabase db = this.getWritableDatabase();
        myDB.update(TABLE_LIST_CONTENTS, values, COLUMN_LISTS_CONTENTS_ID + "= ? AND " + COLUMN_LISTS_CONTENTS_ITEMID + " = ? ", new String[] { listID + "", itemID + "" });

    }

    public void checkoffAllGroceryItemsInDB(int listID, int checkoff) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LISTS_CONTENTS_CHECKOFF, checkoff);

        //SQLiteDatabase db = this.getWritableDatabase();
        myDB.update(TABLE_LIST_CONTENTS, values, COLUMN_LISTS_CONTENTS_ID + "= ?", new String[] { listID + ""});

    }


    public void dummyInitialize() {
        Item item1  =  new Item(1,"Mango", "Fruit");
        Item item2  =  new Item(2 , "Kale", "Vegetable");
        Item item3  =  new Item(3 , "Rice Crispy's", "Cereal");
        Item item4  =  new Item(4 , "Coffee", "Beverages");
        Item item5  =  new Item(5, "Beef", "Meat");
        Item item6  =  new Item(6 , "Eggs" , "Dairy");
        Item item7  =  new Item(7 , "Milk" , "Dairy");
        Item item8  =  new Item(8,"Strawberry", "Fruit");
        Item item9  =  new Item(9 , "Lettuce", "Vegetable");
        Item item10  =  new Item(10 , "Kellogs", "Cereal");
        Item item11  =  new Item(11 , "Tea", "Beverages");
        Item item12  =  new Item(12 , "Chicken", "Meat");
        Item item13  =  new Item(13 , "Cheese" , "Dairy");
        Item item14  =  new Item(14 , "Fish" , "Meat");
        Item item15 =  new Item(15,"Broomstick", "Household Items");
        Item item16 =  new Item(16,"Jeans", "Clothing");
        Item item17 =  new Item(17,"Headphones", "Electronics");
        Item item18 =  new Item(18,"Scissors", "Stationery");
        Item item19 =  new Item(19,"Soap", "Hygiene");
        Item item21 =  new Item(21,"Toasted Bread", "Baked Goods");
        Item item22 =  new Item(22,"Dumbells", "Sports Gear");
        Item item23 =  new Item(23,"Peach", "Fruit");
        Item item24 =  new Item(24,"Cucumber", "Vegetable");
        Item item25 =  new Item(25,"Cocoa Puffs", "Cereal");
        Item item26 =  new Item(26,"Dr. Pepper", "Beverages");
        Item item27 =  new Item(27,"Angus Ribs", "Meat");
        Item item28 =  new Item(28,"Yogurt", "Dairy");
        Item item29 =  new Item(29,"Chocolate Milk", "Dairy");
        Item item30 =  new Item(30,"Pomegrenate", "Fruit");
        Item item31 =  new Item(31,"Potatoes", "Vegetable");
        Item item32 =  new Item(32,"Kellog's", "Cereal");
        Item item33 =  new Item(33,"Pepsi", "Beverages");
        Item item34 =  new Item(34,"Wagyu Beef", "Meat");
        Item item35 =  new Item(35,"Sour Cream", "Dairy");
        Item item36 =  new Item(36,"Lamb Chops", "Meat");
        Item item37 =  new Item(37,"Garbage Cans", "Household Items");
        Item item38 =  new Item(38,"Sweater", "Clothing");
        Item item39 =  new Item(39,"Laptop Bag", "Electronics");
        Item item40 =  new Item(40,"Stapler", "Stationery");
        Item item41 =  new Item(41,"Shampoo", "Hygiene");
        Item item43 =  new Item(43,"Pandesal Bread", "Baked Goods");
        Item item44 =  new Item(44,"Pullup Bar", "Sports Gear");
        Item item45 =  new Item(45,"Plums", "Fruit");
        Item item46 =  new Item(46,"Cabbage", "Vegetable");
        Item item47 =  new Item(47,"Frosties", "Cereal");
        Item item48 =  new Item(48,"Seven Up", "Beverages");
        Item item49 =  new Item(49,"Angus Tenderloin", "Meat");
        Item item50 =  new Item(50,"Low Fat Milk", "Dairy");
        Item item51 =  new Item(51,"Frost Bites", "Dairy");
        Item item20 =  new Item(20,"Snickers", "Candy");
        Item item42 =  new Item(42,"Twix", "Candy");



        addNewITemToDB(item1.getItemID(), item1.getItemType(), item1.getItemName());
        addNewITemToDB(item2.getItemID(), item2.getItemType(), item2.getItemName());
        addNewITemToDB(item3.getItemID(), item3.getItemType(), item3.getItemName());
        addNewITemToDB(item4.getItemID(), item4.getItemType(), item4.getItemName());
        addNewITemToDB(item5.getItemID(), item5.getItemType(), item5.getItemName());
        addNewITemToDB(item6.getItemID(), item6.getItemType(), item6.getItemName());
        addNewITemToDB(item7.getItemID(), item7.getItemType(), item7.getItemName());
        addNewITemToDB(item8.getItemID(), item8.getItemType(), item8.getItemName());
        addNewITemToDB(item9.getItemID(), item9.getItemType(), item9.getItemName());
        addNewITemToDB(item10.getItemID(), item10.getItemType(), item10.getItemName());
        addNewITemToDB(item11.getItemID(), item11.getItemType(), item11.getItemName());
        addNewITemToDB(item12.getItemID(), item12.getItemType(), item12.getItemName());
        addNewITemToDB(item13.getItemID(), item13.getItemType(), item13.getItemName());
        addNewITemToDB(item14.getItemID(), item14.getItemType(), item14.getItemName());
        addNewITemToDB(item15.getItemID(), item15.getItemType(), item15.getItemName());
        addNewITemToDB(item16.getItemID(), item16.getItemType(), item16.getItemName());
        addNewITemToDB(item17.getItemID(), item17.getItemType(), item17.getItemName());
        addNewITemToDB(item18.getItemID(), item18.getItemType(), item18.getItemName());
        addNewITemToDB(item19.getItemID(), item19.getItemType(), item19.getItemName());
        addNewITemToDB(item20.getItemID(), item20.getItemType(), item20.getItemName());
        addNewITemToDB(item21.getItemID(), item21.getItemType(), item21.getItemName());
        addNewITemToDB(item22.getItemID(), item22.getItemType(), item22.getItemName());
        addNewITemToDB(item23.getItemID(), item23.getItemType(), item23.getItemName());
        addNewITemToDB(item24.getItemID(), item24.getItemType(), item24.getItemName());
        addNewITemToDB(item25.getItemID(), item25.getItemType(), item25.getItemName());
        addNewITemToDB(item26.getItemID(), item26.getItemType(), item26.getItemName());
        addNewITemToDB(item27.getItemID(), item27.getItemType(), item27.getItemName());
        addNewITemToDB(item28.getItemID(), item28.getItemType(), item28.getItemName());
        addNewITemToDB(item29.getItemID(), item29.getItemType(), item29.getItemName());
        addNewITemToDB(item30.getItemID(), item30.getItemType(), item30.getItemName());
        addNewITemToDB(item31.getItemID(), item31.getItemType(), item31.getItemName());
        addNewITemToDB(item32.getItemID(), item32.getItemType(), item32.getItemName());
        addNewITemToDB(item33.getItemID(), item33.getItemType(), item33.getItemName());
        addNewITemToDB(item34.getItemID(), item34.getItemType(), item34.getItemName());
        addNewITemToDB(item35.getItemID(), item35.getItemType(), item35.getItemName());
        addNewITemToDB(item36.getItemID(), item36.getItemType(), item36.getItemName());
        addNewITemToDB(item37.getItemID(), item37.getItemType(), item37.getItemName());
        addNewITemToDB(item38.getItemID(), item38.getItemType(), item38.getItemName());
        addNewITemToDB(item39.getItemID(), item39.getItemType(), item39.getItemName());
        addNewITemToDB(item40.getItemID(), item40.getItemType(), item40.getItemName());
        addNewITemToDB(item41.getItemID(), item41.getItemType(), item41.getItemName());
        addNewITemToDB(item42.getItemID(), item42.getItemType(), item42.getItemName());
        addNewITemToDB(item43.getItemID(), item43.getItemType(), item43.getItemName());
        addNewITemToDB(item44.getItemID(), item44.getItemType(), item44.getItemName());
        addNewITemToDB(item45.getItemID(), item45.getItemType(), item45.getItemName());
        addNewITemToDB(item46.getItemID(), item46.getItemType(), item46.getItemName());
        addNewITemToDB(item47.getItemID(), item47.getItemType(), item47.getItemName());
        addNewITemToDB(item48.getItemID(), item48.getItemType(), item48.getItemName());
        addNewITemToDB(item49.getItemID(), item49.getItemType(), item49.getItemName());
        addNewITemToDB(item50.getItemID(), item50.getItemType(), item50.getItemName());
        addNewITemToDB(item51.getItemID(), item51.getItemType(), item51.getItemName());



//
//        GroceryItem groceryItem1 = new GroceryItem();
//        groceryItem1.setItemID(item1.getItemID());
//        groceryItem1.setItemName(item1.getItemName());
//        groceryItem1.setItemType(item1.getItemType());
//        groceryItem1.setQuantityUnit("Integer-Unit");
//        groceryItem1.setQuantityAmount(2);
//        groceryItem1.setCheckOff(false);
//
//        GroceryItem groceryItem2 = new GroceryItem();
//        groceryItem2.setItemID(item2.getItemID());
//        groceryItem2.setItemName(item2.getItemName());
//        groceryItem2.setItemType(item2.getItemType());
//        groceryItem2.setQuantityUnit("Integer-Unit");
//        groceryItem2.setQuantityAmount(2);
//        groceryItem2.setCheckOff(false);
//
//        GroceryItem groceryItem3 = new GroceryItem();
//        groceryItem3.setItemID(item3.getItemID());
//        groceryItem3.setItemName(item3.getItemName());
//        groceryItem3.setItemType(item3.getItemType());
//        groceryItem3.setQuantityUnit("Integer-Unit");
//        groceryItem3.setQuantityAmount(2);
//        groceryItem3.setCheckOff(false);
//
//
//        GroceryList groceryList1 = new GroceryList(1,"demo_list");
//        GroceryList groceryList2 = new GroceryList(2,"demo_list2");
//        groceryList1.addNewItem(groceryItem1);
//        groceryList1.addNewItem(groceryItem2);
//
//
//
//        groceryList2.addNewItem(groceryItem3);
//
//        addNewListToDB(groceryList1);
//        addNewListToDB(groceryList2);


    }

    /*
 * getting all grocery item
 * */
    public Map<String,GroceryList> getAllGroceryItemListFromDB() {
        //List<GroceryItem> groceryItems = new ArrayList<GroceryItem>();
        // This is a hashmap that has list Names as keys and the corresponding grocery lists as values
        Map<String,GroceryList> listOfGroceryLists = new HashMap<String,GroceryList>();
        Map<String,List<GroceryItem>> listOfGroceryItemsPerList = new HashMap<String,List<GroceryItem>>();
        String selectQuery = "SELECT  * FROM " + TABLE_LIST_CONTENTS;

        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = myDB.rawQuery(selectQuery, null);
        int maxListID = -1;
        GroceryListManager listManager = GroceryListManagerSingleton.getInstance();
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                GroceryItem item = new GroceryItem();
                int tempItemID = c.getInt((c.getColumnIndex(COLUMN_LISTS_CONTENTS_ITEMID)));
                System.out.println(tempItemID + " is giving me trouble!");
                if(tempItemID != -1) {

                    Item temp = listManager.getITem(tempItemID);
                    item.setItemID(temp.getItemID());
                    item.setItemName(temp.getItemName());
                    item.setItemType(temp.getItemType());
                    item.setCheckOff(convertToBolean(c.getInt(c.getColumnIndex(COLUMN_LISTS_CONTENTS_CHECKOFF))));
                    item.setQuantityAmount(c.getInt(c.getColumnIndex(COLUMN_LISTS_CONTENTS_QUANTITYAMOUNT)));
                    item.setQuantityUnit(c.getString(c.getColumnIndex(COLUMN_LISTS_CONTENTS_QUANTITYUNIT)));
                }
                // adding  grocery item
                //groceryItems.add(item);
                // This if statement populates the lists and all their groceryitems
                String tempListName = c.getString((c.getColumnIndex(COLUMN_LISTS_CONTENTS_NAME )));
                Integer tempListID =  c.getInt((c.getColumnIndex(COLUMN_LISTS_CONTENTS_ID)));
                if (listOfGroceryLists.containsKey(tempListName)){
                    if(tempItemID == -1)
                        continue;
                    listOfGroceryItemsPerList.get(tempListName).add(item);
                }
                else{
                    GroceryList groceryList = new GroceryList(tempListID, tempListName);
                    if(tempListID.intValue() >= maxListID)
                        listManager.setNextListID(tempListID + 1);
                    List<GroceryItem> tempGroceryItems = new ArrayList<GroceryItem>();
                    if(tempItemID == -1){
                        listOfGroceryLists.put(tempListName,groceryList);
                        listOfGroceryItemsPerList.put(tempListName,tempGroceryItems);
                        continue;
                    }

                    else
                        tempGroceryItems.add(item);

                    listOfGroceryLists.put(tempListName,groceryList);
                    listOfGroceryItemsPerList.put(tempListName,tempGroceryItems);
                }

            } while (c.moveToNext());


            //System.out.println(listOfGroceryItemsPerList.size() + "");
            //System.out.println(listOfGroceryLists.size());
            // This method was seen on Stackoverflow (http://stackoverflow.com/questions/46898/how-to-efficiently-iterate-over-each-entry-in-a-map)

            for (Map.Entry<String,List<GroceryItem>> mapping : listOfGroceryItemsPerList.entrySet())
            {
                if(mapping == null)
                    System.out.println("It is NULL!");
                System.out.println(mapping.getKey());
                listOfGroceryLists.get(mapping.getKey()).populateList(mapping.getValue());
            }

//
//            String[] tempLists = (String[])listOfGroceryItemsPerList.keySet().toArray();
//            for(int i = 0 ; i < tempLists.length; i++ ){
//
//            }
        }

        return listOfGroceryLists;
    }

    public boolean convertToBolean(int num){
        return num == 1 ;
    }

    public Map<Integer, Item> getAllItemFromDB() {
        Map<Integer, Item> item_lists = new HashMap<Integer, Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;

        System.out.println("I am not here");
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery(selectQuery, null);
        int maxItemID = -1;
        GroceryListManager listManager = GroceryListManagerSingleton.getInstance();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                Integer itemIdTemp = Integer.parseInt(cursor.getString(0));
                if(itemIdTemp.intValue() > maxItemID)
                    listManager.setNextItemID(itemIdTemp + 1);
                item.setItemID(itemIdTemp.intValue());
                item.setItemName(cursor.getString(2));
                item.setItemType(cursor.getString(1));
                // Adding item to list
                item_lists.put(itemIdTemp, item);
            } while (cursor.moveToNext());
        }

        // return item list
        return item_lists;

    }

    public List<String> getAllListFromDB() {
        List<String> item_lists = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  DISTINCT " + COLUMN_LISTS_CONTENTS_NAME + " FROM " + TABLE_LIST_CONTENTS;

        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding item to list
                item_lists.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // return item list
        return item_lists;

    }


    public ArrayList<Cursor> getData(String Query){
        //get writable database
        //SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = myDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }

}
