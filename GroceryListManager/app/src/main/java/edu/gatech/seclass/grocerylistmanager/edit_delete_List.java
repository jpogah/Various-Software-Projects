package edu.gatech.seclass.grocerylistmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.R.id.message;
import static edu.gatech.seclass.grocerylistmanager.R.styleable.View;

public class edit_delete_List extends AppCompatActivity {

    //GroceryListManager gListManager = new GroceryListManager();
    GroceryListManager listManager = GroceryListManagerSingleton.getInstance();


    EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete__list);



        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("List_Name");
        Button btnRename = (Button)findViewById(R.id.rename);
        Button btnDelete = (Button)findViewById(R.id.delete);


        editText1 = (EditText)findViewById(R.id.editListName);
        editText1.setText(message);


        btnRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String newListName = editText1.getText().toString().trim();


                if (newListName == null || newListName.isEmpty()){
                    Toast.makeText(getApplicationContext(),"List name must not be empty", Toast.LENGTH_SHORT).show();
                }else if(listManager.createList(newListName) == false) {
                    Toast.makeText(getApplicationContext(),"List name already exist. Please provide new name", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "old: " + message + "\nnew: " + newListName, Toast.LENGTH_SHORT).show();
                    listManager.renameList(message.toString(),newListName);
                    Intent go = new Intent(edit_delete_List.this,CreateNewList.class);
                    startActivity(go);
                }

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "delete list: " + message, Toast.LENGTH_SHORT).show();

                listManager.deleteList(message.toString());
                Intent go = new Intent(edit_delete_List.this,CreateNewList.class);
                startActivity(go);

            }
        });


        Button button = (Button)findViewById(R.id.btnCancel);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(edit_delete_List.this, CreateNewList.class);
                startActivity(go);
            }
        });

    }

}

