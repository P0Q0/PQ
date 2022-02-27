package pkg.what.a_6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R

class ViewA6 : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.layout_a6)
        Snackbar.make(findViewById(R.id.layout_a6),getString(R.string.feature_title_a_6), Snackbar.LENGTH_SHORT).show()
    }
}