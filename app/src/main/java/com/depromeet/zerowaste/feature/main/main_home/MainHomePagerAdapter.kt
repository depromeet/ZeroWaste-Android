package com.depromeet.zerowaste.feature.main.main_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.data.mission.Mission
import com.depromeet.zerowaste.databinding.ItemMainHomeMyMissionBinding


class MainHomePagerAdapter(vm: MainHomeViewModel) :
    RecyclerView.Adapter<MainHomePagerAdapter.ViewHolder>() {
    private val items = ArrayList<Mission>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMainHomeMyMissionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_main_home_my_mission,
            parent,
            false
        )
        return ViewHolder(binding).apply {
            binding.mainHomeMyMissionBtnAuth.setOnClickListener { view ->
                val position = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                //vm.request(items{position])
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {
            holder.itemView
        }

    }

    fun addItems(items: List<Mission>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMainHomeMyMissionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Mission) {
            binding.mission = item
            binding.executePendingBindings()
        }
    }
}