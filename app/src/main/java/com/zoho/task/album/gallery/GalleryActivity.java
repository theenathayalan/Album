package com.zoho.task.album.gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zoho.task.album.R;
import com.zoho.task.album.gallery.adapter.GalleryAdapter;
import com.zoho.task.album.gallery.model.GalleryData;
import com.zoho.task.album.helper.RecyclerTouchListener;
import com.zoho.task.album.imageloader.ImageCache;
import com.zoho.task.album.imageloader.ImageFetcher;

import java.util.ArrayList;
import java.util.List;


public class GalleryActivity extends AppCompatActivity implements GalleryView {
    private static final String IMAGE_CACHE_DIR = "thumbs";
    private List<GalleryData> galleryDataList;
    private ProgressDialog progressDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private GalleryPresenter galleryPresenter;
    private ImageFetcher mImageFetcher;
    private int mImageThumbSize = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initializeObject();
        galleryPresenter = new GalleryPresenterImpl(this);
        galleryPresenter.callGalleryAPI(this);
    }

    /**
     * initializeObject method is used to create all objects.
     */
    private void initializeObject() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        galleryDataList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading json...");
        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(this, IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f);
        mImageFetcher = new ImageFetcher(this, mImageThumbSize);
        mImageFetcher.setLoadingImage(R.mipmap.default_placeholder);
        mImageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onSuccess(List<GalleryData> galleryDataList) {
        setGalleryAdapter(galleryDataList);
    }

    @Override
    public void onException() {

    }

    @Override
    protected void onDestroy() {
        galleryPresenter.onDestroy();
        super.onDestroy();
        mImageFetcher.closeCache();
    }

    @Override
    public void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mImageFetcher.setPauseWork(false);
        mImageFetcher.setExitTasksEarly(true);
        mImageFetcher.flushCache();
    }


    /**
     * setGalleryAdapter method is used for set the adapter to recyclerview.
     */
    private void setGalleryAdapter(final List<GalleryData> galleryDataList) {
        this.galleryDataList = galleryDataList;
        mAdapter = new GalleryAdapter(getApplicationContext(), this.galleryDataList, mImageFetcher);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(GalleryActivity.this, FullViewActivity.class);
                intent.putExtra("imageURL", GalleryActivity.this.galleryDataList.get(position).getUrl());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


}