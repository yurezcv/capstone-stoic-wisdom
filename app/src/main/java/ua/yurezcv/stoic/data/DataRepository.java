package ua.yurezcv.stoic.data;

import android.content.Context;

import ua.yurezcv.stoic.data.db.LocalRepository;
import ua.yurezcv.stoic.data.model.QuoteDisplay;
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
        mLocalRepository.getAuthors(callback);
    }

    @Override
    public void getQuotes(int filter, GetQuotesCallback callback) {
        mLocalRepository.getQuotes(filter, callback);
    }

    @Override
    public QuoteDisplay getQuoteById(int quoteId) {
        return mLocalRepository.getQuoteById(quoteId);
    }

    @Override
    public QuoteDisplay getRandomQuote() {
        return mLocalRepository.getRandomQuote();
    }

    @Override
    public void markAsFavorite(int quoteId, boolean isFavorite) {
        mLocalRepository.markAsFavorite(quoteId, isFavorite);
    }
}
