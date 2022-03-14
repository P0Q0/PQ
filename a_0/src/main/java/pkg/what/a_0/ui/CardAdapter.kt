package pkg.what.a_0.ui

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import pkg.what.a_0.data.model.DataModel
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_CITY
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_EMAIL
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_EMPLOYER
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_ID
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_IMGURL
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_NAME
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_PHONE
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_WEBTKN
import pkg.what.a_0.domain.core.constant.ProfileTags.TAG_ZIP
import pkg.what.a_0.ui.view.ViewDisplay

/** @desc shorten alias with a meaningful representation */
typealias CardHolder = CardAdapter.ViewHolder

class CardAdapter(private val vd: ViewDisplay
    , private val users: List<DataModel.UserModel>
        , private val images: List<Bitmap>) : RecyclerView.Adapter<CardHolder>() {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val card = view.findViewById<MaterialCardView>(pkg.what.pq.R.id.card_view_root_a0)
        private val textView = view.findViewById<MaterialTextView>(pkg.what.pq.R.id.card_tv_a0)
        private val imgView = view.findViewById<ImageView>(pkg.what.pq.R.id.card_img_a0)

        fun bind(position: Int) {
            textView.text = users[position].name
            if(images.isNotEmpty()){ imgView.setImageBitmap(images[position]) }
            else {
                /** @documentation_for_the_future
                 * suppose that the images are not ready due to whatever
                 * this can be adjusted,
                 * 1) make coroutines doing the work faster
                 * 2) , or use an api such as promises to continue execution on deferred objects
                 * 3) , or prefill the data model of images with empty bitmaps
                 * 4) , or use a progress bar to intuitively instruct the user to wait (mh...)
                 */
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(pkg.what.pq.R.layout.card_item_a0, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {

        if(users.isEmpty()){
            println("users.isEmpty()")
        }

        if(images.isEmpty()){
            println("images.isEmpty()")
        }

        holder.bind(position)

        holder.itemView.setOnClickListener {
            val webtkn = users[position].id ?: "- - - - -"
            val id = users[position].id ?: "- - - - -"
            val name = users[position].name ?: "- - - - -"
            val email = users[position].email ?: "- - - - -"
            val phone = users[position].phone ?: "- - - - -"
            val employer = users[position].company?.name ?: "- - - - -"
            val city = users[position].address?.city ?: "- - - - -"
            val zip = users[position].address?.zipcode ?: "- - - - -"
            val navCntrl = vd.findNavController()
            navCntrl.navigate(
                pkg.what.pq.R.id.action_nav_fragment_a0_display_to_nav_fragment_a0_profile
                , bundleOf(TAG_WEBTKN to webtkn
                    , TAG_ID to id
                    , TAG_NAME to name
                    , TAG_EMAIL to email
                    , TAG_PHONE to phone
                    , TAG_EMPLOYER to employer
                    , TAG_CITY to city
                    , TAG_ZIP to zip
                    , TAG_IMGURL to images[position])
            )
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}