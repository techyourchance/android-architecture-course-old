package com.techyourchance.mvc.networking;

import com.google.gson.annotations.SerializedName;

public class QuestionSchema {

    @SerializedName("title")
    private final String mTitle;

    @SerializedName("question_id")
    private final String mId;

    @SerializedName("body")
    private final String mBody;

    @SerializedName("owner")
    private final UserSchema mOwner;

    public QuestionSchema(String title, String id, String body, UserSchema owner) {
        mTitle = title;
        mId = id;
        mBody = body;
        mOwner = owner;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getId() {
        return mId;
    }

    public String getBody() {
        return mBody;
    }

    public UserSchema getOwner() {
        return mOwner;
    }

}
