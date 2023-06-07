package com.example.socialgift.controller;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.socialgift.datamanager.DataManagerAPI;
import com.example.socialgift.datamanager.DataManagerCallbacks;
import com.example.socialgift.datamanager.MercadoExpressAPI;
import com.example.socialgift.model.Product;
import com.example.socialgift.view.RegisterActivity;
import com.example.socialgift.view.ShowGiftFragment;

public class MercadoExpressController {
    private ShowGiftFragment showGiftFragment;

    private Context context;

    public interface DataManagerCallback<T> {
        void onSuccess(T data);
        void onError(String errorMessage);
    }

    public MercadoExpressController(ShowGiftFragment showGiftFragment, Context context) {
        this.showGiftFragment = showGiftFragment;
        this.context = context;
    }

    public void getAProduct(int productId){
        MercadoExpressAPI.getAProduct(productId, context, new DataManagerCallbacks.DataManagerCallbackProduct<>(){
            @Override
            public void onSuccess(Product product) {
                if(product!=null){
                    System.out.println(product);
                    System.out.println(product.getId());
                    System.out.println(product.getName());
                    ShowGiftFragment.arrayList.add(product.getName());
                    ShowGiftFragment.listView.requestLayout();
                    ShowGiftFragment.listView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "No se han podido recuperar los productos",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
