package com.example.socialgift.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.socialgift.R;
import com.example.socialgift.controller.FriendsController;
import com.example.socialgift.controller.UsersController;
import com.example.socialgift.model.User;
import com.example.socialgift.model.Wishlist;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFriendsFragment} factory method to
 * create an instance of this fragment.
 */
public class UserFriendsFragment extends Fragment {

    public static ArrayAdapter<String> adapter;
    private FriendsController friendsController;
    public static ListView listView;
    public static ArrayList<String> arrayList;
    public static List<User> lstUsers = new ArrayList<>();
    public static Wishlist wishlist;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendsController = new FriendsController(this, getActivity());
        View rootView = inflater.inflate(R.layout.fragment_user_friends, container, false);
        friendsController.getUserFriends(MainActivity.me.getId());
        //ImageView imageView = (ImageView) getView().findViewById(R.id.);
        listView = (ListView) rootView.findViewById(R.id.lv_fragmentUserFriends);

        listView.setVisibility(View.GONE);

        arrayList = new ArrayList<>();

        adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.requestLayout();
        return rootView;
    }
}