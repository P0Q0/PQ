package pkg.what.a_5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import pkg.what.a_5.ViewA5.Companion.BUTTON_TOAST_MSG

class CardAdapter(private val source: DataSource) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val button = view.findViewById<MaterialButton>(pkg.what.pq.R.id.card_btn)

        init {
            setupListeners(view)
        }

        private fun setupListeners(view: View) {
            button.setOnClickListener {
                Toast.makeText(view.context,"$BUTTON_TOAST_MSG button_text=${button.text}",Toast.LENGTH_SHORT).show()
            }
        }

        fun bind(s: String) {
            button.text = s
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(pkg.what.pq.R.layout.card_item, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        holder.bind(source.d[position])
    }

    override fun getItemCount(): Int {
        return source.d.size
    }
}