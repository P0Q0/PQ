package pkg.what.a_3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R

class ViewA3 : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.layout_a3)
        Snackbar.make(
            findViewById(R.id.layout_a3)
            ,getString(R.string.feature_title_a_3)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight)
            ).show()
    }
}