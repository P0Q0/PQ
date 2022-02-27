package pkg.what.pq

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_pq.*

class ActivityPQ : AppCompatActivity() {

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.layout_pq)
        Snackbar.make(findViewById(R.id.layout_pq),getString(R.string.feature_title_PQ),Snackbar.LENGTH_SHORT).show()

        setListeners()
    }

    private fun setListeners(){
        pq_a0.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a0)).also { startActivity(it) }
        }
        pq_a1.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a1)).also { startActivity(it) }
        }
        pq_a2.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a2)).also { startActivity(it) }
        }
        pq_a3.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a3)).also { startActivity(it) }
        }
        pq_a4.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a4)).also { startActivity(it) }
        }
        pq_a5.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a5)).also { startActivity(it) }
        }
        pq_a6.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a6)).also { startActivity(it) }
        }

    }
}