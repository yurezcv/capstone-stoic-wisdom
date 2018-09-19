package ua.yurezcv.stoic.data.remote;

import android.content.Context;

import java.util.List;

import ua.yurezcv.stoic.data.DataSource;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.Quote;

public class RemoteRepository implements DataSource {

    public RemoteRepository(Context context) {
    }

    @Override
    public void getAuthors(GetAuthorsCallback callback) {

    }

    @Override
    public void getAuthorById(long authorId, GetAuthorCallback callback) {
        // local implementation only
    }

    @Override
    public void saveAuthors(List<Author> authors) {
        // local implementation only
    }

    @Override
    public List<Quote> getQuotes(GetQuotesCallback callback) {
        return null;
    }

    @Override
    public Quote getQuoteById(long quoteId) {
        // local implementation only
        return null;
    }

    @Override
    public void markQuoteAsFavorite(long quoteId) {
        // local implementation only
    }

    @Override
    public void saveQuotes(List<Quote> quotes) {
        // local implementation only
    }
}
