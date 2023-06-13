package com.example.ap02_04.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ap02_04.R;
import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.User;

import java.util.List;

// takes data from list of Chat objects, and transfer to recycler view as presentable data
public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ChatViewHolder> {

    private String currentUser;

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDisplayName;
        private final TextView tvLastMessage;
        private final TextView tvCreated;
        private final ImageView ivProfilePic;
        private  ChatViewHolder(View itemView) {
            super(itemView);
            tvDisplayName = itemView.findViewById(R.id.tvDisplayName);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
            tvCreated = itemView.findViewById(R.id.tvCreated);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
        }
    }

    private final LayoutInflater mInflater;
    private List<Chat> chats;

    public ChatsListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    // inflate the layout from xml to an object using inflater
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // the inflater returns a view after the inflation
        View viewItem = mInflater.inflate(R.layout.chat_layout, parent, false);
        // returns the new view we created
        return new ChatViewHolder(viewItem);
    }

    @Override
    // the component will know when it can use an existing holder
    // after we created the holder we can insert the data from each
    // element in the list
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        if (chats != null) {
            final Chat current = chats.get(position);
            final User contact = current.getContact("aaa@gmail.com");
            holder.tvDisplayName.setText(contact.getDisplayName());
            holder.ivProfilePic.setImageResource(contact.getProfilePic());
            final Message lastMessage = current.getLastMessage();
            holder.tvLastMessage.setText(lastMessage.getContent());
            holder.tvCreated.setText(lastMessage.getCreated());
        }
    }

    public void setChats(List<Chat> s) {
        chats = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (chats != null) {
            return chats.size();
        } else {
            return 0;
        }
    }

}
