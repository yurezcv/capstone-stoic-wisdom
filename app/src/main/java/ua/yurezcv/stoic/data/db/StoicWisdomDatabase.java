package ua.yurezcv.stoic.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.Quote;

@Database(entities = { Author.class, Quote.class }, version = StoicWisdomDatabase.VERSION)
public abstract class StoicWisdomDatabase extends RoomDatabase {
    public static String DATABASE_NAME = "stoic_wisdom.db";

    static final int VERSION = 1;

    public abstract QuoteDao quoteDao();

    public abstract AuthorDao authorDao();
}
