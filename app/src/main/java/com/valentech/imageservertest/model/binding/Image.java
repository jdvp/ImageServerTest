package com.valentech.imageservertest.model.binding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jdvp on 3/11/17.
 */

public class Image implements Serializable{

    @Expose
    @SerializedName("_id")
    private String id;

    @Expose
    @SerializedName("img")
    private String img;

    @Expose
    @SerializedName("date")
    private Date date;

    @Expose
    @SerializedName("user")
    private String user;

    @Expose
    @SerializedName("caption")
    private String caption;

    @Expose
    @SerializedName("comments")
    private ArrayList<Comment> comments;

    public String getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public String getUrl() {
        return img;
    }

    public String getUser() {
        return user;
    }

    public String getCaption() {
        return caption;
    }
}
