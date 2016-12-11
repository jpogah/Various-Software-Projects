package edu.gatech.seclass.grocerylistmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;


public class CreateNewItem extends BaseActivity {

    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private TextView searchItemView;
    private String chosenItemType;
    private String searchItem;
    private GroceryListManager listManager;
    private GroceryList currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_item);
        spinner = (Spinner)findViewById(R.id.itemTypeChoose);
        searchItemView = (TextView)findViewById(R.id.searchItem);
        listManager = GroceryListManagerSingleton.getInstance();
        currentList = listManager.getCurrentList();

        Bundle extras = getIntent().getExtras();
        searchItem = "Something went wrong! click BACK button!";
        if (extras != null) {
            searchItem = extras.getString("search_item");
        }
        searchItemView.setText(searchItem);
        adapter = ArrayAdapter.createFromResource(this,R.array.itemTypes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenItemType = String.valueOf(spinner.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void handleAddToDBClick(View view){
        if(chosenItemType.equals("Choose Item Type")){
            Toast.makeText(CreateNewItem.this,"Please choose Item Type",Toast.LENGTH_SHORT).show();
        } else {
            int itemID = listManager.getNextItemID();
            listManager.setNextItemID(itemID + 1);
            GroceryItem item = new GroceryItem(itemID, searchItem, chosenItemType, 0.0, "", false);
            currentList.addNewItem(item);
            Toast.makeText(this, "Item is created", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, CurrentList.class);
            startActivity(i);
        }
    }

}
