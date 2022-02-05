package r.dadashreza.elham

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import kotlin.random.Random


class Elham {
    companion object {
        private var notificationId = 1
        private var isChannelCreated = false
        private const val CHANNEL_ID = "elham_msg_channel"
        private val probabilityNumbers = arrayOf(8, 16, 23)
        private lateinit var elhams: List<String>

        private fun rand(start: Int, end: Int): Int {
            require(start <= end) { "Illegal Argument" }
            val rand = Random(System.nanoTime())
            return (start..end).random(rand)
        }

        private fun loadElhams(appContext: Context) {
            if (!this::elhams.isInitialized)
                this.elhams = appContext.assets.open("elhams.txt")
                    .reader()
                    .readText()
                    .split('#')
        }

        fun getElham(i: Long, appContext: Context): String {
            this.loadElhams(appContext)

            return this.elhams[(i % (this.elhams.size - 1)).toInt()]
        }

        fun getRandElham(appContext: Context): String {
            this.loadElhams(appContext)

            return this.getElham(this.rand(0, this.elhams.size - 1).toLong(), appContext)
        }


        private fun createNotificationChannel(appContext: Context) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = appContext.getString(R.string.app_name)
                val descriptionText = appContext.getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }


        private fun decide(): Boolean {
            //return true

            val randInt = rand(1, 24)

            if (BuildConfig.DEBUG) {
                Log.i("*** Elham ***", "deciding")
                Log.i("*** Elham ***", "rand: $randInt")
            }

            if (randInt in probabilityNumbers)
                return true
            return false
        }

        fun ask(appContext: Context): Boolean {
            if (!isChannelCreated) {
                createNotificationChannel(appContext)
                isChannelCreated = true
            }

            if (!decide())
                return false

            val bundle = Bundle()
            bundle.putBoolean("isRand", true)

            val pendingIntent =
                NavDeepLinkBuilder(appContext)
                    .setComponentName(MainActivity::class.java)
                    .setGraph(R.navigation.mobile_navigation)
                    .setDestination(R.id.nav_home)
                    .setArguments(bundle)
                    .createPendingIntent()

            val builder =
                NotificationCompat.Builder(appContext, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_stat_icon_ntfcn_light)
                    .setContentTitle(appContext.getText(R.string.app_name))
                    .setContentText(appContext.getText(R.string.content_text))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)

            if (BuildConfig.DEBUG) {
                Log.i("*** Elham ***", "notificationId, $notificationId")
            }

            NotificationManagerCompat
                .from(appContext)
                .notify(notificationId++, builder.build())

            return true
        }

        private fun isJobSchedulerRunning(context: Context): Boolean {
            val jobScheduler =
                context.getSystemService(AppCompatActivity.JOB_SCHEDULER_SERVICE) as JobScheduler
            return jobScheduler.allPendingJobs.size > 0
        }

        fun scheduleJob(context: Context) {
            if (isJobSchedulerRunning(context)) {
                if (BuildConfig.DEBUG) {
                    Log.i("*** Elham ***", "Job already scheduled.")
                    Toast.makeText(context, "Job already scheduled.", Toast.LENGTH_LONG).show()
                }
                return
            }

            val jobScheduler = context
                .getSystemService(AppCompatActivity.JOB_SCHEDULER_SERVICE) as JobScheduler
            val componentName = ComponentName(
                context,
                JobService::class.java
            )
            val jobInfo = JobInfo.Builder(1, componentName)
                .setPeriodic(
                    1000 * 60 * 40
                )
                .setPersisted(true).build()
            jobScheduler.schedule(jobInfo)
        }
    }
}