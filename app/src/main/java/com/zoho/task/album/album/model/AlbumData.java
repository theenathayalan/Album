package com.zoho.task.album.album.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AlbumData implements Serializable {
    @SerializedName("userId")
    int userId;
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
