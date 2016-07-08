package com.weatherforecast;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.weatherforecast.models.City;
import com.weatherforecast.models.Notification;
import com.weatherforecast.models.Weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NotificationSetter extends Activity implements View.OnClickListener{
    private ImageView back;
    private ImageView check;
    private AutoCompleteTextView actv;
    private EditText fromDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_setter_layout);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteCity);
        String[] cities = getCities();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cities);
        actv.setAdapter(adapter);

        back = (ImageView) findViewById(R.id.back_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        check = (ImageView) findViewById(R.id.approve);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actv.getText().toString().length() == 0 || fromDateEtxt.getText().toString().length() == 0) {
                    Toast.makeText(NotificationSetter.this.getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<Weather> weathers = (ArrayList<Weather>) Weather.find(Weather.class, "type = ? and name = ?", "curr", actv.getText().toString());
                    String currTemp = "31";
                    if (weathers != null && weathers.size() > 0) {
                        currTemp = weathers.get(0).getTemp();
                    }
                    Notification notification = new Notification(actv.getText().toString(), currTemp, "5", fromDateEtxt.getText().toString());
                    notification.save();
                    Intent returnIntent = new Intent();
                    setResult(RESULT_CANCELED, returnIntent);
                    finish();
                }
            }
        });

    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        }
    }

    private String[] getCities() {
        ArrayList<String> cities = new ArrayList<>();
        ArrayList<City> c = MainActivity.getCities();
        for (City city : c) {
            cities.add(city.getName());
        }
        String[] res = new String[cities.size()];
        return cities.toArray(res);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }
}
