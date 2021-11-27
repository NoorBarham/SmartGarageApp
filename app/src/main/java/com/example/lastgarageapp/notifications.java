package com.example.lastgarageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lastgarageapp.adapter.newsAdapter;
import com.example.lastgarageapp.adapter.notificationAdapter;
import com.example.lastgarageapp.itemClasses.newsItem;
import com.example.lastgarageapp.itemClasses.notificationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class notifications extends AppCompatActivity {

    ImageView homeIcon,notificationIcon,personalIcon,messagesIcon,menuIcon;
    RecyclerView myRecyclerView;

    ArrayList<notificationItem> myNotification = new ArrayList<>();
    notificationAdapter myNotificationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //views in my actionbarPage
        homeIcon = findViewById(R.id.myActionBar_homeIcon);
        notificationIcon = findViewById(R.id.myActionBar_notificationsIcon);
        personalIcon = findViewById(R.id.myActionBar_personIcon);
        messagesIcon = findViewById(R.id.myActionBar_messagesIcon);
        menuIcon = findViewById(R.id.myActionBar_menuIcon);

        myRecyclerView = findViewById(R.id.notoRecyView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(notifications.this));

        String url = url_serverName.serverName + "showNotifications.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("notification");
                    notificationItem myItem;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject reader = jsonArray.getJSONObject(i);

                        //String textName, String textNews, String textHour
                        String name = reader.getString("name");
                        String time = reader.getString("time");
                        myItem = new notificationItem(name, time);

                        myNotification.add(myItem);
                    }
                    myNotificationAdapter = new notificationAdapter(notifications.this, myNotification);
                    myRecyclerView.setAdapter(myNotificationAdapter);
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
        my_singleton.getInstance(notifications.this).addToRequestQueue(myStringRequest);

        notificationIcon.setBackgroundColor(Color.WHITE);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notifications.this, home_page.class);
                startActivity(intent);
            }
        });
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notifications.this, notifications.class);
                startActivity(intent);
            }
        });
        personalIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notifications.this, personal_page.class);
                startActivity(intent);
            }
        });
        messagesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notifications.this, messages.class);
                startActivity(intent);
            }
        });

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notifications.this, menu.class);
                startActivity(intent);
            }
        });
    }
/// recycleview

        public void shoowNotification () {

            String url = url_serverName.serverName + "showNotifications.php";
            StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray("notification");
                        notificationItem myItem;
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject reader = jsonArray.getJSONObject(i);

                            //String textName, String textNews, String textHour
                            String name = reader.getString("name");
                            String time = reader.getString("time");
                            myItem = new notificationItem(name, time);

                            myNotification.add(myItem);
                        }
                        myNotificationAdapter = new notificationAdapter(notifications.this, myNotification);
                        myRecyclerView.setAdapter(myNotificationAdapter);
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
            my_singleton.getInstance(notifications.this).addToRequestQueue(myStringRequest);
        }
    }


// عند اضافة خبر يتم عمل اشعار