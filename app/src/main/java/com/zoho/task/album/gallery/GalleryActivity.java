package com.zoho.task.album.gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zoho.task.album.R;
import com.zoho.task.album.gallery.adapter.GalleryAdapter;
import com.zoho.task.album.gallery.model.GalleryData;
import com.zoho.task.album.helper.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;


public class GalleryActivity extends AppCompatActivity implements GalleryView {

    private List<GalleryData> galleryDataList;
    private ProgressDialog progressDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private GalleryPresenter galleryPresenter;

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
    }

    /**
     * setGalleryAdapter method is used for set the adapter to recyclerview.
     */
    private void setGalleryAdapter(final List<GalleryData> galleryDataList) {
        this.galleryDataList = galleryDataList;
        mAdapter = new GalleryAdapter(getApplicationContext(), galleryDataList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("imageURL", galleryDataList.get(position).getUrl());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                FullViewDialogFragment newFragment = new FullViewDialogFragment();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

}