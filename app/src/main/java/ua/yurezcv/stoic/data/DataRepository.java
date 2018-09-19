package ua.yurezcv.stoic.data;

import java.util.List;

import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.Quote;

public class DataRepository implements DataSource {

    private static final String LOG = DataRepository.class.getSimpleName();

    private final DataSource mLocalData;

    public DataRepository(DataSource mLocalData) {
        this.mLocalData = mLocalData;
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
