package ua.yurezcv.stoic.data.remote;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.QuoteJson;

public class FetchDataService extends IntentService {

    public static final String KEY_REQUEST = "request";

    public static final int CODE_QUOTES = 0;
    public static final int CODE_AUTHORS = 1;

    private static final String TAG = "FetchDataService";

    private RemoteHttpClient mHttpClient;

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
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(!isOnline()) {
            return;
        }

        int code = intent.getIntExtra(KEY_REQUEST, CODE_QUOTES);

        switch (code) {
            case CODE_QUOTES:
                fetchQuotes();
                break;
            case CODE_AUTHORS:
                fetchAuthors();
                break;
            default:
                break;
        }
    }

    private List<QuoteJson> fetchQuotes() {
        try {
            List<QuoteJson> quotes =  mHttpClient.getQuotes();
            Log.d(TAG, quotes.toString());

            return quotes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void fetchAuthors() {
        try {
            List<Author> authors =  mHttpClient.getAuthors();
            Log.d(TAG, authors.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = manager.getActiveNetworkInfo() != null;

        return isNetworkAvailable && manager.getActiveNetworkInfo().isConnected();
    }
}
