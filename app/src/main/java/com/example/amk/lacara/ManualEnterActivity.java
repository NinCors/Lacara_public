package com.example.amk.lacara;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import android.widget.DatePicker;
import android.util.Log;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ManualEnterActivity extends AppCompatActivity {

    EditText item;
    EditText location;
    EditText price;
    EditText date;

    TextView areaDisplay;
    MyDBHandler dbHandler;
    //String[] itemsTest = AddPrefUtility.getItemList(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manualenter);
        Toolbar ManualToolbar = (Toolbar) findViewById(R.id.ManualTB);
        setSupportActionBar(ManualToolbar);
        dbHandler = new MyDBHandler(this, null, null, 1);
        CatSpinner();

        item = (EditText) findViewById(R.id.itemName);
        location = (EditText) findViewById(R.id.locationInput);
        price = (EditText) findViewById(R.id.priceInput);
        areaDisplay = (TextView) findViewById(R.id.areaDisplay);


        printDatabase();
    }

//save entered text to button saveInfo
    public void saveInfo (View view){
        DatePicker mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        Spinner dropdown = (Spinner) findViewById(R.id.categories);
        String temp = price.getText().toString();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();
        String date = "";
        month++;
        if(month < 10)
        {
            date += "0";
        }

        date += month + "-";

        if(day < 10)
        {
            date += "0";
        }

        date += mDatePicker.getDayOfMonth() + "-" + mDatePicker.getYear();

        boolean stop = false;
        if(item.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Item Empty!", Toast.LENGTH_LONG).show();
            stop = true;
        }

        if(location.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Location Empty!", Toast.LENGTH_LONG).show();
            stop = true;
        }
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(price.toString());

        if(price.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Price Empty!", Toast.LENGTH_LONG).show();
            stop = true;
        }

       /* if(!m.matches())
        {
            Toast.makeText(getApplicationContext(), "Invalid Price Entry!", Toast.LENGTH_LONG).show();
            price.setText("");
            stop = true;
        }*/


        if(stop)
        {
            return;
        }
        Log.d("Day", date);
        Data recipe = new Data(item.getText().toString().toString(),location.getText().toString().toString(),Double.parseDouble(temp), date, dropdown.getSelectedItem().toString());
        dbHandler.addRecipe(recipe);
        printDatabase();
        showAlerts();
    }


    public void showAlerts(){
        AlertDialog.Builder alert = new AlertDialog.Builder(ManualEnterActivity.this);
        alert.setMessage("Warning ! you spend too much this month!");
        alert.setCancelable(false);

        alert.setPositiveButton(
                "Understand",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        alert.setNegativeButton(
                "Don't Care",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = alert.create();


    }

        //create spinner for item categories
        public void CatSpinner()
        {
            Spinner dropdown = (Spinner) findViewById(R.id.categories);
            List<String> list = new ArrayList<String>();
            list.add("Food");
            list.add("Utilities");
            list.add("Personal");
            list.add("Activities");
            list.add("Auto");
            list.add("Home");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
            dropdown.setAdapter(adapter);
        }

        public void DatePickerInt()
        {
            DatePicker mDatePicker = (DatePicker) findViewById(R.id.datePicker);
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            mDatePicker.init(year, month, day, null);
            //Log.d("test", Integer.toString(mDatePicker.getDayOfMonth()));
        }

        //button to test to display saved data
        public void displayInfo (View view){
            /*
            SharedPreferences sharedPref = getSharedPreferences ("itemInfo", Context.MODE_PRIVATE);

            String iname = sharedPref.getString("itemName", "");
            String lname = sharedPref.getString("locName", "");
            String pname = sharedPref.getString("priceName", "");
            areaDisplay.setText(iname + " " + lname + " " + pname);


            //I'm trying random stuff right here lol
            String itemTest2 = itemsTest.toString();
            areaDisplay.setText(itemTest2);
            */

    }




    public void printDatabase()
    {
        //String dbString = dbHandler.databaseToString();
        double test = dbHandler.getMonthTotal("05", "2017");
        String dbString =  "" + test;
        areaDisplay.setText(dbString);
        item.setText("");
        location.setText("");
        price.setText("");
      /*  Log.d("Food", Double.toString(dbHandler.getMonthCat("04", "2017", "Food")));
        Log.d("Utiities", Double.toString(dbHandler.getMonthCat("04", "2017", "Utilities")));
        Log.d("Personal", Double.toString(dbHandler.getMonthCat("04", "2017", "Personal")));
        Log.d("Activities", Double.toString(dbHandler.getMonthCat("04", "2017", "Activities")));
        Log.d("Auto", Double.toString(dbHandler.getMonthCat("04", "2017", "Auto")));
        Log.d("Home", Double.toString(dbHandler.getMonthCat("04", "2017", "Home")));*/
        //date.setText("");
    }

}



