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
import com.example.socialgift.view.ShowReservedFragment;

public class MercadoExpressController {
    private ShowGiftFragment showGiftFragment;
    private ShowReservedFragment showReservedFragment;

    private Context context;

    public interface DataManagerCallback<T> {
        void onSuccess(T data);
        void onError(String errorMessage);
    }

    public MercadoExpressController(ShowGiftFragment showGiftFragment, Context context) {
        this.showGiftFragment = showGiftFragment;
        this.context = context;
    }

    public MercadoExpressController(ShowReservedFragment showReservedFragment, Context context) {
        this.showReservedFragment = showReservedFragment;
        this.context = context;
    }

    public void getAProduct(int productId, int flag){
        MercadoExpressAPI.getAProduct(productId, context, new DataManagerCallbacks.DataManagerCallbackProduct<>(){
            @Override
            public void onSuccess(Product product) {
                if(product!=null){
                    if(flag==0){
                        System.out.println(product);
                        System.out.println(product.getId());
                        System.out.println(product.getName());
                        ShowGiftFragment.arrayList.add(product.getName());
                        ShowGiftFragment.listView.requestLayout();
                        ShowGiftFragment.listView.setVisibility(View.VISIBLE);
                    } else if (flag==1) {
                        System.out.println(product);
                        System.out.println(product.getId());
                        System.out.println(product.getName());
                        ShowReservedFragment.arrayList.add(product.getName());
                        ShowReservedFragment.listView.requestLayout();
                        ShowReservedFragment.listView.setVisibility(View.VISIBLE);
                    }else{

                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "No se han podido recuperar los productos",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
