package pkg.what.a_0.data.model

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import pkg.what.a_0.ui.view.ViewDisplay

/** @desc shorten alias with a meaningful representation */
typealias CardHolder = CardAdapter.ViewHolder

class CardAdapter( private val vd: ViewDisplay
    , private val users: List<DataModel.UserModel>
        , private val images: List<Bitmap>) : RecyclerView.Adapter<CardHolder>() {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val card = view.findViewById<MaterialCardView>(pkg.what.pq.R.id.card_view_root_a0)
        private val textView = view.findViewById<MaterialTextView>(pkg.what.pq.R.id.card_tv_a0)
        private val imgView = view.findViewById<ImageView>(pkg.what.pq.R.id.card_img_a0)

        init {
            setupListeners()
        }

        private fun setupListeners() {
            card.setOnClickListener {
                val navCntrl = vd.findNavController()
                navCntrl.navigate(pkg.what.pq.R.id.action_nav_fragment_a0_display_to_nav_fragment_a0_profile)
            }
        }

        fun bind(data: DataModel.UserModel) {
            textView.text = data.name
            //imgView.setImageBitmap(bitmap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(pkg.what.pq.R.layout.card_item_a0, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        //TODO: CardAdapter, images[position]
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}