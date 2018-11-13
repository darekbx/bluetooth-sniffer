package com.bluetoothsniffer.ui.main

import android.bluetooth.le.ScanResult
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bluetoothsniffer.databinding.AdapterDeviceBinding

class ScanResultsAdapter(context: Context) : RecyclerView.Adapter<ScanResultsAdapter.ScanResultViewHolder>() {

    private var items = listOf<ScanResult>()

    fun swapData(items: List<ScanResult>) {
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

        fun bind(scanResult: ScanResult) {
            binding.scanResult = scanResult
            binding.executePendingBindings()
        }
    }
}