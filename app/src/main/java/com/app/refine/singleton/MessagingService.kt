package com.app.refine.singleton

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.app.refine.R.drawable.ic_circle
import com.app.refine.ui.SplashActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {

    private val TAG = "fmsg_serv_tager"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "onMessageReceived: ")

        setChannel(this)

        processNotification(remoteMessage)
    }

    private fun processNotification(remoteMessage: RemoteMessage) {
        var title = ""
        var body = ""
        var image = ""
        var open = ""
        var articleId = ""

        if (remoteMessage.data.isNotEmpty()) {
            remoteMessage.data.apply {
                title = get("title") ?: ""
                body = get("body") ?: ""
                image = get("image") ?: ""
                open = get("open") ?: ""
                articleId = get("articleId") ?: ""
            }
        } else {
            title = remoteMessage.notification?.title ?: ""
            body = remoteMessage.notification?.body ?: ""
            image = remoteMessage.notification?.imageUrl.toString() ?: ""
        }

        loadImageNotification(title, body, image, open, articleId)

        Log.d(TAG, "processNotification: $title")
        Log.d(TAG, "processNotification: $body")
        Log.d(TAG, "processNotification: $image")
        Log.d(TAG, "processNotification: $open")
        Log.d(TAG, "processNotification: $articleId")

    }

    private fun loadImageNotification(
        title: String,
        body: String,
        image: String,
        open: String,
        articleId: String,
    ) {
        if (image.isNotEmpty() && image.isNotBlank()) {
            Glide.with(this).load(image).addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    val bitmap: Bitmap = (resource as BitmapDrawable).bitmap
                    showNotification(title, body, bitmap, open, articleId)
                    return false
                }

            }).submit()
        } else {
            showNotification(title, body, null, open, articleId)
        }

    }

    private fun showNotification(
        title: String,
        body: String,
        bitmap: Bitmap?,
        open: String,
        articleId: String,
    ) {
        val intent = Intent(this, SplashActivity::class.java)
        intent.putExtra("open", open)
        intent.putExtra("articleId", articleId)
        val pendingIntent = PendingIntent.getActivity(this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "notificationChannel")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(ic_circle)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        if (bitmap != null) {
            val largeIcon =
                (ContextCompat.getDrawable(this, ic_circle) as BitmapDrawable).bitmap
            builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap)
                .bigLargeIcon(largeIcon))
        }

        val manager = NotificationManagerCompat.from(this)
        manager.notify(1, builder.build())
    }

    private fun setChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("notificationChannel",
                "notificationChannel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = context.getSystemService(
                NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}