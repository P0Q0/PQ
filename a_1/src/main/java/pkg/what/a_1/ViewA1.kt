package pkg.what.a_1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.R

class ViewA1 : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.layout_a1)
        Snackbar.make(findViewById(R.id.layout_a1),getString(R.string.feature_title_a_1), Snackbar.LENGTH_SHORT).show()
    }
}