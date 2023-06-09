package com.example.socialgift.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.socialgift.R;

import java.util.ArrayList;

public class RequestsAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public RequestsAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return Integer.valueOf(list.get(pos));
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.request_layout, null);
        }

        //Handle TextView and display string from your list
        TextView tvContact= (TextView)view.findViewById(R.id.user_name);
        tvContact.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button aprove= (Button)view.findViewById(R.id.aprove);

        aprove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something

            }
        });
        Button reject= (Button)view.findViewById(R.id.reject);
        reject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }
}
