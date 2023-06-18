package com.assesment2.barangid.ui.barang

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.assesment2.barangid.R
import com.assesment2.barangid.databinding.ListBarangBinding
import com.assesment2.barangid.network.BarangApi
import com.assesment2.barangid.network.BarangApiService
import com.bumptech.glide.Glide

class BarangAdapter : RecyclerView.Adapter<BarangAdapter.ViewHolder>() {

    private val data = mutableListOf<DataBarang>()
    fun updateData(newData: List<DataBarang>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ListBarangBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(barang: DataBarang) = with(binding) {
            tvBarang.text = barang.namaBarang
            tvInggris.text = barang.namaInggris
            Glide.with(imgBarang.context)
                .load(BarangApiService.getBarangUrl(barang.imageId))
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imgBarang)

            root.setOnClickListener {
                val message = root.context.getString(R.string.message, barang.namaBarang)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListBarangBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}