package com.valentech.imageservertest.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.valentech.imageservertest.R;
import com.valentech.imageservertest.model.binding.Image;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jdvp on 3/19/17.
 */

public class ViewImageDetailActivity extends AppCompatActivity {
    public static final String TAG = ViewImageDetailActivity.class.getSimpleName();
    public static final String IMAGE_KEY = "image_key";

    @BindView(R.id.detailed_image)
    ImageView imageView;

    @BindView(R.id.image_caption)
    TextView captionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_detail);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().getSerializableExtra(IMAGE_KEY) != null) {
            Image image = (Image) getIntent().getSerializableExtra(IMAGE_KEY);
            String user = image.getUser();
            String caption = image.getCaption();

            imageView.setBackgroundColor(getResources().getColor(android.R.color.black));
            Picasso.with(ViewImageDetailActivity.this).load(image.getUrl()).into(imageView);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(getWidth(), getWidth()));

            if (isNullOrEmpty(caption) && !isNullOrEmpty(user)) {
                captionTextView.setText(Html.fromHtml(getString(R.string.posted_by, user)));
            } else if (!isNullOrEmpty(caption) && isNullOrEmpty(user)) {
                captionTextView.setText(caption);
            } else if (!isNullOrEmpty(caption) && !isNullOrEmpty(user)) {
                captionTextView.setText(Html.fromHtml(getString(R.string.img_caption, user, caption)));
            }
        } else {
            //if we don't have an image we might as well go back
            onBackPressed();
        }
    }

    private int getWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    
    private static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().equals("");
    }
}
