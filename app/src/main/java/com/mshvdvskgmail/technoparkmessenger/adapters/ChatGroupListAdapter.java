package com.mshvdvskgmail.technoparkmessenger.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.fragments.FragmentDocumentsList;
import com.mshvdvskgmail.technoparkmessenger.models.MessageChatItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static android.view.View.GONE;

/**
 * Created by mshvdvsk on 06/04/2017.
 */

public class ChatGroupListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = FragmentDocumentsList.class.toString();
    private ArrayList<MessageChatItem> messagesList;
    private View rowView;
    private ViewGroup.MarginLayoutParams params;
    private LinearLayout.LayoutParams params2;
    private Context context;
    private MessageChatItem currentItem;
    private int count;

    private String text;
    private String time;
    private int status;
    private int type;

    private TextView tvText;
    private TextView tvName;
    private TextView tvTime;
    private TextView tvFileType;
    private TextView tvFileSize;
    private TextView tvFileName;
    private ImageView imageStatus;
    private ImageView imageBlobCorner;


    public ChatGroupListAdapter(ArrayList <MessageChatItem> messagesList, Context context) {
        this.messagesList = messagesList;
        this.context = context;
        count = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case 0: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_incoming_text_named, parent, false);
                ChatGroupListAdapter.MessageViewHolder viewHolder0 = new ChatGroupListAdapter.MessageViewHolder(rowView);
                return viewHolder0;
            case 1: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_outgoing_text_named, parent, false);
                ChatGroupListAdapter.MessageViewHolder viewHolder1 = new ChatGroupListAdapter.MessageViewHolder(rowView);
                return viewHolder1;
            case 2: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_incoming_pic_named, parent, false);
                ChatGroupListAdapter.PicHolder viewHolder2 = new ChatGroupListAdapter.PicHolder(rowView);
                return viewHolder2;
            case 3: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_outgoing_pic_named, parent, false);
                ChatGroupListAdapter.PicHolder viewHolder3 = new ChatGroupListAdapter.PicHolder(rowView);
                return viewHolder3;
            case 4: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_incoming_file_named, parent, false);
                ChatGroupListAdapter.FileHolder viewHolder4 = new ChatGroupListAdapter.FileHolder(rowView);
                return viewHolder4;
            case 5: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_outgoing_file_named, parent, false);
                ChatGroupListAdapter.FileHolder viewHolder5 = new ChatGroupListAdapter.FileHolder(rowView);
                return viewHolder5;
            case 7: rowView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_message_item_time, parent, false);
                ChatGroupListAdapter.TimeHolder viewHolder7 = new ChatGroupListAdapter.TimeHolder(rowView);
                return viewHolder7;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int dpValue = 7; // margin in dips
        float d = context.getResources().getDisplayMetrics().density;
        int marginTopSmall = (int)(dpValue * d); // margin in pixels

        dpValue = 23; // margin in dips
        int marginTopBig = (int)(dpValue * d); // margin in pixels

        type = messagesList.get(position).getType();

        switch (type) {


            case 0:
            case 1:

                ChatGroupListAdapter.MessageViewHolder msgHolder = (ChatGroupListAdapter.MessageViewHolder) holder;

                tvText = msgHolder.tvText;
                tvTime = msgHolder.tvTime;

                imageStatus = msgHolder.tvStatus;

                tvText.setText(messagesList.get(position).getText());
                tvTime.setText(messagesList.get(position).getTime());

                  /* if outgoing, show status */
                if (messagesList.get(position).getType() == 1) {

                    status = messagesList.get(position).getStatus();
                    switch (status) {
                        case 0:
                            imageStatus.setVisibility(GONE);
                            break;
                        case 1:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_pending);
                            break;
                        case 2:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_sent);
                            break;
                        case 3:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_recieved);
                            break;
                        case 4:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_read);
                            break;
                    }
                }

                /* setting blob corner and blob margin */

                imageBlobCorner = msgHolder.imageBlobCorner;

                try{
                    if (messagesList.get(position).isIncoming()!=messagesList.get(position+1).isIncoming()){
                        imageBlobCorner.setVisibility(View.VISIBLE);
                        params = (ViewGroup.MarginLayoutParams) msgHolder.view.getLayoutParams();
                        params.bottomMargin = marginTopBig;
                    } else {
                        imageBlobCorner.setVisibility(View.INVISIBLE);
                        params = (ViewGroup.MarginLayoutParams) msgHolder.view.getLayoutParams();
                        params.bottomMargin = marginTopSmall;
                    }
                } catch (Exception e) {
                    imageBlobCorner.setVisibility(View.VISIBLE);

                    params = (ViewGroup.MarginLayoutParams) msgHolder.view.getLayoutParams();
                    params.bottomMargin = marginTopBig;
                }

                /* setting name visibility */

                if (messagesList.get(position).isIncoming()){

                    tvName = msgHolder.tvName;
                    tvName.setText(messagesList.get(position).getName());

                    try{
                        if (messagesList.get(position).getName().equals(messagesList.get(position-1).getName())){
                            tvName.setVisibility(View.GONE);
                        } else {
                            tvName.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        if (messagesList.get(position).isIncoming()){
                            tvName.setVisibility(View.VISIBLE);
                        }
                    }
                }


                break;

            case 2:
            case 3:

                ChatGroupListAdapter.PicHolder picHolder = (ChatGroupListAdapter.PicHolder) holder;

                imageStatus = picHolder.status;

                tvTime = picHolder.time;
                tvTime.setText(messagesList.get(position).getTime());

                ImageView pic = picHolder.pic;
                Picasso.with(context).load(R.drawable.oh_my_cat).transform(new RoundedCornersTransformation(20,0)).into(pic); // TODO обрезалка углов работает плохо

                if (messagesList.get(position).getType() == 3) {

                    status = messagesList.get(position).getStatus();
                    switch (status) {
                        case 0:
                            imageStatus.setVisibility(GONE);
                            break;
                        case 1:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_pending);
                            break;
                        case 2:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_sent);
                            break;
                        case 3:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_recieved);
                            break;
                        case 4:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_read);
                            break;
                    }
                }

                /* setting blob corner and blob margin */

                imageBlobCorner = picHolder.imageBlobCorner;

                try{
                    if (messagesList.get(position).isIncoming()!=messagesList.get(position+1).isIncoming()){
                        imageBlobCorner.setVisibility(View.VISIBLE);
                        params = (ViewGroup.MarginLayoutParams) picHolder.view.getLayoutParams();
                        params.bottomMargin = marginTopBig;
                    } else {
                        imageBlobCorner.setVisibility(View.INVISIBLE);
                        params = (ViewGroup.MarginLayoutParams) picHolder.view.getLayoutParams();
                        params.bottomMargin = marginTopSmall;
                    }
                } catch (Exception e) {
                    imageBlobCorner.setVisibility(View.VISIBLE);

                    params = (ViewGroup.MarginLayoutParams) picHolder.view.getLayoutParams();
                    params.bottomMargin = marginTopBig;
                }

                /* setting name visibility */

                if (messagesList.get(position).isIncoming()){

                    tvName = picHolder.tvName;
                    tvName.setText(messagesList.get(position).getName());

                    try{
                        if (messagesList.get(position).getName().equals(messagesList.get(position-1).getName())){
                            tvName.setVisibility(View.GONE);
                        } else {
                            tvName.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        if (messagesList.get(position).isIncoming()){
                            tvName.setVisibility(View.VISIBLE);
                        }
                    }
                }

                break;

            case 4:
            case 5:

                ChatGroupListAdapter.FileHolder fileHolder = (ChatGroupListAdapter.FileHolder) holder;

                tvFileType = fileHolder.tvFileType;
                tvFileType.setText(messagesList.get(position).getFileType());

                tvFileName = fileHolder.tvFileName;
                tvFileName.setText(messagesList.get(position).getFileName());

                tvFileSize = fileHolder.tvFileSize;
                tvFileSize.setText(messagesList.get(position).getFileSize());

                if (messagesList.get(position).getType() == 5) {

                    status = messagesList.get(position).getStatus();
                    switch (status) {
                        case 0:
                            imageStatus.setVisibility(GONE);
                            break;
                        case 1:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_pending);
                            break;
                        case 2:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_sent);
                            break;
                        case 3:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_recieved);
                            break;
                        case 4:
                            imageStatus.setVisibility(View.VISIBLE);
                            imageStatus.setImageResource(R.drawable.ic_message_read);
                            break;
                    }
                }

                imageBlobCorner = fileHolder.imageBlobCorner;

                try{
                    if (messagesList.get(position).isIncoming()!=messagesList.get(position+1).isIncoming()){
                        imageBlobCorner.setVisibility(View.VISIBLE);
                        params = (ViewGroup.MarginLayoutParams) fileHolder.view.getLayoutParams();
//                        params.topMargin = marginTopSmall;
                        params.bottomMargin = marginTopBig;
                    } else {
                        imageBlobCorner.setVisibility(View.INVISIBLE);
                        params = (ViewGroup.MarginLayoutParams) fileHolder.view.getLayoutParams();
//                        params.topMargin = marginTopBig;
                        params.bottomMargin = marginTopSmall;
                    }
                } catch (Exception e) {
                    imageBlobCorner.setVisibility(View.VISIBLE);

                    params = (ViewGroup.MarginLayoutParams) fileHolder.view.getLayoutParams();
                    params.bottomMargin = marginTopBig;
                }

                /* setting name visibility */

                if (messagesList.get(position).isIncoming()){

                    tvName = fileHolder.tvName;
                    tvName.setText(messagesList.get(position).getName());

                    try{
                        if (messagesList.get(position).getName().equals(messagesList.get(position-1).getName())){
                            tvName.setVisibility(View.GONE);
                        } else {
                            tvName.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        if (messagesList.get(position).isIncoming()){
                            tvName.setVisibility(View.VISIBLE);
                        }
                    }
                }

                break;

            case 7:

                ChatGroupListAdapter.TimeHolder timeHolder = (ChatGroupListAdapter.TimeHolder) holder;

                params = (ViewGroup.MarginLayoutParams) timeHolder.view.getLayoutParams();
                params.topMargin = marginTopBig;
                params.bottomMargin = marginTopBig;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        type = messagesList.get(position).getType();
        switch (type) {
            case 0: return 0; // incoming text
            case 1: return 1; // outgoing text
            case 2: return 2; // incoming pic
            case 3: return 3; // outgoing pic
            case 4: return 4; // incoming doc
            case 5: return 5; // outgoing doc
            case 7: return 7; // time
        }
        return -1;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvText;
        TextView tvName;
        TextView tvTime;
        ImageView tvStatus;
        ImageView imageBlobCorner;

        public MessageViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_message_text_named_tv_name);
            tvText = (TextView) itemView.findViewById(R.id.recycler_item_message_text_named_tv_text);
            tvTime = (TextView) itemView.findViewById(R.id.recycler_item_message_text_named_tv_time);
            tvStatus = (ImageView) itemView.findViewById(R.id.recycler_item_message_text_named_image_delivery_status);
            imageBlobCorner= (ImageView) itemView.findViewById(R.id.recycler_item_message_text_named_image_corner);
        }
    }


    public static class PicHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView pic;
        TextView time;
        TextView tvName;
        ImageView status;
        ImageView imageBlobCorner;

        public PicHolder(View itemView) {
            super(itemView);
            view = itemView;
            pic = (ImageView) itemView.findViewById(R.id.recycler_item_message_picture_named_image_picture);
            time = (TextView) itemView.findViewById(R.id.recycler_item_message_picture_named_tv_time);
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_message_picture_named_tv_name);
            status = (ImageView) itemView.findViewById(R.id.recycler_item_message_picture_named_delivery_status);
            imageBlobCorner= (ImageView) itemView.findViewById(R.id.recycler_item_message_picture_named_image_corner);
        }
    }

    public static class FileHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tvFileType;
        TextView tvFileName;
        TextView tvName;
        TextView tvFileSize;
        TextView tvTime;
        ImageView imageStatus;
        ImageView imageBlobCorner;

        public FileHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvFileType = (TextView) itemView.findViewById(R.id.recycler_item_message_file_named_tv_type);
            tvFileName = (TextView) itemView.findViewById(R.id.recycler_item_message_file_named_tv_file_name);
            tvName = (TextView) itemView.findViewById(R.id.recycler_item_message_file_named_tv_name);
            tvFileSize = (TextView) itemView.findViewById(R.id.recycler_item_message_file_named_tv_size);
            tvTime = (TextView) itemView.findViewById(R.id.recycler_item_message_file_named_tv_time);
            imageStatus = (ImageView) itemView.findViewById(R.id.recycler_item_message_file_named_image_delivery_status);
            imageBlobCorner= (ImageView) itemView.findViewById(R.id.recycler_item_message_file_named_image_corner);
        }
    }

    public static class TimeHolder extends RecyclerView.ViewHolder {

        View view;
        TextView text;

        public TimeHolder(View itemView) {
            super(itemView);
            view = itemView;
            text = (TextView) itemView.findViewById(R.id.recycler_item_message_time_tv_text);
        }
    }
}
