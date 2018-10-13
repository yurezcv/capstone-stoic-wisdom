package ua.yurezcv.stoic.widget;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.yurezcv.stoic.R;
import ua.yurezcv.stoic.data.model.QuoteDisplay;
import ua.yurezcv.stoic.ui.quotes.QuotesViewModel;

/**
 * The configuration screen for the {@link QuoteWidget QuoteWidget} AppWidget.
 */
public class QuoteWidgetConfigureActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "ua.yurezcv.stoic.widget.QuoteWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    // UI elements
    @BindView(R.id.rv_widget_quotes)
    RecyclerView mQuotesRecycleView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProgressBar;

    @BindView(R.id.tv_error_message)
    TextView mErrorTextView;

    private QuotesWidgetAdapter mAdapter;

    public QuoteWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveQuoteWidgetPref(Context context, int appWidgetId, int quoteId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId, quoteId);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static int loadQuoteWidgetPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getInt(PREF_PREFIX_KEY + appWidgetId, -1);
    }

    static void deleteQuoteWidgetPref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.quote_widget_configure);
        ButterKnife.bind(this);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        setupViewModel();

        mAdapter = new QuotesWidgetAdapter(new ArrayList<QuoteDisplay>());

        // init the recycler view
        mQuotesRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mQuotesRecycleView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_widget_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_widget:
                if (mAdapter.getSelectedQuoteId() != RecyclerView.NO_POSITION) {
                    saveQuoteWidgetPref(getApplicationContext(), mAppWidgetId, mAdapter.getSelectedQuoteId());

                    // send a broadcast to update a widget after the first init
                    Intent broadcast = new Intent(this, QuoteWidget.class);
                    broadcast.setAction(QuoteWidget.QUOTE_WIDGET_BROADCAST);
                    sendBroadcast(broadcast);

                    // Make sure we pass back the original appWidgetId
                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    setResult(RESULT_OK, resultValue);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.error_widget_no_selection,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    private void setupViewModel() {
        QuotesViewModel viewModel = ViewModelProviders.of(this).get(QuotesViewModel.class);
        viewModel.getQuotes().observe(this, new Observer<List<QuoteDisplay>>() {

            @Override
            public void onChanged(@Nullable List<QuoteDisplay> quoteList) {

                if (quoteList != null) {
                    mAdapter.setData(quoteList);
                    hideProgressBar();
                } else {
                    // show an error
                    Log.d("WidgetConfigActivity", "quotes list is empty");
                }

            }
        });
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mQuotesRecycleView.setVisibility(View.VISIBLE);
    }
}

