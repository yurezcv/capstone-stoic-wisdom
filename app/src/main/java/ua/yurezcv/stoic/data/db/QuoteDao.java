package ua.yurezcv.stoic.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ua.yurezcv.stoic.data.model.Quote;

@Dao
public interface QuoteDao {

    @Query("SELECT COUNT(*) FROM " + Quote.TABLE_NAME)
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Quote quote);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Quote... quotes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Quote> quotes);

    @Query("SELECT * FROM " + Quote.TABLE_NAME)
    List<Quote> getAll();

    @Query("SELECT * FROM " + Quote.TABLE_NAME + " WHERE " + Quote.COLUMN_ID + " = :quoteId")
    Quote loadById(int quoteId);

    @Query("SELECT * FROM " + Quote.TABLE_NAME + " ORDER BY RANDOM() LIMIT 1")
    Quote loadRandom();

    @Query("SELECT * FROM " + Quote.TABLE_NAME + " WHERE " + Quote.COLUMN_IS_FAVORITE + " = 1")
    List<Quote> loadFavoriteQuotes();

    @Query("UPDATE " + Quote.TABLE_NAME + " SET " + Quote.COLUMN_IS_FAVORITE + "= :isFavorite WHERE " + Quote.COLUMN_ID + " = :id")
    int markAsFavorite(int id, boolean isFavorite);

    @Query("DELETE FROM " + Quote.TABLE_NAME)
    void truncateTable();

    @Update
    int updateQuote(Quote quote);

    @Update
    int updateQuotes(Quote... quotes);

    @Delete
    int deleteQuote(Quote quote);
}
