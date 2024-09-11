package com.incirkus.connect.ADAPTER

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.incirkus.connect.DATA.Model.Holiday
import com.incirkus.connect.R
import com.incirkus.connect.databinding.HolidayListElementBinding

class HolidayAdapter(private var holidayList: List<Holiday>) :
    RecyclerView.Adapter<HolidayAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: HolidayListElementBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            HolidayListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        if (holiday.holidayRegion.toString().endsWith(",")) {
            binding.tvRegion.text = holiday.holidayRegion.toString()
                .replace("bw", " BW")
                .replace("by", " BY")
                .replace("be", " BE")
                .replace("bb", " BB")
                .replace("hb", " HB")
                .replace("hh", " HH")
                .replace("he", " HE")
                .replace("mv", " MV")
                .replace("ni", " NI")
                .replace("nw", " NW")
                .replace("rp", " RP")
                .replace("sl", " SL")
                .replace("sn", " SN")
                .replace("st", " ST")
                .replace("sh", " SH")
                .replace("th", " TH")
                .removeSuffix(",")
        } else {
            binding.tvRegion.text = holiday.holidayRegion.toString().replace("DE", "Bundesweit")
        }


        binding.tvHolidayDescription.visibility = View.GONE
        binding.btnHolidayDescription.visibility = View.INVISIBLE

        if (holiday.comment != "") {
            binding.btnHolidayDescription.visibility = View.VISIBLE
        }

        binding.btnHolidayDescription.setOnClickListener {
            if (binding.tvHolidayDescription.visibility == View.GONE) {
                binding.btnHolidayDescription.setImageResource(R.drawable.baseline_arrow_drop_up_24)
                binding.tvHolidayDescription.text = holiday.comment
                binding.tvHolidayDescription.visibility = View.VISIBLE
            } else {
                binding.btnHolidayDescription.setImageResource(R.drawable.baseline_arrow_drop_down_24)
                binding.tvHolidayDescription.visibility = View.GONE
            }
        }
    }
}
