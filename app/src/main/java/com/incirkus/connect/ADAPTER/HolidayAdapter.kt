package com.incirkus.connect.ADAPTER

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.incirkus.connect.DATA.Model.Holiday
import com.incirkus.connect.databinding.HolidayListElementBinding


class HolidayAdapter (private var holidayList: List<Holiday>) : RecyclerView.Adapter<HolidayAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: HolidayListElementBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = HolidayListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return holidayList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding
        val holiday = holidayList[position]

        binding.tvHolidayDate.text = "${holiday.holidayDay}.${holiday.holidayMonth}. "
        binding.tvHolidayName.text = holiday.holidayName
        binding.tvRegion.text = holiday.holidayRegion
        if (holiday.comment != ""){
            binding.tvHolidayDescription.text = holiday.comment
        }else{
            binding.tvHolidayDescription.isGone = true
        }
        if (holiday.augsburg){
            binding.cbAugsburg.isActivated = true
        }else{
            binding.cbAugsburg.isGone = true
        }
        if (holiday.katholisch){
            binding.cbAugsburg.isActivated = true
        }else{
            binding.cbKatholisch.isGone = true
        }

    }
}
