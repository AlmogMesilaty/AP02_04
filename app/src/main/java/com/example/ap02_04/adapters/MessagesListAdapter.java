package com.example.ap02_04.adapters;

//public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder> {
//    class MessageViewHolder extends RecyclerView.ViewHolder {
//        private final TextView tvContent;
//        private  MessageViewHolder(View itemView) {
//            super(itemView);
//            tvContent = itemView.findViewById(R.id.tvContent);
//        }
//    }
//    private final LayoutInflater mInflater;
//    private List<Message> messages;
//
//  public MessagesListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

//    @Override
//    public MessagesListAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View viewItem = mInflater.inflate(R.layout.chat_layout, parent, false);
//        return new MessagesListAdapter.MessageViewHolder(viewItem);
//    }

//    @Override
//    public void onBindViewHolder(MessagesListAdapter.MessageViewHolder holder, int position) {
//        if (messages != null) {
//            final Message current = messages.get(position);
//            holder.tvContent.setText(current.getContent());
//        }
//    }
//
//    public void setChats(List<Message> s) {
//        messages = s;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemCount() {
//        if (messages != null) {
//            return messages.size();
//        } else {
//            return 0;
//        }
//    }
//}
