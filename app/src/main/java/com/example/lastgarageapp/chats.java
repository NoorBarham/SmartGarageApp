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
import com.example.lastgarageapp.adapter.chatAdapter;
import com.example.lastgarageapp.itemClasses.chatItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chats extends AppCompatActivity {

    ImageView homeIcon,notificationIcon,personalIcon,messagesIcon,menuIcon;
    ArrayList<chatItem> myMessages = new ArrayList<>();
    chatAdapter myMessageAdapter;
    RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        //views in my actionbarPage
        homeIcon=findViewById(R.id.myActionBar_homeIcon);
        notificationIcon=findViewById(R.id.myActionBar_notificationsIcon);
        personalIcon=findViewById(R.id.myActionBar_personIcon);
        messagesIcon=findViewById(R.id.myActionBar_messagesIcon);
        menuIcon=findViewById(R.id.myActionBar_menuIcon);
        myRecyclerView=findViewById(R.id.message_recyview);

        messagesIcon.setBackgroundColor(Color.WHITE);
        selectChats();

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(chats.this ,home_page.class);
                startActivity(intent);
            }
        });
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(chats.this ,notifications.class);
                startActivity(intent);
            }
        });
        personalIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(chats.this ,personal_page.class);
                startActivity(intent);
            }
        });
        messagesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(chats.this , chats.class);
                startActivity(intent);
            }
        });///// its okkkkkkkkk hhhhhhhhhhhhhhhh
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(chats.this ,menu.class);
                startActivity(intent);
            }
        });
        //recyclerView
        chatItem m=new chatItem("waed","hi","10:30");
        ArrayList<chatItem> messangerArray= new ArrayList<>();
        messangerArray.add(m);
        messangerArray.add(m);
        chatAdapter m_adapter;


        RecyclerView messengerRecyclerView = findViewById(R.id.message_recyview);
        messengerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        m_adapter = new chatAdapter(this, messangerArray);
        messengerRecyclerView.setAdapter(m_adapter);
    }

    public void selectChats() {
        String url = url_serverName.serverName + "showChats.php";
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("chats");
                    chatItem myItem;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject reader = jsonArray.getJSONObject(i);

                        String user_name = reader.getString("user_name");
                        String message_text = reader.getString("message_text");
                        if(message_text.length()>16){
                            message_text=message_text.substring(0,15)+".....";
                        }
                        String hour = reader.getString("hour");
                        myItem = new chatItem(user_name,message_text, hour);

                        myMessages.add(myItem);
                    }
                    myMessageAdapter = new chatAdapter(chats.this, myMessages);
                    myRecyclerView.setAdapter(myMessageAdapter);
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
        my_singleton.getInstance(chats.this).addToRequestQueue(myStringRequest);
    }
}