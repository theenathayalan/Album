package com.zoho.task.album.gallery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zoho.task.album.R;
import com.zoho.task.album.gallery.model.GalleryData;
import com.zoho.task.album.imageloader.ImageFetcher;

import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private List<GalleryData> galleryDataList;
    private Context mContext;
    private ImageFetcher mImageFetcher;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public ProgressBar progressBar;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }


    public GalleryAdapter(Context context, List<GalleryData> galleryDataList, ImageFetcher mImageFetcher) {
        mContext = context;
        this.galleryDataList = galleryDataList;
        this.mImageFetcher = mImageFetcher;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        GalleryData galleryData = galleryDataList.get(position);
        mImageFetcher.loadImage(galleryData.getThumbnailUrl(), holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return galleryDataList.size();
    }

}