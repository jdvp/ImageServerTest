package com.valentech.imageservertest.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;
import com.valentech.imageservertest.R;
import com.valentech.imageservertest.activity.injection.InjectorActivity;
import com.valentech.imageservertest.viewmodel.PostImageViewModel;
import com.valentech.imageservertest.viewmodel.module.PostImageActivityModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jdvp on 3/19/17.
 */

public class PostImageActivity extends InjectorActivity {
    public static final String TAG = PostImageActivity.class.getSimpleName();

    @BindView(R.id.pick_button)
    Button pickButton;

    @BindView(R.id.selected_image)
    ImageView selectedImage;

    @BindView(R.id.button_bar)
    View buttonBar;

    @BindView(R.id.cancel_button)
    Button cancelButton;

    @BindView(R.id.confirm_button)
    Button uploadButton;

    @BindView(R.id.comment_edit_text)
    EditText commentEditText;

    @BindView(R.id.name_edit_text)
    EditText nameEditText;

    @Inject
    PostImageViewModel postImageViewModel;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Uri currentImageUri;

    @Override
    public Object getModule() {
        return new PostImageActivityModule();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image);
        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        compositeSubscription.add(RxView.clicks(pickButton).subscribe(aVoid -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto , 1);
        }));

        compositeSubscription.add(RxView.clicks(cancelButton).subscribe(aVoid -> onBackPressed()));

        compositeSubscription.add(RxView.clicks(uploadButton).subscribe(aVoid -> uploadImage()));
    }


    public void uploadImage() {
        try {
            //grab the image
            InputStream inputStream = getContentResolver().openInputStream(currentImageUri);
            assert inputStream != null;
            byte[] buffer = new byte[inputStream.available()];
            int couldRead = inputStream.read(buffer);
            if(couldRead <= 0)
                throw new Error("File is empty");

            //create a temporary file to store the image
            File tempFile = File.createTempFile("temp_file", null);
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(buffer);

            //set the appropriate form data
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), tempFile);
            MultipartBody.Part imageBody = MultipartBody.Part.createFormData("image", tempFile.getName(), reqFile);
            RequestBody requestName = RequestBody.create(MediaType.parse("multipart/form-data"), nameEditText.getText().toString());
            RequestBody requestCaption = RequestBody.create(MediaType.parse("multipart/form-data"), commentEditText.getText().toString());

            //show a spinner to tell the user that the upload is in progress
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setMessage(getString(R.string.sending));
            dialog.setCancelable(false);
            dialog.show();

            //actually send the data
            compositeSubscription.add(postImageViewModel.postImage(imageBody, requestName, requestCaption).subscribe(
            imageList -> {
                dialog.dismiss();
                //go back to the main screen if the image load is successful
                onBackPressed();
            }, throwable -> {
                dialog.dismiss();
                Toast.makeText(this, R.string.upload_failed, Toast.LENGTH_LONG).show();
                Log.e(TAG, "upload failed", throwable);
            }));

        } catch (Exception ignored) {
            Log.e(TAG, "caught exception: ", ignored);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    currentImageUri = imageReturnedIntent.getData();
                    selectedImage.setBackgroundColor(getResources().getColor(android.R.color.black));
                    Picasso.with(PostImageActivity.this).load(currentImageUri).into(selectedImage);
                    selectedImage.setLayoutParams(new LinearLayout.LayoutParams(getWidth(), getWidth()));
                    buttonBar.setVisibility(View.VISIBLE);
                    pickButton.setText(R.string.choose_again);
                }
                break;
        }
    }

    private int getWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
