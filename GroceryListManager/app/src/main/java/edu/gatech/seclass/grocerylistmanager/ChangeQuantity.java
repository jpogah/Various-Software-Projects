package edu.gatech.seclass.grocerylistmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class ChangeQuantity extends BaseActivity {
    private TextView itemName;
    private EditText itemQuantity;
    private EditText itemQuantityUnit;
    private GroceryListManager groceryList;
    private GroceryList currentList;
    private int itemID;
    private String item;
    private Double itemQuant;
    private String itemUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_quantity);

        itemName = (TextView)findViewById(R.id.changeItem);
        itemQuantity = (EditText)findViewById(R.id.changeQuantity);
        itemQuantityUnit = (EditText)findViewById(R.id.changeQuantityUnit);

        itemID = Integer.parseInt(getIntent().getStringExtra("GroceryItemID"));
        item = getIntent().getStringExtra("GroceryItem");
        itemQuant = Double.parseDouble(getIntent().getStringExtra("GroceryItemQuant"));
        itemUnit = getIntent().getStringExtra("GroceryItemUnit");

        itemName.setText(item);
    }

    /**
     * This method is called when Save button is clicked.
     * @param view
     */
    public void handleChangeSave(View view){
        groceryList = GroceryListManagerSingleton.getInstance();
        currentList = groceryList.getCurrentList();
        String quant = itemQuantity.getText().toString();
        if(quant.length() > 0){
            itemQuant = Double.parseDouble(quant);
            itemUnit = itemQuantityUnit.getText().toString();
            currentList.changeQuantity(itemID,itemQuant,itemUnit);
            Intent i = new Intent(this,CurrentList.class);
            startActivity(i);
        }else{
            Toast.makeText(this,"Please provide quantity",Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method is called when Cancel button is clicked.
     * @param view
     */
    public void handleChangeCancel(View view){
        Intent i = new Intent(this,CurrentList.class);
        startActivity(i);
    }
}
