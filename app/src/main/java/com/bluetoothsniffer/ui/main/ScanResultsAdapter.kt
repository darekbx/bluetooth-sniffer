package com.bluetoothsniffer.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bluetoothsniffer.databinding.AdapterDeviceBinding
import com.bluetoothsniffer.model.ScanResultWrapper

@BindingAdapter("app:signalIcon")
fun setSignalIcon(view: View, scanResult: ScanResultWrapper) {
    val imageView = view as TextView
    imageView.setCompoundDrawablesWithIntrinsicBounds(scanResult.signalStrengthIcon(), 0, 0, 0)
}

class ScanResultsAdapter(context: Context) : RecyclerView.Adapter<ScanResultsAdapter.ScanResultViewHolder>() {

     var items = listOf<ScanResultWrapper>()

    fun swapData(items: List<ScanResultWrapper>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanResultViewHolder {
        val binding = AdapterDeviceBinding.inflate(inflater, parent, false)
        return ScanResultViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ScanResultViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private val inflater by lazy { LayoutInflater.from(context) }

    class ScanResultViewHolder(val binding: AdapterDeviceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(scanResult: ScanResultWrapper) {
            binding.wrapper = scanResult
            binding.executePendingBindings()
        }
    }
}