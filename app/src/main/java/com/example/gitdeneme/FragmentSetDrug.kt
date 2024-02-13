package com.example.gitdeneme

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gitdeneme.databinding.FragmentSetDrugBinding
import com.example.gitdeneme.model.Drug
import com.example.gitdeneme.viewmodel.DrugViewModel
import java.util.Calendar


class FragmentSetDrug : Fragment() {
    private lateinit var binding: FragmentSetDrugBinding
    private val drugViewModel: DrugViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(context, "Geri tuşuna basıldı!", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_fragmentSetDrug_to_drugListFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)


    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetDrugBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.addButton.setOnClickListener {
            val timesPerDay = binding.perDay.text.toString().toInt()

            val hour = binding.timeInput.hour
            val minute = binding.timeInput.minute
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            val time = calendar.timeInMillis

            val endDateCalendar = Calendar.getInstance()
            endDateCalendar.set(
                binding.endDatePicker.year,
                binding.endDatePicker.month,
                binding.endDatePicker.dayOfMonth
            )
            val endDate = endDateCalendar.timeInMillis
            val drug = Drug(0,binding.drugNameSave.text.toString(),binding.descriptionSave.text.toString(),time,endDate,timesPerDay)
            drugViewModel.impDrug(drug)

            drugViewModel.readAllData.observe(viewLifecycleOwner,{
                it?.let {
                    it.forEach {
                        Log.d("DrugRepo", "Drug: ${it.id}, Name: ${drug.name}")
                    }
                    if(it.size==0){
                        Toast.makeText(context,"liste boş", Toast.LENGTH_LONG).show()
                    }

                }
            })

            findNavController().navigate(R.id.action_fragmentSetDrug_to_drugListFragment)

        }

        return view    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}

