package edu.gatech.seclass.grocerylistmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity {

    private GroceryListManager listManager;
    private static DBInterface DBConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.deleteDatabase("groceryListManagerDB.db");
        DBConn = new DBInterface(this);
        GroceryListManager listManager = GroceryListManagerSingleton.getInstance();

        listManager.DBConn = DBConn;
        listManager.initialize();
        listManager.DBConn.dummyInitialize();


        // Get all the list from GroceryListManager and check the length
        // If it is 0 means no grocery list for the user and user has to create new list
        List<String> listCollection = DBConn.getAllListFromDB();

        Intent i = new Intent(this,CreateNewList.class);
        if( listCollection.size() == 0 ) {
            setContentView(R.layout.activity_no_list);
        } else {

            startActivity(i);
            //setContentView(R.layout.activity_create_new_list);
        }

    }

    public void onTerminate() {
        DBConn.close();
        DBConn = null;
        listManager = null;
    }

    public void handleCreateNewListClick(View view) {
        Intent i = new Intent(this,CreateNewList.class);
        startActivity(i);
    }
}
