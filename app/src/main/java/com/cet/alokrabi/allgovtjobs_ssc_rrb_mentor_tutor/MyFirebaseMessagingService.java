package com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor.Common.Common;

/**
 * Created by Kamala on 11/15/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        String link = remoteMessage.getData().get("link");
        String message = remoteMessage.getData().get("message");
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        sendNotification(message,link);


        if (link != null ){
            Intent resultIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(link));
           // PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            resultIntent.putExtra("LINK",link);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
                    notificationBuilder.setStyle(bigText);
                    notificationBuilder.setPriority(Notification.PRIORITY_HIGH);


            //notificationIntent.putExtra("path", viewObject.getPath());
            notificationBuilder.setContentTitle("JELET Quiz");
            notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setContentIntent(pendingIntent);
            notificationBuilder.setSound(defaultSoundUri);
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());



        }else {

            Intent pushNotification = new Intent(this, MainActivity.class);
            //pushNotification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           // pushNotification.putExtra("LINK", link);
            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(pushNotification);
            pushNotification.putExtra(Common.STR_PUSH, remoteMessage);
            pushNotification.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, pushNotification, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

            notificationBuilder.setStyle(bigText);
            notificationBuilder.setPriority(NotificationManager.IMPORTANCE_HIGH);
            notificationBuilder.setContentTitle("CET Mentor");
            notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
            notificationBuilder.setAutoCancel(true);

            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setContentIntent(pendingIntent);
            notificationBuilder.setSound(defaultSoundUri);
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());



           /* pushNotification = new Intent(this, MockExam.class);
            pushNotification.putExtra(Common.STR_PUSH,remoteMessage);
            pushNotification.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);*/

            //String title = remoteMessage.getNotification().getTitle();
            //String message = remoteMessage.getNotification().getBody();*/

        }
    }

        //handleMessage(remoteMessage.getData().get(Common.STR_KEY));


    private void sendNotification(String messageBody , String link) {

        /*PendingIntent pendingIntent = PendingIntent.getActivity(this, 0  Request code , intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                //.setLargeIcon(image)/*Notification icon image
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("JELET Quiz")

                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification , notificationBuilder.build());*/
    }

  /*  private void handleMessage(String message) {
        Intent pushNotification = new Intent(Common.STR_PUSH);
        pushNotification.putExtra(Common.STR_MESSAGE,message);
        if (pushNotification.getAction().equals(Common.STR_KEY))
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(message));
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

    }*/

}
