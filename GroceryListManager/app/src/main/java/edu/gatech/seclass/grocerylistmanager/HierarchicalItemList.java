package edu.gatech.seclass.grocerylistmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class HierarchicalItemList extends BaseActivity {

    private Button saveItemButton;
    private Spinner staticSpinner, dynamicSpinner;
    private EditText quantityAmountValue;
    private EditText quantityUnitValue;

    private ArrayList<String> items;
    private String chosenItemType;
    private ArrayList<Item> itemList;

    private GroceryListManager listManager;
    private GroceryList currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hierarchical_item_list);

        staticSpinner = (Spinner) findViewById(R.id.staticSpinner);
        dynamicSpinner = (Spinner) findViewById(R.id.itemSpinner);
        quantityAmountValue = (EditText)findViewById(R.id.quantityAmountValue);
        quantityUnitValue = (EditText)findViewById(R.id.quantityUnitValue);
        saveItemButton = (Button) findViewById(R.id.saveItemToList);

        listManager = GroceryListManagerSingleton.getInstance();
        currentList = listManager.getCurrentList();

        addListenerOnStaticSpinnerItemSelection();
        addListenerOnButton();
        saveItemButton.setEnabled(false);

    }

    /**
     * Static spinner is used to display item typ list.
     */
    public void addListenerOnStaticSpinnerItemSelection() {
        staticSpinner = (Spinner) findViewById(R.id.staticSpinner);
        staticSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                addItemOnDynamicSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    /**
     * Dynamic spinner is used to displays list of items for item type.This is populated only if item type
     * is choosen.
     */
    public void addItemOnDynamicSpinner() {
        saveItemButton.setEnabled(true);
        dynamicSpinner = (Spinner) findViewById(R.id.itemSpinner);
        chosenItemType = String.valueOf(staticSpinner.getSelectedItem());

        if(chosenItemType.equals("Choose Item Type")){
            Toast.makeText(this,"Please choose Item Type",Toast.LENGTH_SHORT).show();
        }

        itemList = currentList.getAllItemForItemType(chosenItemType);

        int length = 0;

        if(itemList!=null && !itemList.isEmpty()){
            length = itemList.size();

        }
        items = new ArrayList<String>(length+1);

        items.add("Choose Item");

        for (int i = 0; i < length; i++) {
            items.add(itemList.get(i).getItemName());

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(dynamicSpinner != null) {
            dynamicSpinner.setAdapter(dataAdapter);
            dynamicSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    saveItemButton.setEnabled(true);
                    String item = String.valueOf(dynamicSpinner.getSelectedItem());
                    if(item.equals("Choose Item")){
                        Toast.makeText(HierarchicalItemList.this,"Please choose Item",Toast.LENGTH_SHORT).show();
                        saveItemButton.setEnabled(false);
                    } else{
                        //Check if item is already added to the current list
                        List<GroceryItem> tempList = currentList.getGroceryItemList();
                        for(GroceryItem temp : tempList){
                            if(temp.getItemName().equals(item)){
                                Toast.makeText(HierarchicalItemList.this,"Chosen item already exist in current List.",Toast.LENGTH_SHORT).show();;
                                saveItemButton.setEnabled(false);
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }

    public void addListenerOnButton() {

       saveItemButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int itemId =0;
                String item = String.valueOf(dynamicSpinner.getSelectedItem());

                double quantityValue;
                String quantValueString = quantityAmountValue.getText().toString().trim();
                if(quantValueString.isEmpty() || quantValueString.equals("")){
                    quantityValue = 0.0;
                }else {
                    quantityValue = Double.parseDouble(quantValueString);
                }
                String unitValue = quantityUnitValue.getText().toString();
                
                // Find itemID for the chosen item
                for(Item temp : itemList){
                    String name = temp.getItemName();
                    itemId = temp.getItemID();
                    if(temp.getItemName().equals(chosenItemType)){
                        name = temp.getItemName();
                        itemId = temp.getItemID();
                        break;
                    }
                }
                currentList.addItemToList(itemId,item,chosenItemType,quantityValue,unitValue);

                Toast.makeText(HierarchicalItemList.this,
                        "Item Saved : " +
                                 String.valueOf(dynamicSpinner.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(HierarchicalItemList.this,CurrentList.class);
                startActivity(i);
            }

        });
    }

    /**
     * This method is called when search button is clicked. This method starts search item activity.
     * @param v
     */
    public void handleSearchItemClick(View v){
        Intent i = new Intent(this,SearchItem.class);
        startActivity(i);
    }
}








