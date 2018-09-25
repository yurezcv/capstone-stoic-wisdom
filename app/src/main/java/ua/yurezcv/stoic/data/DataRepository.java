package ua.yurezcv.stoic.data;

import android.content.Context;

import java.util.List;

import ua.yurezcv.stoic.data.db.LocalRepository;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.Quote;

public class DataRepository implements DataSource {

    private static volatile DataRepository sInstance;

    private final LocalRepository mLocalRepository;
    // TODO add a proper remote repository
    // private final RemoteRepository mRemoteRepository;

    public DataRepository(Context context) {
        this.mLocalRepository = new LocalRepository();
    }

    public static DataRepository getInstance(Context context) {
        // making sInstance thread safe
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) sInstance = new DataRepository(context);
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
    public List<Quote> getQuotes(GetQuotesCallback callback) {
        // TODO finish this method and populate the fragment
        return null;
    }

    @Override
    public Quote getQuoteById(long quoteId) {
        return null;
    }

    @Override
    public void markQuoteAsFavorite(long quoteId) {

    }

    @Override
    public void saveQuotes(List<Quote> quotes) {

    }
}
