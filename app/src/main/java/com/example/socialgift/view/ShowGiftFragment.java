package com.example.socialgift.view;

import static com.example.socialgift.view.SearchFragment.adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.socialgift.R;
import com.example.socialgift.controller.UsersController;
import com.example.socialgift.model.Gift;
import com.example.socialgift.model.User;

import java.util.ArrayList;
import java.util.List;

public class ShowGiftFragment extends Fragment {
    private UsersController usersController;
    public static ListView listView;
    public static List<Gift> lstGifts = new ArrayList<>();
    public static ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usersController = new UsersController(this, getActivity());
        View rootView = inflater.inflate(R.layout.fragment_show_gift, container, false);
        if(lstGifts.size() > 0){
            for (Gift g: lstGifts) {
                arrayList.add(g.getProductUrl());
            }
        }
        //ImageView imageView = (ImageView) getView().findViewById(R.id.);
        listView = (ListView) rootView.findViewById(R.id.lv_fragmentGift);

        listView.setVisibility(View.GONE);

        arrayList = new ArrayList<>();

        adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.requestLayout();
        System.out.println("arraylist :: "+arrayList);
        listView.setVisibility(View.VISIBLE);
        return rootView;
    }
}