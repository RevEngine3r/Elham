package r.dadashreza.elham

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import android.widget.Toast

class JobService : JobService() {
    override fun onStartJob(jobParameters: JobParameters): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i("*** Elham ***", "Elham JobService Started")
            Toast.makeText(this, "Elham JobService Started", Toast.LENGTH_LONG).show()
        }

        Elham.ask(this.applicationContext)

        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        if (BuildConfig.DEBUG) {
            Log.i("*** Elham ***", "Elham JobService Stopped")
            Toast.makeText(this, "Elham JobService Started", Toast.LENGTH_LONG).show()
        }

        return true
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Log.i("*** Elham ***", "Elham JobService Created")
            Toast.makeText(this, "Elham JobService Created", Toast.LENGTH_LONG).show()
        }
    }
}