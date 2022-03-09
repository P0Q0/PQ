package pkg.what.a_0.data.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import pkg.what.a_0.data.io.network.DataSource

/** @desc shorten alias with a meaningful representation */
typealias CardHolder = CardAdapter.ViewHolder

//TODO: CardAdapter, fix it, for now @param is Int but change to @param DataSource
class CardAdapter(private val source: Int) : RecyclerView.Adapter<CardHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val button = view.findViewById<MaterialButton>(pkg.what.pq.R.id.card_btn_a5)

        init {
            setupListeners(view)
        }

        private fun setupListeners(view: View) {
            button.setOnClickListener {
                Snackbar.make(it," button_text=${button.text}", Snackbar.LENGTH_SHORT).show()
            }
        }

        fun bind(s: String) {
            button.text = s
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(pkg.what.pq.R.layout.card_item_a5, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        //holder.bind(source.d[position]) //TODO: CardAdapter, onBindViewHolder
    }

    override fun getItemCount(): Int {
        return 0 //TODO: CardAdapter, getItemCount
        //return source.d.size
    }
}