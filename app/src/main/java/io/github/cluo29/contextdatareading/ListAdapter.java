package io.github.cluo29.contextdatareading;

/**
 * Created by Comet on 15/08/16.
 */

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Replay> objects;

    ListAdapter(Context context, ArrayList<Replay> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    Replay getProduct(int position) {
        return ((Replay) getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.schedule_item, parent, false);
        }

        Replay p = getProduct(position);

        ((TextView) view.findViewById(R.id.tvSI1)).setText("Tartget App Name: " + p.appName);
        ((TextView) view.findViewById(R.id.tvSI2)).setText("Data Source Type: " + p.sensor);
        ((TextView) view.findViewById(R.id.tvSI3)).setText("Status: " + p.status);

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(p.box);
        return view;
    }

    ArrayList<Replay> getBox() {
        ArrayList<Replay> box = new ArrayList<Replay>();
        for (Replay p : objects) {
            if (p.box)
                box.add(p);
        }
        return box;
    }

    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getProduct((Integer) buttonView.getTag()).setBox(isChecked);
        }
    };
}

