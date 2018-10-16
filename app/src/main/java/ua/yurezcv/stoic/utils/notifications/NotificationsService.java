package ua.yurezcv.stoic.utils.notifications;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import ua.yurezcv.stoic.QuotesActivity;
import ua.yurezcv.stoic.R;
import ua.yurezcv.stoic.StoicWisdomApp;
import ua.yurezcv.stoic.data.DataRepository;
import ua.yurezcv.stoic.data.model.QuoteDisplay;
import ua.yurezcv.stoic.utils.threading.AppExecutors;

public class NotificationsService extends IntentService {

    private static final String TAG = "NotificationsService";

    private static final String NOTIFICATION_CHANNEL = "quotes_notification_channel";
    private static final int NOTIFICATION_ID = 111;

    public NotificationsService() {
        super(TAG);
    }

    public static void startService(Context context) {
        Intent service = new Intent(context, NotificationsService.class);
        context.startService(service);
    }

    public static PendingIntent getNotificationServicePendingIntent(Context context) {
        Intent action = new Intent(context, NotificationsService.class);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppExecutors appExecutors = StoicWisdomApp.getExecutors();
        DataRepository repository = DataRepository.getInstance(getApplicationContext(), appExecutors);

        QuoteDisplay quoteDisplay = repository.getRandomQuote();
        showNotification(quoteDisplay);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.quote_channel_name);
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    name,
                    NotificationManager.IMPORTANCE_LOW);
            channel.enableVibration(true);
            channel.enableLights(true);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            if(notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(QuoteDisplay quote) {
        createNotificationChannel();

        Intent startActivityIntent = new Intent(this, QuotesActivity.class);
        PendingIntent launchIntent = PendingIntent.getActivity(this, 0,
                startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder quoteNotificationBuilder
                = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL);

        quoteNotificationBuilder
                .setSmallIcon(R.drawable.ic_sample_author_1)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(launchIntent)
                .setAutoCancel(true)
                .setShowWhen(true);

        quoteNotificationBuilder
                .setContentTitle(quote.getAuthor())
                .setContentText(quote.getQuote()).setStyle(new NotificationCompat.BigTextStyle()
                .bigText(quote.getQuote()));

        NotificationManagerCompat notificationManager
                = NotificationManagerCompat.from(this);

        notificationManager.notify(NOTIFICATION_ID,
                quoteNotificationBuilder.build());
    }

}
