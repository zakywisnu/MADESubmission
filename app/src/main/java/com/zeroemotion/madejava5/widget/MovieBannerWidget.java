package com.zeroemotion.madejava5.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.zeroemotion.madejava5.R;

/**
 * Implementation of App Widget functionality.
 */
public class MovieBannerWidget extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.zeroemotion.madejava5.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.zeroemotion.madejava5.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.movie_banner_widget);
        views.setRemoteAdapter(R.id.stack_view,intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view_text);

        Intent toastIntent = new Intent(context,MovieBannerWidget.class);
        toastIntent.setAction(MovieBannerWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,toastIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() != null){
            int viewIndex = intent.getIntExtra(EXTRA_ITEM,0);
            Toast.makeText(context, TOAST_ACTION, Toast.LENGTH_SHORT).show();
//            if (intent.getAction().equals(TOAST_ACTION)){
//                Toast.makeText(context, TOAST_ACTION, Toast.LENGTH_SHORT).show();
//            }
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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
    public static void updateWidget(Context context){
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] widgetId = manager.getAppWidgetIds(new ComponentName(context, MovieBannerWidget.class));
        manager.notifyAppWidgetViewDataChanged(widgetId, R.id.stack_view);
    }
}

