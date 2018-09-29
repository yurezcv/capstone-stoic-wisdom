package ua.yurezcv.stoic.data.model;

import com.google.gson.annotations.SerializedName;

class Source {

    @SerializedName("title")
    private String title;

    @SerializedName("subtitle")
    private String subtitle;

    @Override
    public String toString() {
        return "Source{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                '}';
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTitle() {
        return title;
    }
}
