/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.zoho.task.album.album;

import android.content.Context;

import com.zoho.task.album.album.model.AlbumData;

import java.util.List;

public class AlbumPresenterImpl implements AlbumPresenter, AlbumInteractor.OnLoginFinishedListener {

    private AlbumView albumView;
    private AlbumInteractor albumInteractor;

    public AlbumPresenterImpl(AlbumView albumView) {
        this.albumView = albumView;
        this.albumInteractor = new AlbumInteractorImpl();
    }

    @Override
    public void callAlbumAPI(Context context) {
        if (albumView != null) {
            albumView.showProgress();
        }
        albumInteractor.albumAPI(context,this);
    }

    @Override
    public void onDestroy() {
        albumView = null;
    }


    @Override
    public void onSuccess(List<AlbumData> albumDataList) {
        if (albumView != null) {
            albumView.onSuccess(albumDataList);
            albumView.hideProgress();
        }
    }

    @Override
    public void onException() {
        if (albumView != null) {
            albumView.onException();
            albumView.hideProgress();
        }
    }
}
