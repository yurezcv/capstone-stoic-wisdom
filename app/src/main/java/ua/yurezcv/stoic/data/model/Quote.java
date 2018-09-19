package ua.yurezcv.stoic.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

@Entity(tableName = Quote.TABLE_NAME,
        foreignKeys = {
                @ForeignKey(
                        entity = Author.class,
                        parentColumns = Author.COLUMN_ID,
                        childColumns = Quote.COLUMN_AUTHOR,
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )})
public class Quote {
    public static final String TABLE_NAME = "quotes";

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_QUOTE = "quote";
    public static final String COLUMN_AUTHOR = "author_id";
    public static final String COLUMN_SOURCE_TITLE = "source_title";
    public static final String COLUMN_SOURCE_SUBTITLE = "source_subtitle";
    public static final String COLUMN_IS_FAVORITE = "is_favorite";

    public Quote(String quote, long authorId) {
        setQuote(quote);
        setAuthor(authorId);
        // set default values for now
        setFavorite(false);
    }

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_QUOTE)
    private String quote;

    @ColumnInfo(name = COLUMN_AUTHOR)
    private long authorId;

    @ColumnInfo(name = COLUMN_SOURCE_TITLE)
    private String sourceTitle;

    @ColumnInfo(name = COLUMN_SOURCE_SUBTITLE)
    private String sourceSubTitle;

    @ColumnInfo(name = COLUMN_IS_FAVORITE)
    private boolean isFavorite;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthor(long authorId) {
        this.authorId = authorId;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getSourceSubTitle() {
        return sourceSubTitle;
    }

    public void setSourceSubTitle(String sourceSubTitle) {
        this.sourceSubTitle = sourceSubTitle;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                ", author='" + authorId + '\'' +
                ", sourceTitle='" + sourceTitle + '\'' +
                ", sourceSubTitle='" + sourceSubTitle + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
