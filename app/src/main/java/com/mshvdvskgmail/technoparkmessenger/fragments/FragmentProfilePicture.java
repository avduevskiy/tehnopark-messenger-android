package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;
import com.squareup.picasso.Picasso;


import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mshvdvsk on 03/04/2017.
 */

public class FragmentProfilePicture extends BaseFragment {
    private View mRootView;
    private Handler handler;

    public FragmentProfilePicture() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_profile_picture, container, false);

        Bundle bundle = getArguments();
        String userAvatar = bundle.getString("user.avatar");
        String userName = bundle.getString("user.cn");

        TextView tvProfileName = (TextView) mRootView.findViewById(R.id.fragment_profile_picture_tv_name);
        tvProfileName.setText(userName);

        ImageView profileIcon = (ImageView) mRootView.findViewById(R.id.fragment_profile_picture_image_picture);
        if(userAvatar != null)
            Picasso.with(getContext())
                    .load(userAvatar)
                    .resizeDimen(R.dimen.chat_item_avatar_medium,R.dimen.chat_item_avatar_medium)
                    .centerCrop()
                    .placeholder(R.drawable.icon_user)
                    .error(R.drawable.icon_user).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);
//
//        ImageView profileIcon = (ImageView) mRootView.findViewById(R.id.fragment_profile_picture_image_picture);
//        Picasso.with(getContext()).load(R.drawable.pushkin).transform(new RoundedCornersTransformation(360,0)).into(profileIcon);

//        handler = new Handler();
//
//        handler.postDelayed(new Runnable(){
//            @Override
//            public void run(){
//                profileIconShadow.setVisibility(View.VISIBLE);
//            }
//        }, 8200);
        setIconsTouchListeners(mRootView);

        return mRootView;
    }

    private void setIconsTouchListeners(View mRootView) {
        FrameLayout flBackButton = (FrameLayout) mRootView.findViewById(R.id.fragment_profile_picture_fl_back);
        flBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
    }

}
