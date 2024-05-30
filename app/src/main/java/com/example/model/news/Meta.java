
package com.example.model.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;
    @SerializedName("lastPage")
    @Expose
    private Integer lastPage;
    @SerializedName("perPage")
    @Expose
    private Integer perPage;
    @SerializedName("total")
    @Expose
    private Integer total;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
