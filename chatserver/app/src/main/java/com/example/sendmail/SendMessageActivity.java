package com.example.sendmail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;

import javax.mail.MessagingException;

public class SendMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message_formular);
        final AppCompatButton send = this.findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String fromEmail = ((TextInputEditText) findViewById(R.id.from))
                        .getText().toString();

                String fromPassword = ((TextInputEditText) findViewById(R.id.password))
                        .getText().toString();

                String toEmail = ((TextInputEditText) findViewById(R.id.to))
                        .getText().toString();

                String emailSubject = ((TextInputEditText) findViewById(R.id.subject))
                        .getText().toString();
                String emailBody = ((TextInputEditText) findViewById(R.id.message))
                        .getText().toString();

                new SendEmailAsyncTask(SendMessageActivity.this).execute(toEmail, fromEmail, fromPassword, emailSubject, emailBody);
            }
        });
    }
}
