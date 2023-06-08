package com.example.socialgift.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.socialgift.R;
import com.example.socialgift.controller.MercadoExpressController;
import com.example.socialgift.controller.UsersController;
import com.example.socialgift.model.Gift;
import com.example.socialgift.model.Wishlist;

import java.util.ArrayList;
import java.util.List;

public class ShowReservedFragment extends Fragment {
    public static ArrayAdapter<String> adapter;
    private UsersController usersController;
    public static MercadoExpressController mercadoExpressController;
    public static ListView listView;
    public static ArrayList<String> arrayList = new ArrayList<>();
    public static List<Gift> lstGift = new ArrayList<>();
    public static Wishlist wishlist;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usersController = new UsersController(this, getActivity());
        mercadoExpressController = new MercadoExpressController(this, getActivity());
        View rootView = inflater.inflate(R.layout.fragment_show_reserved, container, false);
        usersController.getGiftsReserved(SearchFragment.user.getId());
        //ImageView imageView = (ImageView) getView().findViewById(R.id.);
        listView = (ListView) rootView.findViewById(R.id.lv_fragmentReserved);

        listView.setVisibility(View.GONE);

        adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.requestLayout();




        return rootView;
    }
}