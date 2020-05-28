package com.example.sendmail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<MessageModel> messageModelList;
    MessageModel messageModel;

    public MessagesAdapter(Context context, List<MessageModel> messageModelList) {
        this.inflater = LayoutInflater.from(context);
        this.messageModelList = messageModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.inbox_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        messageModel = messageModelList.get(position);
        for(MessageModel messageModel : messageModelList) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ============= " + messageModel.getSubject().toString());
        }

        holder.subject.setText(messageModel.getSubject());
        holder.from.setText(messageModel.getFrom());
        holder.message.setText(messageModel.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView message , from , subject;

        ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.content_value);
            from = itemView.findViewById(R.id.from_value);
            subject = itemView.findViewById(R.id.subject_value);
        }

    }
}
