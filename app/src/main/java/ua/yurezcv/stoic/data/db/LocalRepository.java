package ua.yurezcv.stoic.data.db;

import java.util.List;

import ua.yurezcv.stoic.data.DataSource;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.Quote;

public class LocalRepository implements DataSource {

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
