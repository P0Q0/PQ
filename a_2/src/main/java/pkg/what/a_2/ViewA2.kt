package pkg.what.a_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R

class ViewA2 : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.layout_a2)
        Snackbar.make(
            findViewById(R.id.layout_a2)
            ,getString(R.string.feature_title_a_2)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight)
            ).show()
    }
}