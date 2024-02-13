package com.example.gitdeneme

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gitdeneme.databinding.FragmentHomeBinding
import com.example.gitdeneme.viewmodel.PharmacyViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Calendar


class FragmentHome : Fragment() {
    private lateinit var binding :FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        binding.homeNesnesi=this


        greetingTitle()

        sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val bloodcel = sharedPreferences.getString("blood_cell","kan grubu")
        binding.bloodGroup.setText(bloodcel)
        val emergencyCall = sharedPreferences.getString("emergency_number","acil durumu araması")
        binding.emerNumber.setText(emergencyCall)

        val userName = sharedPreferences.getString("user_name","Kullanıcı Adı")
        binding.userName.setText(userName).toString().uppercase()





        return binding.root
    }

    fun navigateFragmentDrugList (){
        binding.drugList.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_drugListFragment)
        }

    }
    fun navigateFragmentPharmacyList (){
        binding.phList.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_pharmacyList)
        }

    }
    fun navigateFragmentUserInfoEdit(){
        binding.accountEdit.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_userInfoEdit)
        }
    }
    fun navigateFragmentDateList(){
        binding.dateList.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentDateList)
        }
    }

    private fun startPhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)

        }
        else
            Log.d("FragmentHome", "Telefon arama aktivitesi bulunamadı.")

    }

    fun greetingTitle() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        if (hour in 19..23 || hour in 0..5) {
            binding.textView3.text = "İYİ AKŞAMLAR"
        } else {
            binding.textView3.text = "İYİ GÜNLER"
        }
    }
    fun callEmer(){
        val emergencyCall = sharedPreferences.getString("emergency_number","acil durumu araması")
        binding.cardCall.setOnClickListener {
            if (emergencyCall!!.isNotEmpty()) {
                startPhoneCall(emergencyCall)
            }


        }

    }



}