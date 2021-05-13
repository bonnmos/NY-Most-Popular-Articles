package com.example.nymostpopular.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    public String uri;
    public String url;
    public Object id;
    public Object asset_id;
    public String source;
    public String published_date;
    public String updated;
    public String section;
    public String subsection;
    public String nytdsection;
    public String adx_keywords;
    public Object column;
    public String byline;
    public String type;
    public String title;
    @SerializedName("abstract")
    public String abstruct;
    public List<String> des_facet;
    public List<String> org_facet;
    public List<String> per_facet;
    public List<String> geo_facet;
    public List<Medium> media;
    public int eta_id;
}
