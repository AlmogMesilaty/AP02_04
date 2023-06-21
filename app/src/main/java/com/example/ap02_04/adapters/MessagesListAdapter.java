package com.example.ap02_04.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ap02_04.R;
import com.example.ap02_04.entities.Message;
import com.example.ap02_04.webservices.WebChat;

import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder> {

    private final LayoutInflater mInflater;
    private List<Message> messages;

    private Message current;
    private int currentPosition = 0;


    class MessageViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSentContent;
        private final TextView tvReceivedContent;

        private MessageViewHolder(View itemView) {
            super(itemView);
//            currentPosition = 0;
            tvSentContent = itemView.findViewById(R.id.tvSentContent);
            tvReceivedContent = itemView.findViewById(R.id.tvReceivedContent);
        }

    }


    public MessagesListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        if (messages != null) {
            current = messages.get(currentPosition);
            currentPosition = currentPosition + 1;
            View viewItem;
            if (current.getSender().getUsername().equals(WebChat.getUsername())) {
                viewItem = mInflater.inflate(R.layout.sent_message_layout, parent, false);
            } else {
                viewItem = mInflater.inflate(R.layout.received_message_layout, parent, false);
            }
            return new MessageViewHolder(viewItem);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MessagesListAdapter.MessageViewHolder holder, int position) {

        if (messages != null) {
            current = messages.get(position);

            // checks if sent or received message
            if (current.getSender().getUsername().equals(WebChat.getUsername())){
                if (holder.tvSentContent != null) {
                    holder.tvSentContent.setText(current.getContent());
                }
            }

            else {
                if (holder.tvReceivedContent != null) {
                    holder.tvReceivedContent.setText(current.getContent());
                }
            }

        }

    }

    public void setMessages(List<Message> s) {
        messages = s;
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        } else {
            return 0;
        }
    }
}
