package ua.yurezcv.stoic.data.remote;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ua.yurezcv.stoic.data.db.StoicWisdomDatabase;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.QuoteJson;

public class FetchDataService extends IntentService {

    public static final String KEY_REQUEST = "request";

    public static final int CODE_QUOTES = 0;
    public static final int CODE_AUTHORS = 1;

    private static final String TAG = "FetchDataService";

    private RemoteHttpClient mHttpClient;
    private StoicWisdomDatabase mDatabase;

    public static Intent createIntent(Context context) {
        return new Intent(context, FetchDataService.class);
    }

    public FetchDataService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHttpClient = new RemoteHttpClient();
        mDatabase = Room.databaseBuilder(getApplicationContext(),
                StoicWisdomDatabase.class, StoicWisdomDatabase.DATABASE_NAME).build();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(!isOnline()) {
            return;
        }

        int code = intent.getIntExtra(KEY_REQUEST, CODE_QUOTES);

        switch (code) {
            case CODE_QUOTES:
                List<QuoteJson> responseQuotes = fetchQuotes();
                // List<Quote> quotes = new ArrayList<>();

                // Log.d(TAG, responseQuotes.toString());
                // mDatabase.quoteDao().truncateTable();

                for (QuoteJson quoteJson: responseQuotes) {
                    // quotes.add(quoteJson.toQuote());
                    mDatabase.quoteDao().insert(quoteJson.toQuote());
                }

                Log.d(TAG, "count in quotes table = " + mDatabase.quoteDao().count());

                // mDatabase.quoteDao().insertList(quotes);
                break;
            case CODE_AUTHORS:
                List<Author> authors = fetchAuthors();

                // Log.d(TAG, authors.toString());
                // mDatabase.authorDao().truncateTable();

                /*for (Author author: authors) {
                    mDatabase.authorDao().insert(author);
                }*/

                mDatabase.authorDao().insert(authors);

                Log.d(TAG, "count in authors table = " + mDatabase.authorDao().count());
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        mDatabase.close();
        super.onDestroy();
    }

    private List<QuoteJson> fetchQuotes() {
        try {
            return mHttpClient.getQuotes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private List<Author> fetchAuthors() {
        try {
            return mHttpClient.getAuthors();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private boolean isOnline() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = manager.getActiveNetworkInfo() != null;

        return isNetworkAvailable && manager.getActiveNetworkInfo().isConnected();
    }
}
