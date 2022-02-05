package r.dadashreza.elham

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (BuildConfig.DEBUG) {
            Log.i("*** Elham ***", "ElhamReciever, onReceive")
            Toast.makeText(context, "BOOT_COMPLETED", Toast.LENGTH_LONG).show()
        }

        Elham.scheduleJob(context)
    }
}
