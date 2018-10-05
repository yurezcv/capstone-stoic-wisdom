package ua.yurezcv.stoic.data;

import android.content.Context;

import java.util.List;

import ua.yurezcv.stoic.data.db.LocalRepository;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.Quote;
import ua.yurezcv.stoic.utils.threading.AppExecutors;

public class DataRepository implements DataSource {

    private static volatile DataRepository sInstance;

    private final LocalRepository mLocalRepository;

    private DataRepository(Context context, AppExecutors executors) {
        this.mLocalRepository = new LocalRepository(context, executors);
    }

    public static DataRepository getInstance(Context context, AppExecutors executors) {
        // making sInstance thread safe
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) sInstance = new DataRepository(context, executors);
            }
        }

        return sInstance;
    }

    @Override
    public void getAuthors(GetAuthorsCallback callback) {

    }

    @Override
    public void getAuthorById(long authorId, GetAuthorCallback callback) {

    }

    @Override
    public void saveAuthors(List<Author> authors) {

    }

    @Override
    public void getQuotes(GetQuotesCallback callback) {
        mLocalRepository.getQuotes(callback);
    }

    @Override
    public void getQuoteById(int quoteId) {

    }

    @Override
    public void markAsFavorite(int quoteId, boolean isFavorite) {
        mLocalRepository.markAsFavorite(quoteId, isFavorite);
    }

    @Override
    public void saveQuotes(List<Quote> quotes) {

    }
}
