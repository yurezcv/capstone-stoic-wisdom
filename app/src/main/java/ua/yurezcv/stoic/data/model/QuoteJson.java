package ua.yurezcv.stoic.data.model;

import com.google.gson.annotations.SerializedName;

public class QuoteJson {

    @SerializedName("id")
    private long id;

    @SerializedName("quote")
    private String quote;

    @SerializedName("author_id")
    private long authorId;

    @SerializedName("source")
    private QuoteSource source;

    @Override
    public String toString() {
        return "QuoteJson{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                ", authorId=" + authorId +
                ", source=" + source +
                '}';
    }
}
