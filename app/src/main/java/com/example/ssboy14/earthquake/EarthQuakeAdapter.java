package com.example.ssboy14.earthquake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    public EarthQuakeAdapter(Context context, List<EarthQuake> list) {
        super(context,0, list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        EarthQuake earthQuake = getItem(position);

        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).
                            inflate(R.layout.earthquakesample, parent, false);
        }

        TextView mag = view.findViewById(R.id.mag);
        TextView loc = view.findViewById(R.id.location);
        TextView date = view.findViewById(R.id.earthquake_date);

        mag.setText(earthQuake.getMag());
        loc.setText(earthQuake.getLocation());
        date.setText(earthQuake.getDate());


        return view;
    }
}
