package ua.yurezcv.stoic.data.model;

import com.google.gson.annotations.SerializedName;

public class QuoteJson {

    @SerializedName("id")
    private int id;

    @SerializedName("quote")
    private String quote;

    @SerializedName("author_id")
    private int authorId;

    @SerializedName("source")
    private Source source;

    @Override
    public String toString() {
        return "QuoteJson{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                ", authorId=" + authorId +
                ", source=" + source +
                '}';
    }

    public Quote toQuote() {
        return new Quote(id, quote, authorId, source.getTitle(), source.getSubtitle());
    }
}
