package pkg.what.a_5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R

class ViewA5 : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.layout_a5)
        Snackbar.make(
            findViewById(R.id.layout_a5)
            ,getString(R.string.feature_title_a_5)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight)
            ).show()
    }
}