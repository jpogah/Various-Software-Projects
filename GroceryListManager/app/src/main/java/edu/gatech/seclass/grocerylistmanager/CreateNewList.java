package edu.gatech.seclass.grocerylistmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateNewList extends BaseActivity {

    private EditText newListName;
    private DBInterface dbConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //dbConn = new DBInterface(this);
        GroceryListManager listManager = GroceryListManagerSingleton.getInstance();

        setContentView(R.layout.activity_create_new_list);
        newListName = (EditText)findViewById(R.id.newListName);
        ListView myListView = (ListView)findViewById(R.id.myListView);

        final List<String> listCollection = new ArrayList<String>();


        for (Map.Entry<String,GroceryList> mapping : listManager.getAllLists().entrySet())
        {
            listCollection.add(mapping.getKey());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listCollection);
        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                //Toast.makeText(getApplicationContext(), "You selected " + listCollection.get(i), Toast.LENGTH_SHORT).show();
                GroceryListManager listManager = GroceryListManagerSingleton.getInstance();

                listManager.setCurrentList( listCollection.get(i));
                Intent go = new Intent(CreateNewList.this, CurrentList.class);
                startActivity(go);
            }
        }


        );

       myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getApplicationContext(), "You selected " + listCollection.get(i), Toast.LENGTH_SHORT).show();
                Intent go = new Intent(CreateNewList.this, edit_delete_List.class);
                go.putExtra("List_Name",listCollection.get(i));
                startActivity(go);


                return true;
            }
        });

    }

    public void handleAddNewListClick(View view) {

        String newList = newListName.getText().toString().trim();
        GroceryListManager listManager = GroceryListManagerSingleton.getInstance();

        Intent i = new Intent(CreateNewList.this,CurrentList.class);

        if (newList == null || newList.isEmpty()){
            Toast.makeText(this,"List must not be empty", Toast.LENGTH_SHORT).show();
        }else if(listManager.createList(newList) == false) {
            Toast.makeText(this,"List name already exist. Please provide new name", Toast.LENGTH_SHORT).show();
        }else{startActivity(i);}


    }

    @Override
    public void onBackPressed() {

    }
}
