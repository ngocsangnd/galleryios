package com.zer.gallery.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zer.gallery.AlbumActivity;
import com.zer.gallery.R;
import com.zer.gallery.model.Album;
import com.zer.gallery.utils.Const;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ngodi on 18/05/2017.
 */

public class AlbumAdapter extends ArrayAdapter<Album> {
    private ArrayList<Album> arrAlbum;
    private LayoutInflater inflater;
    private Context context;
    public AlbumAdapter(Context context, int resource, ArrayList<Album> objects) {
        super(context, resource, objects);
        arrAlbum = objects;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

    @Override
        public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if(view == null){
        viewHolder = new ViewHolder();
        view = inflater.inflate(R.layout.item_album,parent,false);
        ImageView imv = (ImageView) view.findViewById(R.id.imv_first_image);
        TextView tvBucket = (TextView) view.findViewById(R.id.tv_bucket);
        LinearLayout llCategory = (LinearLayout) view.findViewById(R.id.ll_category);
            TextView tvSizeAlbum = (TextView) view.findViewById(R.id.tv_size_album);
        viewHolder.tvBucket = tvBucket;
        viewHolder.imv = imv;
            viewHolder.llCategory = llCategory;
        viewHolder.tvSizeAlbum = tvSizeAlbum;
        view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        // viewHolder.tvName.setText(arrSong.get(position).getTitle());
        Picasso.with(context).load(new File(arrAlbum.get(position).getArrImage().get(0).getPath())).resize(200,200).centerCrop()
                        .into(viewHolder.imv);
        viewHolder.tvBucket.setText(arrAlbum.get(position).getBucket());
        viewHolder.tvSizeAlbum.setText(arrAlbum.get(position).getArrImage().size()+"");
        viewHolder.llCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , AlbumActivity.class);
                intent.putExtra(Const.IAMGES , arrAlbum.get(position).getArrImage());
                intent.putExtra(Const.TITLE , arrAlbum.get(position).getBucket());
                context.startActivity(intent);
            }
        });
        return view;
        }
    class ViewHolder {
        private LinearLayout llCategory;
        private TextView tvBucket, tvSizeAlbum;
        private ImageView imv;
    }
}

