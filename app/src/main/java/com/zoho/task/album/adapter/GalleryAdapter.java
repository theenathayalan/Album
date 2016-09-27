package com.zoho.task.album.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zoho.task.album.R;
import com.zoho.task.album.model.GalleryData;

import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private List<GalleryData> galleryDataList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public ProgressBar progressBar;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }


    public GalleryAdapter(Context context, List<GalleryData> galleryDataList) {
        mContext = context;
        this.galleryDataList = galleryDataList;
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

        Glide.with(mContext).load(galleryData.getThumbnailUrl())
                .thumbnail(0.5f).placeholder(R.mipmap.default_thumbnail)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return galleryDataList.size();
    }

}