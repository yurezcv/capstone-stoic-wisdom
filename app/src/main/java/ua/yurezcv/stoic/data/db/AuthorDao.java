package ua.yurezcv.stoic.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ua.yurezcv.stoic.data.model.Author;

@Dao
public interface AuthorDao {
    @Query("SELECT COUNT(*) FROM " + Author.TABLE_NAME)
    int count();

    @Query("SELECT * FROM " + Author.TABLE_NAME)
    List<Author> getAll();

    @Query("SELECT * FROM " + Author.TABLE_NAME + " WHERE " + Author.COLUMN_ID + " = :authorId")
    Author loadById(long authorId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Author author);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Author... authors);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Author> authors);

    @Update
    void update(Author... authors);

    @Delete
    void delete(Author... authors);

    @Query("DELETE FROM " + Author.TABLE_NAME)
    void truncateTable();
}
