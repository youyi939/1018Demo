package com.example.a1018demo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Goods> {

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

        Glide.with(parent.getContext()).load(good.getImage()).into(imageView);

        return view;
    }
}