import android.content.Intent
import android.net.Uri

class Utils {
    companion object {
        fun openUrl(url: String): Intent {
            val uri: Uri = Uri.parse(url) // missing 'http://' will cause crashed
            return Intent(Intent.ACTION_VIEW, uri)
        }
    }
}