package com.valentech.imageservertest.model.binding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jdvp on 3/20/17.
 */

public class Comment implements Serializable{
    @Expose
    @SerializedName("date")
    private Date date;

    @Expose
    @SerializedName("user")
    private String user;

    @Expose
    @SerializedName("body")
    private String body;

    public Date getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }
}
