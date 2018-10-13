package ua.yurezcv.stoic.utils.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

public class AlarmsHelper {
    public static void scheduleSingleAlarm(Context context, long alarmTime) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent operation =
                NotificationsService.getNotificationServicePendingIntent(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExact(AlarmManager.RTC, alarmTime, operation);
        } else {
            manager.set(AlarmManager.RTC, alarmTime, operation);
        }
    }

    public static void scheduleRepeatingAlarm(Context context, long alarmTime, long interval) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent operation =
                NotificationsService.getNotificationServicePendingIntent(context);

        manager.setInexactRepeating(AlarmManager.RTC, alarmTime, interval, operation);
    }

    public static void cancelAlarm(Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent operation =
                NotificationsService.getNotificationServicePendingIntent(context);
        manager.cancel(operation);
    }
}
