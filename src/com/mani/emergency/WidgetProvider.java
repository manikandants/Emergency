package com.mani.emergency;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider{
	public static String ACTION_SEND_SMS = "SMS";
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.e("Tag", "onDeleted");
	}
	@Override
	public void onDisabled(Context context) {
		Log.e("Tag", "onDisabled");
	}
	@Override
	public void onEnabled(Context context) {
		Log.e("Tag", "onEnabled");
	}
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.e("Tag", "onUpdate");
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
	    Intent configIntent = new Intent(context, WidgetProvider.class);
	    configIntent.setAction(WidgetProvider.ACTION_SEND_SMS);
	    PendingIntent configPendingIntent = PendingIntent.getBroadcast(context, 0, configIntent, 0);
	    remoteViews.setOnClickPendingIntent(R.id.widgetbutton, configPendingIntent);
	    appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction() == WidgetProvider.ACTION_SEND_SMS){
			Log.e("Tag", "onReceive");
			Intent smsIntent = new Intent();
			smsIntent.setClassName("com.mani.emergency", "com.mani.emergency.GPSActivity");
			smsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(smsIntent);
		}
		super.onReceive(context, intent);
	}
}
