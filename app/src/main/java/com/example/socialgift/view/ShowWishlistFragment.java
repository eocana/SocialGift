package com.example.socialgift.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.socialgift.R;
import com.example.socialgift.controller.UsersController;
import com.example.socialgift.model.User;
import com.example.socialgift.model.Wishlist;

import java.util.ArrayList;
import java.util.List;

public class ShowWishlistFragment extends Fragment {

    public static ArrayAdapter<String> adapter;
    private UsersController usersController;
    public static ListView listView;
    public static ArrayList<String> arrayList;
    public static List<Wishlist> lstWishlist = new ArrayList<>();
    public static Wishlist wishlist;

    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usersController = new UsersController(this, getActivity());
        View rootView = inflater.inflate(R.layout.fragment_show_wishlist, container, false);
        usersController.wishlistsUser(SearchFragment.user.getId());
        //ImageView imageView = (ImageView) getView().findViewById(R.id.);
        listView = (ListView) rootView.findViewById(R.id.lv_fragmentWishlist);

        listView.setVisibility(View.GONE);

        arrayList = new ArrayList<>();

        adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.requestLayout();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.requestLayout();
                wishlist = new Wishlist();
                wishlist.setId(lstWishlist.get((int) id).getId());
                wishlist.setName(lstWishlist.get((int) id).getName());
                wishlist.setCreationDate(lstWishlist.get((int) id).getCreationDate());
                wishlist.setDescription(lstWishlist.get((int) id).getDescription());
                wishlist.setGifts(lstWishlist.get((int) id).getGifts());
                wishlist.setIdUser(lstWishlist.get((int) id).getIdUser());
                wishlist.setEndDate(lstWishlist.get((int) id).getEnd_date());
                System.out.println("wishlist getId :: "+wishlist.getId());
                System.out.println("wishlist getName :: "+wishlist.getName());
                System.out.println("wishlist getDescription :: "+wishlist.getDescription());
                System.out.println("wishlist getIdUser :: "+wishlist.getIdUser());
                System.out.println("wishlist getCreationDate :: "+wishlist.getCreationDate());
                System.out.println("wishlist getGifts :: "+wishlist.getGifts());
                System.out.println("wishlist getEnd_date :: "+wishlist.getEnd_date());
                if(wishlist.getGifts()==null){
                    Toast.makeText(getActivity().getApplicationContext(), "No tiene regalos relacionados",Toast.LENGTH_SHORT).show();
                }else{
                    ShowGiftFragment.lstGifts = wishlist.getGifts();
                    startActivity( new Intent(getActivity().getApplicationContext(), ShowGiftActivity.class));
                }
            }
        });

        return rootView;
    }
}