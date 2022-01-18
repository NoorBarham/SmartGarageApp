package com.example.lastgarageapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class edit_garage extends AppCompatActivity {

    private EditText openHour, closeHour, Location;
    private Spinner garageName, adminName;
    private Button cancel, saveChange;

    private TimePickerDialog timePickerDialog;
    String amPm;
    ArrayList garageName_arr =new ArrayList();
    ArrayList adminName_arr =new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_garage);

        garageName =findViewById(R.id.editGarage_garageName);
        adminName =findViewById(R.id.editGarage_newAdminName);
        openHour = findViewById(R.id.editGarage_newOpenHour);
        closeHour = findViewById(R.id.editGarage_newCloseHour);
        Location =findViewById(R.id.editGarage_newLocation);
        saveChange = (Button) findViewById(R.id.editGarage_saveChange);
        cancel = (Button) findViewById(R.id.editGarage_cancel);

        //if boss
        garageNameSpinner();
        adminNameSpinner();

        //if normal Admin
        garage_and_admin();

        garageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();

                if (!selectedItem.equals("لم يحدد")) {
                    selectGarageData(selectedItem);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        openHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(edit_garage.this,R.style.Theme1_LastGarageApp, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        openHour.setText(String.format("%02d : %02d " , hourOfDay, minute));
                    }
                },00, 00, true );
                timePickerDialog.show();

            }
        });

        closeHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(edit_garage.this,R.style.Theme1_LastGarageApp, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        closeHour.setText(String.format("%02d : %02d " , hourOfDay, minute));
                    }
                }, 00 ,00,true);
                timePickerDialog.show();
            } });
        saveChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(edit_garage.this);
                alert.setTitle("حفظ التغيرات");
                alert.setMessage("هل تريد حفظ البيانات الجديدة؟");
                alert.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                updateData();
            }
        });
                alert.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
finish();
                       // Toast.makeText(edit_garage.this, "تم التراجع",Toast.LENGTH_SHORT).show();
                    }
                });alert.create().show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updateData() {
        String url = url_serverName.serverName+"editGarage.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> myMap = new HashMap<>();

                myMap.put("garage_name", garageName.getSelectedItem().toString());
                myMap.put("open_hour", removeSpaces(getData(openHour)));
                myMap.put("close_hour", removeSpaces(getData(closeHour)));
                myMap.put("location", getData(Location));
                myMap.put("admin_name", adminName.getSelectedItem().toString());
                return myMap;
            }
        };
        my_singleton.getInstance(edit_garage.this).addToRequestQueue(myStringRequest);
    }

    private void selectGarageData(String garageName) {
        String url = url_serverName.serverName + "selectGarageData.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("garages");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        String adminName = reader.getString("admin_name");
                        String fromHoure = reader.getString("open_h");
                        String toHoure = reader.getString("close_h");
                        String location = reader.getString("location");

                        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_garage.this,android.R.layout.simple_spinner_item, adminName_arr);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        edit_garage.this.adminName.setAdapter(adapter);
                        edit_garage.this.adminName.setSelection(adapter.getPosition(adminName));

                        openHour.setText(fromHoure);
                        closeHour.setText(toHoure);
                        Location.setText(location);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> myMap = new HashMap<>();
                myMap.put("garage_name", garageName);
                return myMap;
            }
        };
        my_singleton.getInstance(edit_garage.this).addToRequestQueue(myStringRequest);
    }

    private void garageNameSpinner() {
        //if boss
        garageName_arr.clear();
        garageName_arr.add(0,"لم يحدد");

        String url = url_serverName.serverName + "sourceSpinner.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("garages");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String cityName
                        String cityName = reader.getString("garage_name");
                        garageName_arr.add(cityName);

                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_garage.this,android.R.layout.simple_spinner_item, garageName_arr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    garageName.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        my_singleton.getInstance(edit_garage.this).addToRequestQueue(myStringRequest);
    }

    private void garage_and_admin() {
        String url = url_serverName.serverName + "selectGarageNameByAdmin.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("deleteGarageLine");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);
                        String garage_name = reader.getString("garage_name");
                        garageName_arr.add(garage_name);
                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_garage.this,android.R.layout.simple_spinner_item, garageName_arr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    garageName.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> myMap = new HashMap<>();
                myMap.put("s_id", login.s_id);
                return myMap;
            }
        };
        my_singleton.getInstance(edit_garage.this).addToRequestQueue(myStringRequest);
    }

    private void adminNameSpinner() {
        adminName_arr.clear();
        adminName_arr.add(0,"لم يحدد");

        String url = url_serverName.serverName + "adminSpinner.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("admins");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String cityName
                        String admin_name = reader.getString("admin_name");
                        adminName_arr.add(admin_name);

                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(edit_garage.this,android.R.layout.simple_spinner_item, adminName_arr);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adminName.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        my_singleton.getInstance(edit_garage.this).addToRequestQueue(myStringRequest);
    }
    public String getData(EditText t){
        String myString =t.getText().toString();
        return myString;
    }

    static String removeSpaces(String myString) {
        char []str=myString.toCharArray();
        int count = 0;
        for (int i = 0; i<str.length; i++)
            if (str[i] != ' ')
                str[count++] = str[i];
        return (String) String.valueOf(str).subSequence(0, count);
    }
}
