package edu.gatech.seclass.grocerylistmanager;

import edu.gatech.seclass.grocerylistmanager.ExpandableListAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.graphics.Color;

public class CurrentList extends BaseActivity{
    private TextView currentListName;
    private GroceryListManager groceryList;
    private GroceryList currentList;
    private List<GroceryItem> currentItems;
    private Button deleteButton;
    private Button editButton;
    private Button switchButton;
    private CheckBox checkOffAllButton;
    private LinearLayout layoutHeader;
    private ExpandableListAdapter expListAdapter;

    private List<String> groupList;
    private Map<String, List<GroceryItem>> itemCollection;
    private ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_list);

        checkOffAllButton = (CheckBox) findViewById(R.id.checkOffAll);
        switchButton = (Button)findViewById(R.id.switchList);
        deleteButton = (Button) findViewById(R.id.deleteItem);
        editButton = (Button) findViewById(R.id.editItem);
        groceryList = GroceryListManagerSingleton.getInstance();
        currentListName = (TextView)findViewById(R.id.currentListName);
        layoutHeader = (LinearLayout) findViewById(R.id.groceryItemListHeader);
        currentList = groceryList.getCurrentList();

        currentListName.setText(currentList.getListName());

        currentItems = currentList.getGroceryItemList();

        createCollection();

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(CurrentList.this, CreateNewList.class);
                startActivity(go);
            }
        });

        expListView = (ExpandableListView) findViewById(R.id.itemType_list);
        if((groupList!=null || (!groupList.isEmpty())) && (itemCollection!=null || (!itemCollection.isEmpty()))) {

            expListAdapter = new ExpandableListAdapter(
                    this, groupList, itemCollection);
            expListView.setAdapter(expListAdapter);

            if(itemCollection.size() == 0){
                layoutHeader.setVisibility(View.INVISIBLE);
            }

            //To expand the view at first visit
            for(int i=0; i < expListAdapter.getGroupCount(); i++)
                expListView.expandGroup(i);

            setGroupIndicatorToRight();

            expListView.setOnChildClickListener(new OnChildClickListener() {
                View lastColored;
                int grposition;
                int chposition;

                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    grposition = groupPosition;
                    chposition = childPosition;

                    // to change the background color of child view clicked
                    if(lastColored != null)
                    {
                        lastColored.setBackgroundColor(Color.TRANSPARENT);
                        lastColored.invalidate();
                    }
                    lastColored = v;
                    v.setBackgroundColor(Color.rgb(214, 214, 214));

                    final GroceryItem selected = (GroceryItem) expListAdapter.getChild(
                            grposition, chposition);

                    // This is to handle delete button click when any child view is selected
                    deleteButton.setOnClickListener(new Button.OnClickListener() {
                        public void onClick(View v) {
                            if (selected == null || chposition < 0) {
                                Toast.makeText(CurrentList.this, "Please click a Grocery Item to be deleted and then click DELETE.", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CurrentList.this);
                                builder.setMessage("Do you want to remove?");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                List<GroceryItem> child =
                                                        itemCollection.get(groupList.get(grposition));
                                                GroceryItem childItem = child.get(chposition);
                                                child.remove(chposition);
                                                //check if there is no items left for that item type. if yes remove that type
                                                // from display view
                                                if (child.isEmpty()) {
                                                    for (Iterator<Map.Entry<String, List<GroceryItem>>> it = itemCollection.entrySet().iterator(); it.hasNext(); ) {
                                                        Map.Entry<String, List<GroceryItem>> entry = it.next();
                                                        if (entry.getKey().equals(groupList.get(grposition))) {
                                                            it.remove();
                                                        }
                                                    }
                                                    groupList.remove(grposition);
                                                }
                                                if(itemCollection.isEmpty()){
                                                    layoutHeader.setVisibility(View.INVISIBLE);
                                                }else {
                                                    layoutHeader.setVisibility(View.VISIBLE);
                                                }

                                                currentList.deleteItemFromList(childItem.getItemID());
                                                grposition = -1;
                                                chposition = -1;
                                                expListAdapter.notifyDataSetChanged();
                                            }
                                        });
                                builder.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }
                    });

                    // This is to handle edit button click when any child view is selected
                    editButton.setOnClickListener(new Button.OnClickListener() {
                        public void onClick(View v) {
                            if (selected == null || chposition < 0) {
                                Toast.makeText(CurrentList.this, "Please click a Grocery Item to be deleted and then click DELETE.", Toast.LENGTH_SHORT).show();
                            } else {

                                List<GroceryItem> child =
                                        itemCollection.get(groupList.get(grposition));
                                GroceryItem childItem = child.get(chposition);

                                String itemID = String.valueOf(childItem.getItemID());
                                String itemName = childItem.getItemName();
                                String itemQuant = String.valueOf(childItem.getQuantityAmount());
                                String itemUnit = childItem.getQuantityUnit();

                                Intent i = new Intent(CurrentList.this,ChangeQuantity.class);
                                i.putExtra("GroceryItemID",itemID);
                                i.putExtra("GroceryItem", itemName);
                                i.putExtra("GroceryItemQuant", itemQuant);
                                i.putExtra("GroceryItemUnit", itemUnit);
                                startActivity(i);

                            }
                        }
                    });

                    //This is handle all check off grocery items.
                    checkOffAllButton.setOnClickListener(new Button.OnClickListener() {
                        public void onClick(View v) {
                            if (checkOffAllButton.isChecked()) {
                                currentList.checkOffAllItems(true);
                            } else {
                                currentList.checkOffAllItems(false);
                            }
                            createCollection();
                            for(int i=0; i < expListAdapter.getGroupCount(); i++)
                                expListView.expandGroup(i);

                            expListAdapter.notifyDataSetChanged();
                        }
                    });

                    return true;
                }
            });

        }

    }

    public void handleAddItemClick(View view) {
        Intent i = new Intent(this,HierarchicalItemList.class);
        startActivity(i);

    }

    /**
     * This method is invoked when delete button is clicked without selecting grocery item
     * to be deleted. When this method is invoked , throw a message that please click an item
     * to be deleted, then click delete.
     * @param v
     */
    public void handleDeleteItemClick(View v) {
        Toast.makeText(this,"Please click a Grocery Item to be deleted and then click DELETE.",Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is invoked when edit button is clicked without selecting grocery item
     * to be edited. When this method is invoked , throw a message that please click an item
     * to be edited, then click edit.
     * @param v
     */
    public void handleEditItemClick(View v) {
        Toast.makeText(this,"Please click a Grocery Item to be edited and then click EDIT.",Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is invoked when Checkoff All check box is clicked. When this method is invoked ,
     * it sets check off field for all the  grocery items based on check box value.
     * in the list.
     * @param v
     */
    public void handleCheckOffAllClick(View v) {
        if (checkOffAllButton.isChecked()) {
            currentList.checkOffAllItems(true);
        } else {
            currentList.checkOffAllItems(false);
        }

        expListAdapter.notifyDataSetChanged();
    }

    /**
     * this method creates item type list and collection of grocery items for each item type
     */
    private void createCollection() {
        // Get updated grocery items before creating data for expandable view as this method can called from
        // other places than onCreate().
        currentItems = currentList.getGroceryItemList();
        groupList = new ArrayList<String>();
        itemCollection = new LinkedHashMap<String, List<GroceryItem>>();
        for(GroceryItem temp : currentItems){
            List<GroceryItem> tempList = itemCollection.get(temp.getItemType());
            if (tempList == null) {
                groupList.add(temp.getItemType());
                tempList = new ArrayList<>();
                itemCollection.put(temp.getItemType(), tempList);
            }
            tempList.add(temp);
        }
    }


    private void setGroupIndicatorToRight() {
		/* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(100), width
                - getDipsFromPixel(10));

    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,CreateNewList.class);
        startActivity(i);
    }
}