package pkg.what.a_0

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R

class ViewA0 : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.layout_a0)
        Snackbar.make(
            findViewById(R.id.layout_a0)
            ,getString(R.string.feature_title_a_0)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight)
            ).show()
    }
}