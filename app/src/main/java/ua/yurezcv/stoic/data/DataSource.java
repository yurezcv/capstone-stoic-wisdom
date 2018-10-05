package ua.yurezcv.stoic.data;

import java.util.List;

import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.Quote;
import ua.yurezcv.stoic.data.model.QuoteDisplay;

public interface DataSource {
    /* Methods and interface callbacks related to Author */
    interface GetAuthorsCallback {
        void onSuccess(List<Author> authors);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

    void getAuthors(GetAuthorsCallback callback);

    interface GetAuthorCallback {
        void onSuccess(Author author);

        void onFailure(Throwable throwable);
    }

    void getAuthorById(long authorId, GetAuthorCallback callback);

    void saveAuthors(List<Author> authors);


    /* Methods and callbacks related to Quotes */
    interface GetQuotesCallback {

        void onSuccess(List<QuoteDisplay> quotes);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

    void getQuotes(GetQuotesCallback callback);

    void getQuoteById(int quoteId);

    void markAsFavorite(int quoteId, boolean isFavorite);

    void saveQuotes(List<Quote> quotes);
}
