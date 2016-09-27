package com.zoho.task.album.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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


public class FullViewDialogFragment extends DialogFragment {
    private View view;
    private ImageView imageViewPreview;
    private ProgressBar progressBar;

    static FullViewDialogFragment newInstance() {
        FullViewDialogFragment fullViewDialogFragment = new FullViewDialogFragment();
        return fullViewDialogFragment;
    }

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

        Glide.with(getActivity()).load(imageUrl)
                .thumbnail(0.5f)
                .placeholder(R.mipmap.default_full)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageViewPreview);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

}