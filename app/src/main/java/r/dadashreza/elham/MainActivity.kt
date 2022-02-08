package r.dadashreza.elham

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import r.dadashreza.elham.databinding.ActivityMainBinding
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        Elham.scheduleJob(applicationContext)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_fal, R.id.nav_help
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        this.splashScreen(binding.imgSplash)
        this.firstTimeNotification()
    }

    private fun firstTimeNotification() {
        val sharedPref = getSharedPreferences("ElhamSharedPref", MODE_PRIVATE)

        if (!sharedPref.getBoolean("isFirstRunDone", false)) {
            Elham.ask(applicationContext, true)

            val sharedPrefEdit = sharedPref.edit()
            sharedPrefEdit.putBoolean("isFirstRunDone", true)
            sharedPrefEdit.apply()
        }
    }

    private fun splashScreen(imgSplash: ImageView) {
        val anim = imgSplash.animate()
        anim.startDelay = 3000
        anim.alpha(0.0F)
        anim.duration = 1000
        anim.withEndAction { binding.drawerLayout.removeView(imgSplash) }
    }

    fun setBg(@DrawableRes id: Int) {
        this.findViewById<DrawerLayout>(R.id.drawer_layout)?.background =
            this.applicationContext?.let { ContextCompat.getDrawable(it, id) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}