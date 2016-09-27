package com.zoho.task.album.app;

import com.zoho.task.album.model.AlbumData;
import com.zoho.task.album.model.GalleryData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("photos")
    Call<List<GalleryData>> getPhotosAPI();

    @GET("albums")
    Call<List<AlbumData>> getAlbumsAPI();

}
