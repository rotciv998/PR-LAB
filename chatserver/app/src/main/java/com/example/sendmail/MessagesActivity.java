package com.example.sendmail;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    List<MessageModel> messageModelList;
    MessagesAdapter messagesAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_layout);

        messageModelList = (List<MessageModel>) getIntent().getSerializableExtra("msg");
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa   " + messageModelList.toString());
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesAdapter = new MessagesAdapter(this, messageModelList);
        recyclerView.setAdapter(messagesAdapter);
    }
}
