package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialgift.R;
import com.example.socialgift.view.SearchFragment;

import java.io.InputStream;

public class ShowUserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_profile);

        System.out.println("email :: "+ SearchFragment.user.getEmail());
        System.out.println("class :: "+ SearchFragment.user.getClass());
        System.out.println("image :: "+ SearchFragment.user.getImage());
        System.out.println("name :: "+ SearchFragment.user.getName());
        System.out.println("lastName :: "+ SearchFragment.user.getLastName());
        TextView myAwesomeTextView2 = (TextView)findViewById(R.id.textView2);
        TextView myAwesomeTextView3 = (TextView)findViewById(R.id.textView3);
        TextView myAwesomeTextView4 = (TextView)findViewById(R.id.textView4);

        new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView)).execute(SearchFragment.user.getImage());
        //myAwesomeTextView.setText(SearchFragment.user.getId());
        myAwesomeTextView2.setText("Email : "+SearchFragment.user.getEmail());
        myAwesomeTextView3.setText("Name : "+SearchFragment.user.getName());
        myAwesomeTextView4.setText("Lastname : "+SearchFragment.user.getLastName());
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...",Toast.LENGTH_SHORT).show();
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}