package ua.yurezcv.stoic.data.db;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import ua.yurezcv.stoic.data.model.Author;

public interface AuthorDao {
    @Query("SELECT COUNT(*) FROM " + Author.TABLE_NAME)
    int count();

    @Query("SELECT * FROM " + Author.TABLE_NAME + " WHERE " + Author.COLUMN_ID + " = :authorId")
    Author loadById(long authorId);

    @Insert
    void insert(Author... authors);

    @Update
    void update(Author... authors);

    @Delete
    void delete(Author... authors);

    @Query("DELETE FROM " + Author.TABLE_NAME)
    void truncateTable();
}
