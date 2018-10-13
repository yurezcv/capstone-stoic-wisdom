package ua.yurezcv.stoic.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ua.yurezcv.stoic.R;
import ua.yurezcv.stoic.StoicWisdomApp;
import ua.yurezcv.stoic.data.DataRepository;
import ua.yurezcv.stoic.data.model.QuoteDisplay;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link QuoteWidgetConfigureActivity QuoteWidgetConfigureActivity}
 */
public class QuoteWidget extends AppWidgetProvider {

    public static final String QUOTE_WIDGET_BROADCAST = "ua.yurezcv.stoic.widget.QUOTE_WIDGET_BROADCAST";

    private static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                        final int[] appWidgetIds, final PendingResult pendingResult) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                DataRepository repository =
                        DataRepository.getInstance(context, StoicWisdomApp.getExecutors());

                for (int appWidgetId : appWidgetIds) {
                    int quoteId = QuoteWidgetConfigureActivity.loadQuoteWidgetPref(context, appWidgetId);
                    QuoteDisplay quote = repository.getQuoteById(quoteId);
                    if (quote != null) {
                        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quote_widget);
                        views.setTextViewText(R.id.tv_widget_quote, quote.getQuote());
                        views.setTextViewText(R.id.tv_widget_author, quote.getAuthor());
                        views.setTextViewText(R.id.tv_widget_source, quote.getSource());
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    }
                }

                if (pendingResult != null) {
                    pendingResult.finish();
                }
            }
        };

        thread.start();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (QUOTE_WIDGET_BROADCAST.equals(intent.getAction())) {
            PendingResult pendingResult = goAsync();

            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            ComponentName quoteWidget = new ComponentName(context, QuoteWidget.class);
            int[] appWidgetIds = manager.getAppWidgetIds(quoteWidget);

            updateAppWidget(context, manager, appWidgetIds, pendingResult);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        PendingResult pendingResult = goAsync();
        updateAppWidget(context, appWidgetManager, appWidgetIds, pendingResult);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            QuoteWidgetConfigureActivity.deleteQuoteWidgetPref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

