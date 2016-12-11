package edu.gatech.seclass.grocerylistmanager;

/**
 * Created by shettycm on 10/13/16.
 */
public class GroceryListManagerSingleton {
   private static GroceryListManager listManager;

    private GroceryListManagerSingleton()
    {
    }

    public static synchronized GroceryListManager getInstance()
    {
        if (listManager == null) {
            listManager = new GroceryListManager();
        }
        return listManager;
    }

}
