package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.events.SwitchFragmentEvent;
import com.mshvdvskgmail.technoparkmessenger.models.ChatsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.GroupsListItem;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentChat;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentChatGroup;
import com.mshvdvskgmail.technoparkmessenger.models.ChatsListItem;
import com.mshvdvskgmail.technoparkmessenger.models.GroupsListItem;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 29/03/2017.
 */

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    private ArrayList<Chat> groupsList;
    private View rowView;
    private Context context;
    private Chat currentItem;
    private int count;

    private String name;
    private String lastLine;
    private String time;
    private boolean isOnline;
    private boolean hasNew;
    private int newCount;


    private TextView itemName;
    private TextView itemLastMessage;
    private TextView itemTime;
    private ImageView itemOnline;
    private FrameLayout itemNewNotifi;
    private TextView itemNotifiCount;

    private TextView tvName;
    private TextView tvLastMessage;
    private TextView tvTime;

    private FragmentManager fManager;

    public GroupListAdapter(ArrayList <Chat> groupsList, Context context) {
        this.groupsList = groupsList;
        this.context = context;
//    public GroupListAdapter(ArrayList <GroupsListItem> groupsList, Context context, FragmentManager fManager) {
//        this.groupsList = groupsList;
//        this.context = context;
        this.fManager = fManager;
        count = 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_groups_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        viewHolder.context = context;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position==0){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            params.topMargin = 25;
        } else {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mView.getLayoutParams();
            params.topMargin = 0;
        }

        if (position == groupsList.size() - 1){
            FrameLayout mFrameLayout = holder.mFrameLayout;
            mFrameLayout.setVisibility(View.GONE);
        }

//        ImageView profileIcon = (ImageView) holder.mView.findViewById(R.id.profile_icon);
//        Picasso.with(context).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

        currentItem = groupsList.get(count);
        count++;

//        name = currentItem.getName();
//        lastLine = currentItem.getLastMessage();
//        time = currentItem.getTime();

        tvName = holder.tvName;
        tvLastMessage = holder.tvLastMessage;
        tvTime = holder.tvTime;

        tvName.setText(name);
        tvLastMessage.setText(lastLine);
        tvName.setText(time);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new SwitchFragmentEvent(Fragments.CHAT_GROUP, null));
                //TODO FragmentChatGroup chat = new FragmentChatGroup();
//                fManager.beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, chat)
//                        .addToBackStack(null)
//                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        FrameLayout mFrameLayout;
        Chat chat;
        Context context;
        TextView tvName;
        TextView tvLastMessage;
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.recycler_item_groups_fl_item_separator);
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_groups_tv_name);
            tvLastMessage = (TextView) itemView.findViewById(R.id.recycler_item_groups_tv_last_message);
            tvTime = (TextView) itemView.findViewById(R.id.recycler_item_groups_tv_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).executeAction("showChat", chat);
                }
            });
        }

        public void setChat(Chat achat){
            chat = achat;
        }
    }

}
