package com.valentech.imageservertest.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;
import com.valentech.imageservertest.R;
import com.valentech.imageservertest.activity.injection.InjectorActivity;
import com.valentech.imageservertest.model.binding.Comment;
import com.valentech.imageservertest.model.binding.Image;
import com.valentech.imageservertest.viewmodel.ViewImageDetailActivityViewModel;
import com.valentech.imageservertest.viewmodel.module.ViewImageDetailActivityModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jdvp on 3/19/17.
 */

public class ViewImageDetailActivity extends InjectorActivity {
    public static final String TAG = ViewImageDetailActivity.class.getSimpleName();
    public static final String IMAGE_KEY = "image_key";

    @BindView(R.id.detailed_image)
    ImageView imageView;

    @BindView(R.id.image_caption)
    TextView captionTextView;

    @BindView(R.id.confirm_button)
    Button confirmButton;

    @BindView(R.id.cancel_button)
    Button cancelButton;

    @BindView(R.id.name_edit_text)
    TextView nameEditText;

    @BindView(R.id.comment_edit_text)
    TextView commentEditText;

    @BindView(R.id.comments)
    LinearLayout commentsSection;

    @Inject
    ViewImageDetailActivityViewModel viewModel;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private String id;

    @Override
    public Object getModule() {
        return new ViewImageDetailActivityModule();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_detail);
        ButterKnife.bind(this);
        confirmButton.setText(R.string.comment);
        if (getIntent() != null && getIntent().getSerializableExtra(IMAGE_KEY) != null) {
            Image image = (Image) getIntent().getSerializableExtra(IMAGE_KEY);
            id = image.getId();
            String user = image.getUser();
            String caption = image.getCaption();
            updateComments(image);

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

    @Override
    public void onResume() {
        super.onResume();

        compositeSubscription.add(RxView.clicks(confirmButton).flatMap( aVoid -> {
            if (!isNullOrEmpty(nameEditText.getText().toString()) || !isNullOrEmpty(commentEditText.getText().toString())) {
                return viewModel.postComment(id, nameEditText.getText().toString(), commentEditText.getText().toString());
            }
            return Observable.empty();
        }).subscribe(image -> {
            clearCommentFields();
            updateComments(image);
        }, throwable -> {
            Toast.makeText(this, R.string.comment_failed, Toast.LENGTH_LONG).show();
            Log.e(TAG, "unable to comment", throwable);
        }));

        compositeSubscription.add(RxView.clicks(cancelButton).subscribe(aVoid -> clearCommentFields()));
    }

    private void clearCommentFields() {
        nameEditText.setText("");
        commentEditText.setText("");
    }

    private void updateComments(Image image) {
        commentsSection.removeAllViews();
        if (image.getComments() != null) {
            for (Comment comment : image.getComments()) {
                TextView textView = new TextView(this);
                textView.setText(Html.fromHtml(getString(R.string.formatted_comment, comment.getUser(), comment.getBody())));
                commentsSection.addView(textView);
            }
        }
    }

    private int getWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    
    private static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().equals("");
    }
}
