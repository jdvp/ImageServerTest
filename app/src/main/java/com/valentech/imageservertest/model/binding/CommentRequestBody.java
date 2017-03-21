package com.valentech.imageservertest.model.binding;

/**
 * Created by jdvp on 3/20/17.
 */

public class CommentRequestBody {
    String user;
    String body;

    public CommentRequestBody(String user, String body) {
        this.user = user;
        this.body = body;
    }
}
