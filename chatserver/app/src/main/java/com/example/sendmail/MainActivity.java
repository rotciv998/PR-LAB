package com.example.sendmail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity  {

    private AppCompatButton sendMessage;
    private AppCompatButton reciveMessages;
    private TextInputEditText email, password;
    private Button submit, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_method);

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_view, null);

        email = dialogView.findViewById(R.id.from);
        password = dialogView.findViewById(R.id.password);
        submit = (Button) dialogView.findViewById(R.id.buttonSubmit);
        cancel = (Button) dialogView.findViewById(R.id.buttonCancel);

        sendMessage = findViewById(R.id.sendMesage);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SendMessageActivity.class);
                startActivity(intent);
            }
        });
        reciveMessages = findViewById(R.id.reciveMessage);
        reciveMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ReciveEmailAsyncTask(MainActivity.this, MainActivity.this, email.getText().toString().trim(), password.getText().toString().trim()).execute();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // DO SOMETHINGS
                        dialogBuilder.dismiss();
                    }
                });

                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
            }
        });
    }
}
