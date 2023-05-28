package com.example.socialgift.view;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.socialgift.R;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    ListView listView;
    SearchView searchView;
    Activity activity = getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        //ImageView imageView = (ImageView) getView().findViewById(R.id.);
        searchView = (SearchView) rootView.findViewById(R.id.sv_fragmentSearch);
        listView = (ListView) rootView.findViewById(R.id.lv_fragmentSearch);

        listView.setVisibility(View.GONE);

        arrayList = new ArrayList<>();
        arrayList.add("Monday");
        arrayList.add("Tuesday");
        arrayList.add("Wednesday");
        arrayList.add("Thursday");
        arrayList.add("Friday");
        arrayList.add("Saturday");
        arrayList.add("Sunday");

        adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    listView.setVisibility(View.GONE);
                }else{
                    listView.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter(s);
                }
                return false;
            }
        });
        return rootView;
    }
}