package com.zoho.task.album.album;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zoho.task.album.R;
import com.zoho.task.album.album.adapter.AlbumsAdapter;
import com.zoho.task.album.album.model.AlbumData;
import com.zoho.task.album.gallery.GalleryActivity;
import com.zoho.task.album.helper.DividerItemDecoration;
import com.zoho.task.album.helper.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity implements AlbumView {
    private List<AlbumData> albumDataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AlbumsAdapter mAdapter;
    private ProgressDialog progressDialog;
    private AlbumPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initializeObject();
        setRecyclerClickListener();
        presenter = new AlbumPresenterImpl(this);
        presenter.callAlbumAPI(this);
    }

    /**
     * initializeObject method is used to create all objects.
     */
    private void initializeObject() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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
    public void onSuccess(List<AlbumData> albumDataList) {
        setAdapter(albumDataList);
    }

    @Override
    public void onException() {

    }

    /**
     * setRecyclerClickListener method is used for set click listener for recyclerView.
     */
    private void setRecyclerClickListener() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(AlbumActivity.this, GalleryActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    /**
     * setAdapter method is used for set adapter to recycler view.
     */
    private void setAdapter(List<AlbumData> albumDataList) {
        this.albumDataList = albumDataList;
        mAdapter = new AlbumsAdapter(albumDataList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(AlbumActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
