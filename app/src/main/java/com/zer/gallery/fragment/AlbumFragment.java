package com.zer.gallery.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.zer.gallery.R;
import com.zer.gallery.adapter.AlbumAdapter;
import com.zer.gallery.model.Album;
import com.zer.gallery.model.Image;

import java.util.ArrayList;

/**
 * Created by Anh Son on 17/05/2017.
 */

public class AlbumFragment extends BaseFragment{
    private ListView listView;
    private AlbumAdapter albumAdapter;
    private static final String TAG = "AlbumFragment";
    private ArrayList<Album> arrAlbum = new ArrayList<>();
    private ArrayList<Image> arrImage = new ArrayList<>();
    private ArrayList<String> arrBucketAlbum = new ArrayList<>();
    public void setImagesAlbum(ArrayList<Image> arrImage){
        this.arrImage  =arrImage;
    }
    @Override
    protected void initsView(View view) {
        arrAlbum.clear();
        for(int i = 0 ; i < arrImage.size() ; i++){
            getBucket(arrImage.get(i).getBucket() , arrBucketAlbum);
        }
        for(int i = 0 ; i < arrBucketAlbum.size() ; i++){
            Album album = new Album(arrBucketAlbum.get(i) , arrImage.get(0).getPath() , new ArrayList<Image>());
            for (int j = 0 ; j < arrImage.size() ; j++){
                if(arrBucketAlbum.get(i).equals(arrImage.get(j).getBucket())){
                    album.addImage(arrImage.get(j));
                }
            }
            arrAlbum.add(album);
        }
        listView = (ListView) view.findViewById(R.id.lv_album);
        albumAdapter = new AlbumAdapter(getContext() , android.R.layout.simple_list_item_1 , arrAlbum);
        Log.e(TAG , "bum : "+arrAlbum.size());
        listView.setAdapter(albumAdapter);
    }
    public void getBucket(String bucket , ArrayList<String> arrBucket){
        boolean isAdd = false;
        for(int i = 0 ; i < arrBucket.size() ; i++){
            if(bucket.equals(arrBucket.get(i))){
                isAdd = true;
            }
        }
        if(!isAdd){
            arrBucketAlbum.add(bucket);
        }
    }
    @Override
    protected int getViews() {
        return R.layout.fragment_album;
    }
//    class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{
//        private List<Album> mAlbum;
//
//        public AlbumAdapter(List<Album> mAlbum) {
//            this.mAlbum = mAlbum;
//        }
//
//        private Context mContext;
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            mContext = parent.getContext();
//            View view = LayoutInflater.from(mContext).inflate(R.layout.item_album, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.bindModelItem(mAlbum.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return mAlbum != null ? mAlbum.size() : 0;
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            Album mAlbum;
//            ImageView imvFirstImage;
//            TextView tvBucket , tvSizeAlbum;
//            public ViewHolder(View itemView) {
//                super(itemView);
//                imvFirstImage  = (ImageView) itemView.findViewById(R.id.imv_first_image);
//                tvBucket = (TextView) itemView.findViewById(R.id.tv_bucket);
//                tvSizeAlbum = (TextView) itemView.findViewById(R.id.tv_size_album);
//            }
//
//            public void bindModelItem(Album album) {
//                mAlbum = album;
////                Picasso.with(mContext).load(new File(mAlbum.getPathFirstImage())).resize(200,200).centerCrop()
////                        .into(imvFirstImage);
//                tvBucket.setText(mAlbum.getBucket());
//                tvSizeAlbum.setText(mAlbum.getArrImage().size()+"");
//                Log.e(TAG ,"viewholder : "+mAlbum.getBucket());
//
//            }
//        }
//    }


}
