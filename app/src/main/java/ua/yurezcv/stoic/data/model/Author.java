package ua.yurezcv.stoic.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = Author.TABLE_NAME)
public class Author implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.yearsOfLive);
        dest.writeString(this.bio);
        dest.writeString(this.wikiLink);
    }

    public Author() {
    }

    protected Author(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.yearsOfLive = in.readString();
        this.bio = in.readString();
        this.wikiLink = in.readString();
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel source) {
            return new Author(source);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}
