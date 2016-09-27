package com.zoho.task.album.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zoho.task.album.R;
import com.zoho.task.album.adapter.GalleryAdapter;
import com.zoho.task.album.app.ApiClient;
import com.zoho.task.album.app.ApiInterface;
import com.zoho.task.album.helper.RecyclerTouchListener;
import com.zoho.task.album.model.GalleryData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GalleryActivity extends AppCompatActivity {

    private List<GalleryData> galleryDataList;
    private ProgressDialog progressDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initializeObject();
        callPhotosAPI();
    }

    /**
     * initializeObject method is used to create all objects.
     */
    private void initializeObject() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        galleryDataList = new ArrayList<>();
    }

    /**
     * callPhotosAPI method is used for call the photos api.
     */
    private void callPhotosAPI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading json...");
        progressDialog.show();
        ApiInterface apiService =
                ApiClient.getClient(GalleryActivity.this).create(ApiInterface.class);
        Call<List<GalleryData>> call = apiService.getPhotosAPI();
        call.enqueue(new Callback<List<GalleryData>>() {
            @Override
            public void onResponse(Call<List<GalleryData>> call, Response<List<GalleryData>> response) {
                galleryDataList = response.body();
                setGalleryAdapter();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<GalleryData>> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                progressDialog.dismiss();
            }
        });

    }

    /**
     * setGalleryAdapter method is used for set the adapter to recyclerview.
     */
    private void setGalleryAdapter() {
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
                FullViewDialogFragment newFragment = FullViewDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

}