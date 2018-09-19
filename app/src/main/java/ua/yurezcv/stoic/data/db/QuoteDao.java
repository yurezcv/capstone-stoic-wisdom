package ua.yurezcv.stoic.data.db;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ua.yurezcv.stoic.data.model.Quote;

public interface QuoteDao {

    @Query("SELECT COUNT(*) FROM " + Quote.TABLE_NAME)
    int count();

    @Insert
    void insertMultipleQuotess(Quote... quotes);

    @Insert
    void insertListOfQuotes(List<Quote> quotes);

    @Insert
    void insertOnlySingleRecord(Quote quote);

    @Query("SELECT * FROM " + Quote.TABLE_NAME)
    List<Quote> getAllQuotes();

    @Query("SELECT * FROM " + Quote.TABLE_NAME + " WHERE " + Quote.COLUMN_ID + " = :quoteId")
    Quote loadById(int quoteId);

    @Query("SELECT * FROM " + Quote.TABLE_NAME + " WHERE " + Quote.COLUMN_ID + " IN (:quoteIds)")
    List<Quote> loadAllByIds(int[] quoteIds);

    @Query("SELECT * FROM " + Quote.TABLE_NAME + " WHERE " + Quote.COLUMN_AUTHOR + " = :authorId")
    List<Quote> findByAuthorId(long authorId);

    @Query("SELECT * FROM " + Quote.TABLE_NAME + " WHERE " + Quote.COLUMN_IS_FAVORITE + " = 1")
    List<Quote> loadFavoriteQuotes();

    @Query("DELETE FROM " + Quote.TABLE_NAME)
    void truncateTable();

    @Update
    int updateQuote(Quote quote);

    @Update
    int updateQuotes(Quote... quotes);

    @Delete
    int deleteQuote(Quote quote);
}