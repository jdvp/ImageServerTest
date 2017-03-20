package com.valentech.imageservertest.model.binding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jdvp on 3/11/17.
 */

public class ImageList {

    @Expose
    @SerializedName("images")
    private ArrayList<Image> images = new ArrayList<>();


    public ArrayList<Image> getImages() {
        return images;
    }
}
