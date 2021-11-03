package com.example.lastgarageapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lastgarageapp.R;
import com.example.lastgarageapp.conversation;
import com.example.lastgarageapp.edit_garage;
import com.example.lastgarageapp.personal_page;
import com.example.lastgarageapp.view_notification;

import java.util.ArrayList;
import java.util.List;

public class showdriverAdapter extends RecyclerView.Adapter<showdriverAdapter.myViewHolder>{

    private ArrayList<String> nameText ;
    private ArrayList<String> lineworkText ;
    private Context con;

    public showdriverAdapter(Context context, ArrayList<String> nameText, ArrayList<String> lineworkText) {
        this.nameText = nameText;
        this.lineworkText = lineworkText;
        this.con = context;
    }

    @NonNull
    @Override
    public showdriverAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_drivers_list_item, parent, false);
        myViewHolder holder =new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(showdriverAdapter.myViewHolder holder, int position) {
        String name = nameText.get(position);
        holder.NameText.setText(name);

        String line = lineworkText.get(position);
        holder.LineWorkeText.setText(line);

        holder.iconMesage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(con, conversation.class);
                con.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        //how many items in my list
        return nameText.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView NameText, LineWorkeText;
        RelativeLayout show;
        TextView iconMesage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            NameText=itemView.findViewById(R.id.showDriversItem_driverName);
            LineWorkeText=itemView.findViewById(R.id.showDriversItem_driverWork);
            show=itemView.findViewById(R.id.showdriver_listitems);
            iconMesage=itemView.findViewById(R.id.showDriversItem_messageIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(con, personal_page.class);
                    con.startActivity(intent);
                }
            });

        }


        @Override
        public void onClick(View v) {

        }
    }
}