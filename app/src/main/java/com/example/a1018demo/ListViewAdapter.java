package com.example.a1018demo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Goods> {
    Bitmap bitmap = null;
    private int resourceId;

    public ListViewAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        final TextView name = view.findViewById(R.id.name);
        final ImageView imageView = view.findViewById(R.id.img);
        final TextView price = view.findViewById(R.id.price);

        final Goods good = getItem(position);

        name.setText("商品名称:\n" + good.getName());
        price.setText("商品价格:\n" + good.getPrice());

        //Glide.with(parent.getContext()).load(good.getImage()).into(imageView);              //Glide框架

        imageView.setImageBitmap(returnBitmap(good.getImage()));


        return view;
    }

    private Bitmap returnBitmap(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL fileUrl = null;

                try {
                    fileUrl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream in = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return  bitmap;
    }           //原生根据url渲染图片

}