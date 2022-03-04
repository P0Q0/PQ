package pkg.what.pq

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import pkg.what.pq.databinding.LayoutPqBinding

class ActivityPQ : AppCompatActivity() {

    private lateinit var bind: LayoutPqBinding

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        this.bind = LayoutPqBinding.inflate(layoutInflater).also { setContentView(it.root) }
        Snackbar.make(
            findViewById(R.id.layout_pq)
            ,getString(R.string.feature_title_PQ)
            ,Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.colorLight)
            ).show()
        setListeners()
    }

    private fun setListeners(){
        this.bind.pqA0.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a0)).also { startActivity(it) }
        }
        this.bind.pqA1.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a1)).also { startActivity(it) }
        }
        this.bind.pqA2.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a2)).also { startActivity(it) }
        }
        this.bind.pqA3.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a3)).also { startActivity(it) }
        }
        this.bind.pqA4.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a4)).also { startActivity(it) }
        }
        this.bind.pqA5.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a5)).also { startActivity(it) }
        }
        this.bind.pqA6.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a6)).also { startActivity(it) }
        }
        this.bind.pqA7.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a7)).also { startActivity(it) }
        }
        this.bind.pqA8.setOnClickListener {
            Intent().setClassName(packageName, getString(R.string.class_a8)).also { startActivity(it) }
        }
    }
}