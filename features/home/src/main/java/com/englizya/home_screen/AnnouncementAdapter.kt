package com.englizya.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.home_screen.constants.ImagesConstants
import com.englizya.home_screen.databinding.CardViewAnnouncementBinding
import com.englizya.model.model.Announcement
import com.squareup.picasso.Picasso

class AnnouncementAdapter(
    private var announcements :List<Announcement>,
    private val onItemClicked: (Announcement) -> Unit,
    ) : RecyclerView.Adapter<AnnouncementAdapter.NavigationItemViewHolder>() {

    inner class NavigationItemViewHolder(private val binding: CardViewAnnouncementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(announcement: Announcement) {
            binding.announcementName.text = announcement.announcementTitle
            binding.announcementDetails.text = announcement.announcementDescription
            Picasso.get().load(ImagesConstants.IMAGE_URL + announcement.announcementImageUrl).into(binding.announcementImage)
            itemView.setOnClickListener{
                navigateToAnnouncementDetails(announcement)
            }
        }

        private fun navigateToAnnouncementDetails(announcement: Announcement) {
            onItemClicked(announcement)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NavigationItemViewHolder {
        val binding = CardViewAnnouncementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NavigationItemViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        holder.updateUI(announcements[position])
    }

    override fun getItemCount(): Int {
        return announcements.size
    }

    fun setAnnouncements(list: List<Announcement>) {
        announcements = (list)
        notifyDataSetChanged()
    }
}
