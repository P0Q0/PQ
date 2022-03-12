package pkg.what.a_0.ui.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ViewNotyDead : AppCompatActivity() {

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        finishAndRemoveTask()
        finishAffinity()
        finish()
    }
}