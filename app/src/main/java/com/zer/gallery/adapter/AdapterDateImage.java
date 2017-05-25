package com.zer.gallery.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.zer.gallery.FullScreenActivity;
import com.zer.gallery.R;
import com.zer.gallery.model.DateImage;
import com.zer.gallery.utils.Const;

import java.util.ArrayList;

/**
 * Created by ngodi on 18/05/2017.
 */

public class AdapterDateImage extends ArrayAdapter<DateImage> {
    private static final String TAG = "AdapterDateImage";
    private ArrayList<DateImage> arrDateImage;
    private LayoutInflater inflater;
    private Context context;

    public AdapterDateImage(Context context, int resource, ArrayList<DateImage> objects) {
        super(context, resource, objects);
        arrDateImage = objects;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        AdapterDateImage.ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new AdapterDateImage.ViewHolder();
            view = inflater.inflate(R.layout.item_time_image, parent, false);
            TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
            GridView gridViewDateImage = (GridView) view.findViewById(R.id.gridview_date_image);
            viewHolder.tvDate = tvDate;
            viewHolder.gridViewDateImage = gridViewDateImage;
            view.setTag(viewHolder);
        }
        viewHolder = (AdapterDateImage.ViewHolder) view.getTag();
        // viewHolder.tvName.setText(arrSong.get(position).getTitle());
        viewHolder.tvDate.setText(arrDateImage.get(position).getDate());
        GridViewAdapter adapter = new GridViewAdapter(context , arrDateImage.get(position).getArrPath());
        viewHolder.gridViewDateImage.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        viewHolder.gridViewDateImage.requestLayout();
        viewHolder.gridViewDateImage.setColumnWidth(GridView.AUTO_FIT);

        viewHolder.gridViewDateImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positionShow, long id) {
                Intent intent = new Intent(context , FullScreenActivity.class);
                intent.putExtra(Const.POSITION , positionShow);
                intent.putStringArrayListExtra(Const.FILEPATH , arrDateImage.get(position).getArrPath());
                context.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHolder {
        private TextView tvDate;
        private GridView gridViewDateImage;
    }
}