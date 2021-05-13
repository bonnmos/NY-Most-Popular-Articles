package com.example.nymostpopular.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Medium {
    public String type;
    public String subtype;
    public String caption;
    public String copyright;
    public int approved_for_syndication;
    @SerializedName("media-metadata")
    public List<MediaMetadata> media_metadata;
}
