package ua.yurezcv.stoic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;

import ua.yurezcv.stoic.data.DataRepository;
import ua.yurezcv.stoic.data.model.QuoteDisplay;
import ua.yurezcv.stoic.data.remote.FetchDataService;
import ua.yurezcv.stoic.ui.quotes.QuotesFragment;
import ua.yurezcv.stoic.utils.Analytics;
import ua.yurezcv.stoic.utils.notifications.NotificationsService;
import ua.yurezcv.stoic.utils.threading.AppExecutors;

public class QuotesActivity extends AppCompatActivity implements QuotesFragment.OnQuotesFragmentInteractionListener {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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
            case R.id.menu_show_notification:
                NotificationsService.startService(this);
                break;
        }
        return true;
    }

    @Override
    public void onQuoteLike(int quoteId, boolean isInFavorites) {
        AppExecutors appExecutors = StoicWisdomApp.getExecutors();
        DataRepository repository = DataRepository.getInstance(getApplicationContext(), appExecutors);
        repository.markAsFavorite(quoteId, isInFavorites);

        if(isInFavorites) {
            // log liked quotes ids
            Bundle analyticsBundle = new Bundle();
            analyticsBundle.putInt(FirebaseAnalytics.Param.ITEM_ID, quoteId);
            mFirebaseAnalytics.logEvent(Analytics.EVENT_LIKE, analyticsBundle);
        }
    }

    @Override
    public void onQuoteShare(QuoteDisplay quoteDisplay) {
        shareTheQuote(quoteDisplay);
    }

    private void shareTheQuote(QuoteDisplay quoteDisplay) {
        // log shared quotes ids
        Bundle analyticsBundle = new Bundle();
        analyticsBundle.putInt(FirebaseAnalytics.Param.ITEM_ID, quoteDisplay.getId());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, analyticsBundle);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(quoteDisplay.getSharableContent()));
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.sharable_title)));
    }
}
