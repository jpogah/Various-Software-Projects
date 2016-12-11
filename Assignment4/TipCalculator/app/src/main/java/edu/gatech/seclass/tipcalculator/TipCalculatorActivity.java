package edu.gatech.seclass.tipcalculator;

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TipCalculatorActivity extends AppCompatActivity {
    public final static double fifteenPercent = 0.15;
    public final static double twentyPercent = 0.20;
    public final static double twentyFivePercent = 0.25;
    public final static  String errorMsg = "Empty or incorrect value(s)!";
    public final static int duration = Toast.LENGTH_SHORT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void calculateTip(View view){
        EditText editText1 = (EditText)findViewById(R.id.checkAmountValue);
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        EditText editText2 = (EditText)findViewById(R.id.partySizeValue);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        NumberFormat formatter = new DecimalFormat("#0");
        double checkAmount ;
        double amountPerPerson;
        int partySize;
        try{
            checkAmount = Double.parseDouble(editText1.getText().toString());
            partySize = Integer.parseInt(editText2.getText().toString());
            if(checkAmount <= 0 || partySize <= 0){
                throw new Exception("Invalid input");
            }
            amountPerPerson = checkAmount / partySize ;
            double tip1 = amountPerPerson * fifteenPercent;
            double tip2 = amountPerPerson * twentyPercent;
            double tip3 = amountPerPerson * twentyFivePercent;
            double total1 = Math.round(tip1 + amountPerPerson);
            double total2 = Math.round(tip2 + amountPerPerson);
            double total3 = Math.round(tip3 + amountPerPerson);
            TextView tv1 = (TextView)findViewById(R.id.fifteenPercentTipValue);
            tv1.setText(String.valueOf(Math.round(tip1)));
            TextView tv2 = (TextView)findViewById(R.id.twentyPercentTipValue);
            tv2.setText(String.valueOf(Math.round(tip2)));
            TextView tv3 = (TextView)findViewById(R.id.twentyFivePercentTipValue);
            tv3.setText(String.valueOf(Math.round(tip3)));
            TextView tv4 = (TextView)findViewById(R.id.fifteenPercentTotalValue);
            tv4.setText(String.valueOf(formatter.format(total1)));
            TextView tv5 = (TextView)findViewById(R.id.twentyPercentTotalValue);
            tv5.setText(String.valueOf(formatter.format(total2)));
            TextView tv6 = (TextView)findViewById(R.id.twentyFivePercentTotalValue);
            tv6.setText(String.valueOf(formatter.format(total3)));



        }catch(Exception e){
            Toast toast = Toast.makeText(this, errorMsg, duration);
            toast.show();

        }


    }
}
