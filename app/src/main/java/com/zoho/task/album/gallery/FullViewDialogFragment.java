package com.zoho.task.album.gallery;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.zoho.task.album.R;
import com.zoho.task.album.imageloader.ImageLoader;


public class FullViewDialogFragment extends DialogFragment {
    private View view;
    private ImageView imageViewPreview;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.image_fullscreen_preview, container,
                    false);
        }
        initializeObject();
        loadImage();
        return view;
    }

    /**
     * initializeObject method is used to create all objects.
     */
    private void initializeObject() {
        imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

    }

    /**
     * loadImage method is used to load the image to imageview.
     */
    private void loadImage() {
        String imageUrl = getArguments().getString("imageURL");
        ImageLoader.loadImage(getActivity(), imageUrl, imageViewPreview, progressBar);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

}