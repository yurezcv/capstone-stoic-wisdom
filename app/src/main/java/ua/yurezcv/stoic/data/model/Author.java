package ua.yurezcv.stoic.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = Author.TABLE_NAME)
public class Author {
    public static final String TABLE_NAME = "authors";

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_YEARS_OF_LIVE = "years_of_live";
    public static final String COLUMN_BIO = "bio";
    public static final String COLUMN_WIKI_LINK = "wiki_link";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = COLUMN_NAME)
    @SerializedName(COLUMN_NAME)
    private String name;

    @ColumnInfo(name = COLUMN_YEARS_OF_LIVE)
    @SerializedName(COLUMN_YEARS_OF_LIVE)
    private String yearsOfLive;

    @ColumnInfo(name = COLUMN_BIO)
    @SerializedName(COLUMN_BIO)
    private String bio;

    @ColumnInfo(name = COLUMN_WIKI_LINK)
    @SerializedName(COLUMN_WIKI_LINK)
    private String wikiLink;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYearsOfLive(String yearsOfLive) {
        this.yearsOfLive = yearsOfLive;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYearsOfLive() {
        return yearsOfLive;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearsOfLive='" + yearsOfLive + '\'' +
                ", bio='" + bio + '\'' +
                ", wikiLink='" + wikiLink + '\'' +
                '}';
    }
}
