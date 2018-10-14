package ua.yurezcv.stoic.data;

import java.util.List;

import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.QuoteDisplay;

public interface DataSource {

    public static final int FILTER_ALL = 0;
    public static final int FILTER_FAVORITES = 1;

    /* Methods and interface callbacks related to Author */
    interface GetAuthorsCallback {
        void onSuccess(List<Author> authors);
        void onFailure(Throwable throwable);
    }

    void getAuthors(GetAuthorsCallback callback);

    /* Methods and callbacks related to Quotes */
    interface GetQuotesCallback {
        void onSuccess(List<QuoteDisplay> quotes);
        void onFailure(Throwable throwable);
    }

    void getQuotes(int filter, GetQuotesCallback callback);

    QuoteDisplay getQuoteById(int quoteId);

    QuoteDisplay getRandomQuote();

    void markAsFavorite(int quoteId, boolean isFavorite);
}
