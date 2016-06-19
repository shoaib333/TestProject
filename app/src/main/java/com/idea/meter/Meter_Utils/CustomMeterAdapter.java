package com.idea.meter.Meter_Utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.idea.meter.Main_Entity.AddRemoveMetersActivity;
import com.idea.meter.R;

import android.os.Handler;

/**
 * Created by DELL on 6/18/2016.
 */
public class CustomMeterAdapter extends BaseAdapter {

    String [] result;
    Context context;
    Handler mDeviceSelectHandler;

    private static LayoutInflater inflater;

    public CustomMeterAdapter(AddRemoveMetersActivity parentActivity, String[] prgmNameList,Handler mHandler) {

        result=prgmNameList;
        context=parentActivity;
        mDeviceSelectHandler = mHandler;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return result.length;
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder=new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.custom_meter_list, null);

        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

        TextView temptv = new TextView (context);

        temptv.setText(result[position]);

        holder.tv.setText(temptv.getText());
        holder.img.setImageResource(R.drawable.settings_icon);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();

                String data = result[position];

                mDeviceSelectHandler.obtainMessage(AddRemoveMetersActivity.DEVICE_SELECTED, data).sendToTarget();
            }
        });
        return rowView;
    }
}
