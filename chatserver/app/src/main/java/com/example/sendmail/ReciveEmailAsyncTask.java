package com.example.sendmail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

public class ReciveEmailAsyncTask extends AsyncTask {
    private ProgressDialog statusDialog;
    private Activity sendMailActivity;
    private Context context;
    private String email, password;

    public ReciveEmailAsyncTask(Activity activity, Context context, String email, String password) {
        sendMailActivity = activity;
        this.context = context;
        this.email = email;
        this.password = password;

    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(sendMailActivity);
        statusDialog.setMessage("Getting ready...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            Log.i("ReciveMessages", "About to instantiate GMail...");
            Gmail androidEmail = new Gmail (null, null, null, null, null);
            publishProgress("Receiving...");
            List<MessageModel> messageModel = androidEmail.ReciveMessages(email, password);
            Intent intent = new Intent(context, MessagesActivity.class);
            intent.putExtra("msg", (Serializable) messageModel);
            context.startActivity(intent);
            Log.i("Done", "Done !!!");
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendMailTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());

    }

    @Override
    public void onPostExecute(Object result) {
        statusDialog.dismiss();
    }
}
