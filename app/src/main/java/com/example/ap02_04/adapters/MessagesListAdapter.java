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
    private int count;

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSentContent;
        private final TextView tvReceivedContent;

        private MessageViewHolder(View itemView) {
            super(itemView);
            tvSentContent = itemView.findViewById(R.id.tvSentContent);
            tvReceivedContent = itemView.findViewById(R.id.tvReceivedContent);
        }
    }


    public MessagesListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    // Define view type constants
    private static final int VIEW_TYPE_SENT_MESSAGE = 1;
    private static final int VIEW_TYPE_RECEIVED_MESSAGE = 2;

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem;
        if (viewType == VIEW_TYPE_SENT_MESSAGE) {
            viewItem = mInflater.inflate(R.layout.sent_message_layout, parent, false);
        } else {
            viewItem = mInflater.inflate(R.layout.received_message_layout, parent, false);
        }
        return new MessageViewHolder(viewItem);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSender().getUsername().equals(WebChat.getUsername())) {
            return VIEW_TYPE_SENT_MESSAGE;
        } else {
            return VIEW_TYPE_RECEIVED_MESSAGE;
        }
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
//        notifyDataSetChanged();
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
