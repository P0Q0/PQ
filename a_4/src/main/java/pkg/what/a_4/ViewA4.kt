package pkg.what.a_4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R

class ViewA4 : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.layout_a4)
        Snackbar.make(findViewById(R.id.layout_a4),getString(R.string.feature_title_a_4), Snackbar.LENGTH_SHORT).show()
    }
}