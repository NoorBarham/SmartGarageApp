package com.example.lastgarageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lastgarageapp.adapter.messageAdapter;
import com.example.lastgarageapp.adapter.receiverAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class conversation extends AppCompatActivity {

    private ArrayList<String> MessagesTexts = new ArrayList<>();
    private ArrayList<String> HoureTexts = new ArrayList<>();

    ImageView conversation_send_icon;
    TextView conversation_addText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        conversation_addText=findViewById(R.id.conversation_addText);
        conversation_send_icon = findViewById(R.id.conversation_send_icon);
        conversation_send_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final StringBuilder sb = new StringBuilder(conversation_addText.getText().length());
                sb.append(conversation_addText.getText());
                String s= sb.toString();
                MessagesTexts.add(s);

                TimeZone.setDefault(TimeZone.getTimeZone("GMT" + "02:00"));
                Date currentTime = Calendar.getInstance().getTime();
                String timeStamp = new SimpleDateFormat("HH:mm").format(currentTime);
                HoureTexts.add(timeStamp);

                messageAdapter adapter;

                RecyclerView myRecyclerView = findViewById(R.id.converRecyView);
                myRecyclerView.setLayoutManager(new LinearLayoutManager(conversation.this));
                adapter = new messageAdapter(conversation.this, MessagesTexts,HoureTexts);
                myRecyclerView.setAdapter(adapter);

                conversation_addText.setText("");
            }
        });
        MessagesTexts.add("Horse");
        HoureTexts.add("12:00");
        receiverAdapter adapter;

        RecyclerView myRecyclerView = findViewById(R.id.converRecyView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(conversation.this));
        adapter = new receiverAdapter(conversation.this,MessagesTexts,HoureTexts);
        myRecyclerView.setAdapter(adapter);

    }


}