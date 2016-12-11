package edu.gatech.seclass.grocerylistmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.widget.EditText;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.app.Activity;

public class SearchItem extends Activity{
    private ListView listView;
    private ArrayList<String> itemsSuggestion;
    private ArrayList<Item> matchedItems;
    private String searchItem;
    private EditText searchItemName;
    private Button searchItemButton;
    private ArrayAdapter<String> arrayAdapter;
    private Button createNewItem;
    private Button addItemFromSearchButton;
    private String itemSelected;
    private GroceryListManager listManager = GroceryListManagerSingleton.getInstance();
    private GroceryList currentList = listManager.getCurrentList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        searchItemName = (EditText) findViewById(R.id.searchItemName);
        searchItemButton = (Button) findViewById(R.id.searchItemButton);
        createNewItem = (Button) findViewById(R.id.createItemButton);
        addItemFromSearchButton = (Button) findViewById(R.id.addItemFromSearch);

        addItemFromSearchButton.setEnabled(false);
        createNewItem.setEnabled(false);

        listView = (ListView) findViewById(R.id.searchListView);
        itemsSuggestion = new ArrayList<String>();

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsSuggestion);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                itemSelected = itemsSuggestion.get(position).toString().trim();

                addItemFromSearchButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(itemSelected.equals("")) {
                            Toast.makeText(SearchItem.this,"Please select item to add",Toast.LENGTH_LONG).show();
                        } else {
                            // alreadyExist variable is set to 1 if the selected item name already exist.
                            int alreadyExist = 0;
                            //Check if item is already added to the current list
                            List<GroceryItem> tempList = currentList.getGroceryItemList();
                            for(GroceryItem temp : tempList){
                                if(temp.getItemName().equals(itemSelected)){
                                    alreadyExist = 1;
                                    Toast.makeText(SearchItem.this,"Chosen item already exist in current List.",Toast.LENGTH_SHORT).show();;
                                    addItemFromSearchButton.setEnabled(true);
                                    break;
                                }
                            }

                            // If item is does not already exist in current list then add it to the current list.
                            if ((matchedItems != null) && (alreadyExist==0)) {
                                for (int i = 0; i < matchedItems.size(); i++) {
                                    if (itemSelected.toLowerCase().equals(matchedItems.get(i).getItemName().toLowerCase())) {
                                        Item item = matchedItems.get(i);
                                        currentList.addItemToList(item.getItemID(), item.getItemName(), item.getItemType(),
                                                0.0, "");
                                        Toast.makeText(SearchItem.this, "Item added to the current list", Toast.LENGTH_SHORT).show();
                                        itemSelected = "";
                                        addItemFromSearchButton.setEnabled(true);
                                    }
                                }
                            }
                        }
                    }
                });

            }

        });


        searchItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                itemsSuggestion.clear();
                searchItem = searchItemName.getText().toString();
                if(searchItem.length() > 0){
                    createListViewItems(searchItem);
                    arrayAdapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(SearchItem.this,"Please enter search item",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void createListViewItems(String searchItem) {
        matchedItems = new ArrayList<>(listManager.searchItems(searchItem));
        // If there is no match display a text view and allow user to create new item by choosing item type from
        // drop down
        if(matchedItems.size() == 0){
           Toast.makeText(SearchItem.this,"There is no match for the item name. Please click on CREATE button to create new item!",
                   Toast.LENGTH_SHORT).show();
            createNewItem.setEnabled(true);
            addItemFromSearchButton.setEnabled(false);
        }else{
            addItemFromSearchButton.setEnabled(true);
            for(int i=0; i<matchedItems.size(); i++) {
                itemsSuggestion.add(matchedItems.get(i).getItemName());
            }
       }
    }


    public void handleCreateNewItemClick(View view)
    {
        Intent i = new Intent(this,CreateNewItem.class);
        i.putExtra("search_item",searchItem);
        startActivity(i);
    }

    public void handleAddItemListClick(View view){
        Toast.makeText(this,"Please select item",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,CurrentList.class);
        startActivity(i);
    }

}
