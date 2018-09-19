package ua.yurezcv.stoic.data.db;

import android.arch.persistence.room.RoomDatabase;

public abstract class StoicWisdomDatabase extends RoomDatabase {
    public static String DATABASE_NAME = "stoic_wisdom.db";

    static final int VERSION = 1;

    public abstract QuoteDao quoteDao();

    public abstract AuthorDao authorDao();
}
