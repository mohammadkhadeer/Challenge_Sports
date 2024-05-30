
package com.example.model.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsBase {

    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
