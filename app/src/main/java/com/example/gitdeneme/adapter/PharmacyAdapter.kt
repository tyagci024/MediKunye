package com.example.gitdeneme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitdeneme.databinding.PharmacyItemLayoutBinding
import com.example.gitdeneme.model.Pharmacy

class PharmacyAdapter : RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder>() {

    private var pharmacyList = emptyList<Pharmacy>()

    class PharmacyViewHolder(private val binding: PharmacyItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pharmacy: Pharmacy) {
            binding.itemTitle.text = pharmacy.phName
            binding.itemSubtitle.text = pharmacy.phAdress
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyViewHolder {
        val binding = PharmacyItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PharmacyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        holder.bind(pharmacyList[position])
    }

    override fun getItemCount() = pharmacyList.size

    fun update(pharmacy: List<Pharmacy>) {
        this.pharmacyList = pharmacy
        notifyDataSetChanged()
    }
}