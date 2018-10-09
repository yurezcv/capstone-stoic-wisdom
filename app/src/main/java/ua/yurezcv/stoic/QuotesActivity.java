package ua.yurezcv.stoic;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.concurrent.Executors;

import ua.yurezcv.stoic.data.DataRepository;
import ua.yurezcv.stoic.data.db.StoicWisdomDatabase;
import ua.yurezcv.stoic.data.model.Quote;
import ua.yurezcv.stoic.data.model.QuoteDisplay;
import ua.yurezcv.stoic.data.remote.FetchDataService;
import ua.yurezcv.stoic.ui.quotes.QuotesFragment;
import ua.yurezcv.stoic.utils.threading.AppExecutors;
import ua.yurezcv.stoic.utils.threading.DiskIOThreadExecutor;

public class QuotesActivity extends AppCompatActivity implements QuotesFragment.OnQuotesFragmentInteractionListener {

    private static final String TAG = "QuotesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_quotes);
        setContentView(R.layout.activity_fragment_holder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_authors:
                startActivity(AuthorsActivity.createIntent(this));
                break;
            case R.id.menu_item_setting:
                startActivity(SettingsActivity.createIntent(this));
                break;
        }
        return true;
    }

    @Override
    public void onQuoteLike(int quoteId, boolean isInFavorites) {
        AppExecutors appExecutors = StoicWisdomApp.getExecutors();
        DataRepository repository = DataRepository.getInstance(getApplicationContext(), appExecutors);
        repository.markAsFavorite(quoteId, isInFavorites);
    }

    @Override
    public void onQuoteShare(QuoteDisplay quoteDisplay) {
        shareTheQuote(quoteDisplay);
    }

    public void checkCount(View view) {
        new DatabaseAsync().execute();
    }

    public void fetchQuotes(View view) {
        startFetchService(FetchDataService.CODE_QUOTES);
    }

    public void fetchAuthors(View view) {
        startFetchService(FetchDataService.CODE_AUTHORS);
    }

    private void startFetchService(int code) {
        Intent i = FetchDataService.createIntent(this);
        i.putExtra(FetchDataService.KEY_REQUEST, code);
        startService(i);
    }

    private void shareTheQuote(QuoteDisplay quoteDisplay) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(quoteDisplay.getSharableContent()));
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.sharable_title)));
    }

    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            StoicWisdomDatabase database = Room.databaseBuilder(getApplicationContext(),
                    StoicWisdomDatabase.class, StoicWisdomDatabase.DATABASE_NAME).build();

            Log.d(TAG, "authors table count = " + database.authorDao().count());
            Log.d(TAG, "quotes table count = " + database.quoteDao().count());

            List<Quote> quotes = database.quoteDao().getAll();

            Log.d(TAG, "quote #1 = " + quotes.get(0).getQuote());
            Log.d(TAG, "quote #5 = " + quotes.get(4).getQuote());
            Log.d(TAG, "quote #11 = " + quotes.get(10).getQuote());

            List<Quote> byAuthor = database.quoteDao().findByAuthorId(2);

            for (Quote quote: byAuthor) {
                Log.d(TAG, "quote by author = " + quote.getQuote());
            }

            return null;
        }
    }
}
