package ua.yurezcv.stoic.data.remote;

import android.app.Activity;
import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ua.yurezcv.stoic.R;
import ua.yurezcv.stoic.data.db.StoicWisdomDatabase;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.QuoteJson;

public class FetchDataService extends IntentService {

    public static final String KEY_REQUEST = "request";
    public static final String KEY_RECEIVER = "receiver";

    public static final int CODE_QUOTES = 0;
    public static final int CODE_AUTHORS = 1;
    public static final String KEY_ERROR_MESSAGE = "key_error_message";

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
        int code = intent.getIntExtra(KEY_REQUEST, CODE_QUOTES);

        switch (code) {
            case CODE_QUOTES:
                ResultReceiver rec = intent.getParcelableExtra(KEY_RECEIVER);
                if(!isOnline() && rec != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(KEY_ERROR_MESSAGE, getString(R.string.error_no_connection));
                    rec.send(Activity.RESULT_CANCELED, bundle);

                    return;
                }

                List<QuoteJson> responseQuotes = fetchQuotes();

                for (QuoteJson quoteJson: responseQuotes) {
                    mDatabase.quoteDao().insert(quoteJson.toQuote());
                }

                if(rec != null) {
                    rec.send(Activity.RESULT_OK, null);
                }

                break;
            case CODE_AUTHORS:
                if(!isOnline()) {
                    return;
                }
                List<Author> authors = fetchAuthors();
                mDatabase.authorDao().insert(authors);
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
