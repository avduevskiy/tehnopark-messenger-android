package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mshvdvskgmail.technoparkmessenger.ChatController;
import com.mshvdvskgmail.technoparkmessenger.Controller;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.activities.MainActivity;
import com.mshvdvskgmail.technoparkmessenger.adapters.ChatListAdapter;
import com.mshvdvskgmail.technoparkmessenger.events.DataLoadEvent;
import com.mshvdvskgmail.technoparkmessenger.events.MessageEvent;
import com.mshvdvskgmail.technoparkmessenger.models.MessageChatItem;
import com.mshvdvskgmail.technoparkmessenger.network.REST;
import com.mshvdvskgmail.technoparkmessenger.network.model.Chat;
import com.mshvdvskgmail.technoparkmessenger.network.model.ChatUser;
import com.mshvdvskgmail.technoparkmessenger.network.model.Message;
import com.mshvdvskgmail.technoparkmessenger.network.model.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by mshvdvsk on 30/03/2017.
 */

public class FragmentChat extends BaseFragment {
    private View rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager lm;
    private ArrayList<Message> messages;
    private Chat activeChat;
    private ChatListAdapter mAdapter;
    private EditText w_message;

    private long startTime;
    private int timeout = 2;

    protected Controller controller = Controller.getInstance();

    @Override
    public void onPause() {
  //      EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activeChat = (Chat) getArguments().getSerializable("chat");

        rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_chat_rv_messages);
        recyclerView.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        TextView tvContact = (TextView)rootView.findViewById(R.id.fragment_chat_tv_name);
        TextView tvStatus = (TextView)rootView.findViewById(R.id.fragment_chat_tv_online_status);
//        List<User> usersList = activeChat.Users();
        List<ChatUser> usersList = activeChat.users;

        if(activeChat.peer2peer == 0){
            //групповой чат
            tvContact.setText(activeChat.name);
            if(activeChat.admin.equals(Controller.getInstance().getAuth().getUser().id)) {
                tvStatus.setText("Вы администратор");
            }else{
                tvStatus.setText("Вы не администратор");
            }
        }else{
            //одиночный
            User user = usersList.get(0).User();
            if(user.id.equals(Controller.getInstance().getAuth().getUser().id)) {
                user = usersList.get(1).User();
            }
            tvContact.setText(user.cn);
        }

        //name = user.cn;
        ImageView ivProfile = (ImageView)rootView.findViewById(R.id.fragment_chat_iv_profile);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).executeAction("showGroupSettings", activeChat);
            }
        });

        w_message = (EditText) rootView.findViewById(R.id.fragment_chat_et_write_message);
        w_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(startTime + timeout * 1000 < System.currentTimeMillis()){
                    startTime = System.currentTimeMillis();
                    ChatController.getInstance().r.sendUserStatus(Controller.getInstance().getAuth().user.token, Controller.getInstance().getAuth().user.id, activeChat.uuid, w_message.getText().toString(), "text_input");
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        FrameLayout send_message = (FrameLayout) rootView.findViewById(R.id.fragment_chat_fl_send_message);

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        messages = new ArrayList<>();

        FrameLayout flBackButton = (FrameLayout) rootView.findViewById(R.id.fragment_chat_fl_back);
        flBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
//                FragmentMainFourTabScreen main = new FragmentMainFourTabScreen();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .replace(R.id.container, main)
//                        .addToBackStack(null)
//                        .commit();
            }
        });


//
//
//        MessageChatItem dummyObject1 = new MessageChatItem();
//        dummyObject1.setText("привет");
//        dummyObject1.setTime("15:40");
//        dummyObject1.setType(0);
//        dummyObject1.setStatus(0);
//        dummyObject1.setIncoming(true);
//
//        MessageChatItem dummyObject11 = new MessageChatItem();
//        dummyObject11.setText("привет");
//        dummyObject11.setTime("15:40");
//        dummyObject11.setType(1);
//        dummyObject11.setStatus(4);
//        dummyObject11.setIncoming(false);
//
//
//        MessageChatItem dummyObject2 = new MessageChatItem();
//        dummyObject2.setText("привет привет");
//        dummyObject2.setTime("15:40");
//        dummyObject2.setType(1);
//        dummyObject2.setStatus(4);
//        dummyObject2.setIncoming(false);
//
//        MessageChatItem dummyObject21 = new MessageChatItem();
//        dummyObject21.setText("привет привет");
//        dummyObject21.setTime("15:40");
//        dummyObject21.setType(0);
//        dummyObject21.setStatus(4);
//        dummyObject21.setIncoming(true);
//
//        MessageChatItem dummyObject3 = new MessageChatItem();
//        dummyObject3.setText("доброе утро любимый");
//        dummyObject3.setTime("15:40");
//        dummyObject3.setType(0);
//        dummyObject3.setStatus(4);
//        dummyObject3.setIncoming(true);
//
//        MessageChatItem dummyObject31 = new MessageChatItem();
//        dummyObject31.setText("доброе утро любимый");
//        dummyObject31.setTime("15:40");
//        dummyObject31.setType(1);
//        dummyObject31.setStatus(4);
//        dummyObject31.setIncoming(false);
//
//        MessageChatItem dummyObject4 = new MessageChatItem();
//        dummyObject4.setText("ты уже умерла или еще нет?");
//        dummyObject4.setTime("15:40");
//        dummyObject4.setType(1);
//        dummyObject4.setStatus(4);
//        dummyObject4.setIncoming(false);
//
//        MessageChatItem dummyObject41 = new MessageChatItem();
//        dummyObject41.setText("ты уже умерла или еще нет?");
//        dummyObject41.setTime("15:40");
//        dummyObject41.setType(0);
//        dummyObject41.setStatus(4);
//        dummyObject41.setIncoming(true);
//
//        MessageChatItem dummyObject5 = new MessageChatItem();
//        dummyObject5.setText("еще нет, дорогой, подожди 30 лет");
//        dummyObject5.setTime("15:40");
//        dummyObject5.setType(0);
//        dummyObject5.setStatus(4);
//        dummyObject5.setIncoming(true);
//
//
//        MessageChatItem dummyObject51 = new MessageChatItem();
//        dummyObject51.setText("еще нет, дорогой, подожди 30 лет");
//        dummyObject51.setTime("15:40");
//        dummyObject51.setType(1);
//        dummyObject51.setStatus(4);
//        dummyObject51.setIncoming(false);
//
//
//
//        MessageChatItem dummyObject6 = new MessageChatItem();
//        dummyObject6.setText("ох, ты меня успокоила, а то я думал, ждать 50 придется");
//        dummyObject6.setTime("00:40");
//        dummyObject6.setType(1);
//        dummyObject6.setStatus(4);
//        dummyObject6.setIncoming(false);
//
//
//        MessageChatItem dummyObject61 = new MessageChatItem();
//        dummyObject61.setText("ох, ты меня успокоила, а то я думал, ждать 50 придется");
//        dummyObject61.setTime("00:40");
//        dummyObject61.setType(0);
//        dummyObject61.setStatus(4);
//        dummyObject61.setIncoming(true);
//
//        MessageChatItem dummyObject611 = new MessageChatItem();
//        dummyObject611.setTime("00:40");
//        dummyObject611.setType(2);
//        dummyObject611.setStatus(4);
//        dummyObject611.setIncoming(true);
//
//        MessageChatItem dummyObject6111 = new MessageChatItem();
//        dummyObject6111.setTime("00:40");
//        dummyObject6111.setFileName("Документация по проекту...");
//        dummyObject6111.setFileSize("25 КБ");
//        dummyObject6111.setFileType("LSD");
//        dummyObject6111.setType(4);
//        dummyObject6111.setStatus(4);
//        dummyObject6111.setIncoming(true);
//
//        MessageChatItem dummyObject64 = new MessageChatItem();
//        dummyObject64.setTime("00:40");
//        dummyObject64.setFileName("Документация по проекту...");
//        dummyObject64.setFileSize("25 КБ");
//        dummyObject64.setFileType("LSD");
//        dummyObject64.setType(5);
//        dummyObject64.setStatus(4);
//        dummyObject64.setIncoming(false);
//
//
//
////        MessageChatItem dummyObject1 = new MessageChatItem();
////        dummyObject1.setText("привет");
////        dummyObject1.setTime("15:40");
////        dummyObject1.setType(0);
////        dummyObject1.setStatus(0);
////
////
////        MessageChatItem dummyObject2 = new MessageChatItem();
////        dummyObject2.setText("fuck fuck fucabkdasjkldsahjkdashjkldashjkdasjklh sdalhasdjklhasdjdas");
////        dummyObject2.setTime("15:40");
////        dummyObject2.setType(0);
//////        dummyObject2.setStatus(1);
////
////        MessageChatItem dummyObject3 = new MessageChatItem();
////        dummyObject3.setText("fuck fuck fucabkdasjkldsahjkdashjkldashjkdasjklh sdalhasdjklhasdjdas");
////        dummyObject3.setTime("15:40");
////        dummyObject3.setType(1);
////        dummyObject3.setStatus(2);
////
////        MessageChatItem dummyObject4 = new MessageChatItem();
////        dummyObject4.setText("fuck fuck fucabkdasjkldsahjkdashjkldashjkdasjklh sdalhasdjklhasdjdas");
////        dummyObject4.setTime("15:40");
////        dummyObject4.setType(1);
////        dummyObject4.setStatus(3);
////
////        MessageChatItem dummyObject5 = new MessageChatItem();
////        dummyObject5.setText("ПРОПУЩЕННЫЙ АУДИОЗВОНОК 17.03.2017");
////        dummyObject5.setType(6);
////        dummyObject5.setStatus(4);
////
////        MessageChatItem dummyObject6 = new MessageChatItem();
////        dummyObject6.setText("fuck fuck fucabkdasjkldsahjkdashjkldashjkdasjklh sdalhasdjklhasdjdas");
////        dummyObject6.setTime("00:40");
////        dummyObject6.setType(3);
////        dummyObject6.setStatus(4);
        REST.getInstance().messages(Controller.getInstance().getAuth().getUser().token.session_id, Controller.getInstance().getAuth().getUser().token.token, activeChat.uuid, "0", "100")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new REST.DataSubscriber<List<Message>>(){
                    @Override
                    public void onData(List<Message> data){
                        Log.w("Technopart", "recieve messages "+data);
                        messages.addAll(data);
                    }

                    @Override
                    public void onCompleted(){
                        mAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    }
                });

        /*messages.add(dummyObject1);
        messages.add(dummyObject11);
        messages.add(dummyObject2);
        messages.add(dummyObject21);
        messages.add(dummyObject3);
        messages.add(dummyObject31);
        messages.add(dummyObject4);
        messages.add(dummyObject41);
        messages.add(dummyObject5);
        messages.add(dummyObject51);
        messages.add(dummyObject6);
        messages.add(dummyObject61);
        messages.add(dummyObject611);
        messages.add(dummyObject6111);
        messages.add(dummyObject64);*/

        mAdapter = new ChatListAdapter(messages, activeChat, getContext());
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private void sendMessage() {
        ChatController.getInstance().r.sendMessage(Controller.getInstance().getAuth().user.token, Controller.getInstance().getAuth().user.id, activeChat.uuid, w_message.getText().toString(), "no local id", null).subscribe();
        w_message.setText("");
    }

    protected void eventMessage(Message message) {
        if(message.getType() == Message.Type.DIALOG) {
            messages.add(message);
            mAdapter.notifyDataSetChanged();

            recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
            ChatController.getInstance().r.sendMessageStatus(Controller.getInstance().getAuth().user.token, Controller.getInstance().getAuth().user.id, activeChat.uuid, message.uuid, message.local_id, Message.Status.DELIVERED);
        }else if(message.getType() == Message.Type.ERROR){
            Log.e("temp", message.message);
            Toast.makeText(getContext(), "Ошибка отправки", Toast.LENGTH_LONG).show();
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public final void onEvent(DataLoadEvent event) {
//        eventDataLoad(event.dataSource);
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public final void onEvent(MessageEvent event) {
//        eventMessage(event.getMessage());
//    }


    protected void eventDataLoad(String dataSource){
        if(dataSource.equals("Groups")) {
            Log.w("GroupsList", "eventDataLoad " + dataSource);
//            groups.clear();
//            groups.addAll(Controller.getInstance().getGroupChats());
            mAdapter.notifyDataSetChanged();
        }
    }

}
