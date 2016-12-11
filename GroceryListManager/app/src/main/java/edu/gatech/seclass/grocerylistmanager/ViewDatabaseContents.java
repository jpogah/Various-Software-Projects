package edu.gatech.seclass.grocerylistmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewDatabaseContents extends AppCompatActivity {

    public DBInterface DBConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database_contents);
        DBConn = new DBInterface(this);
        DBConn.dummyInitialize();

        Button button =(Button)findViewById(R.id.view_db);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(ViewDatabaseContents.this, AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

    }


}
