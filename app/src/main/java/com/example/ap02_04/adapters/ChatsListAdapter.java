package com.example.ap02_04.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap02_04.R;
import com.example.ap02_04.activities.MessagesActivity;
import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.User;
import com.example.ap02_04.webservices.WebChat;

import java.util.List;

// takes data from list of Chat objects, and transfer to recycler view as presentable data
public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ChatViewHolder> {

    private final LayoutInflater mInflater;
    private List<Chat> chats;

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDisplayName;
        private final TextView tvLastMessage;
        private final TextView tvCreated;
        private final ImageView ivProfilePic;
        private final CardView cvChat;
        private  ChatViewHolder(View itemView) {
            super(itemView);
            tvDisplayName = itemView.findViewById(R.id.tvDisplayName);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
            tvCreated = itemView.findViewById(R.id.tvCreated);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            cvChat = itemView.findViewById(R.id.cvChat);
        }
    }

    public ChatsListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = mInflater.inflate(R.layout.chat_layout, parent, false);
        return new ChatViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

        if (chats != null) {
            final Chat current = chats.get(position);
            final User contact = current.getContact(WebChat.getUsername());

            holder.tvDisplayName.setText(contact.getDisplayName());
            holder.ivProfilePic.setImageBitmap(getUserImage(contact.getProfilePic()));

            final Message lastMessage = current.getLastMessage();

            holder.tvLastMessage.setText(lastMessage.getContent());
            holder.tvCreated.setText(lastMessage.getCreated());

            // set click listener for the item view
            holder.cvChat.setOnClickListener(view -> {
                WebChat.setContact(contact);
                WebChat.setChat(chats.get(position));
                Context context = view.getContext();
                Intent i = new Intent(context, MessagesActivity.class);
                context.startActivity(i);
            });
        }
    }

    private Bitmap getUserImage(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public void setChats(List<Chat> s) {
        chats = s;
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (chats != null) {
            return chats.size();
        } else {
            return 0;
        }
    }

    public Chat getItem(int position) {
        if (chats != null) {
            return chats.get(position);
        } else {
            return null;
        }
    }

}
