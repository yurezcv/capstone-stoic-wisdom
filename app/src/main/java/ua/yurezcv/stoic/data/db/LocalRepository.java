package ua.yurezcv.stoic.data.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import ua.yurezcv.stoic.data.DataSource;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.Quote;
import ua.yurezcv.stoic.data.model.QuoteDisplay;
import ua.yurezcv.stoic.utils.EmptyLocalDataException;
import ua.yurezcv.stoic.utils.threading.AppExecutors;

public class LocalRepository implements DataSource {

    private final StoicWisdomDatabase mDatabase;
    private final AppExecutors mExecutors;

    public LocalRepository(Context context, AppExecutors executors) {
        this.mDatabase = Room.databaseBuilder(context.getApplicationContext(),
                StoicWisdomDatabase.class, StoicWisdomDatabase.DATABASE_NAME).build();
        this.mExecutors = executors;
    }

    @Override
    public void getAuthors(final GetAuthorsCallback callback) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                if (isEmpty()) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(new EmptyLocalDataException("No data in the database"));
                        }
                    });
                }

                final List<Author> authors = mDatabase.authorDao().getAll();

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(authors);
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getAuthorById(long authorId, GetAuthorCallback callback) {

    }

    @Override
    public void saveAuthors(List<Author> authors) {

    }

    @Override
    public void getQuotes(final GetQuotesCallback callback) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                if (isEmpty()) {
                    mExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(new EmptyLocalDataException("No data in the database"));
                        }
                    });
                }

                List<Quote> quotesDb = mDatabase.quoteDao().getAll();
                List<Author> authors = mDatabase.authorDao().getAll();

                SparseArray<Author> authorMap = createAuthorsMap(authors);

                final List<QuoteDisplay> quotes = new ArrayList<>();
                for (Quote quote : quotesDb) {
                    QuoteDisplay quoteDisplay = toQuoteDisplay(quote,
                            authorMap.get(quote.getAuthorId()).getName());

                    quotes.add(quoteDisplay);
                }

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(quotes);
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public QuoteDisplay getQuoteById(int quoteId) {
        if(quoteId != -1) {
            List<Author> authors = mDatabase.authorDao().getAll();

            SparseArray<Author> authorMap = createAuthorsMap(authors);
            Quote quote = mDatabase.quoteDao().loadById(quoteId);

            return toQuoteDisplay(quote, authorMap.get(quote.getAuthorId()).getName());
        } else {
            return null;
        }
    }

    @Override
    public QuoteDisplay getRandomQuote() {
        List<Author> authors = mDatabase.authorDao().getAll();

        SparseArray<Author> authorMap = createAuthorsMap(authors);
        Quote quote = mDatabase.quoteDao().loadRandom();

        return toQuoteDisplay(quote, authorMap.get(quote.getAuthorId()).getName());
    }

    @Override
    public void markAsFavorite(final int quoteId, final boolean isFavorite) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                mDatabase.quoteDao().markAsFavorite(quoteId, isFavorite);
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveQuotes(List<Quote> quotes) {

    }

    private boolean isEmpty() {
        return mDatabase.authorDao().count() == 0 || mDatabase.quoteDao().count() == 0;
    }

    private QuoteDisplay toQuoteDisplay(Quote quote, String author) {
        return new QuoteDisplay(
                quote.getId(),
                quote.getQuote(),
                author,
                quote.getSource(),
                quote.isFavorite());
    }

    private SparseArray<Author> createAuthorsMap(List<Author> authors) {
        SparseArray<Author> authorMap = new SparseArray<>();

        for (Author author : authors) {
            authorMap.append(author.getId(), author);
        }

        return authorMap;
    }

}
