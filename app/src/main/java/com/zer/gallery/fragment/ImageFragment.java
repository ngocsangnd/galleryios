package com.zer.gallery.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zer.gallery.FullScreenActivity;
import com.zer.gallery.R;
import com.zer.gallery.adapter.GridViewAdapter;

import java.util.ArrayList;

/**
 * Created by Anh Son on 17/05/2017.
 */

public class ImageFragment extends BaseFragment{
    private GridViewAdapter adapter;
    private GridView gridView;
    private ArrayList<String> imagePaths = new ArrayList<String>();

    public void setImagePaths(ArrayList<String> imagePaths) {
        this.imagePaths.removeAll(this.imagePaths);
        this.imagePaths.addAll(imagePaths);
    }

    @Override
    protected void initsView(View view) {
        gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setColumnWidth(GridView.AUTO_FIT);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), FullScreenActivity.class);
                intent.putExtra("filepath", imagePaths);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        adapter = new GridViewAdapter(getContext(), imagePaths);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected int getViews() {
        return R.layout.fragment_all_image;
    }
    public void notifyDataChanged() {
        adapter.setFilePath(imagePaths);
        adapter.notifyDataSetChanged();
    }
}
