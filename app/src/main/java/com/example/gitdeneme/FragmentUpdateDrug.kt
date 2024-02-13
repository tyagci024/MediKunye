package com.example.gitdeneme

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gitdeneme.databinding.FragmentUpdateDrugBinding
import com.example.gitdeneme.model.Drug
import com.example.gitdeneme.viewmodel.DrugViewModel
import java.util.Calendar


class FragmentUpdateDrug : Fragment() {
    private lateinit var binding: FragmentUpdateDrugBinding
    private val drugViewModel: DrugViewModel by viewModels()
    private val args by navArgs<FragmentUpdateDrugArgs>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(context, "Geri tuşuna basıldı!", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_fragmentUpdateDrug_to_drugListFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)


    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateDrugBinding.inflate(inflater, container, false)

        binding.drugNameUpdate.setText(args.currentDrug.name)
        binding.perDayUpdate.setText(args.currentDrug.timesPerDay.toString())
        binding.descriptionUpdate.setText(args.currentDrug.description)

        val endDateCalendar = Calendar.getInstance().apply {
            timeInMillis = args.currentDrug.endDate
        }

        binding.endDatePickerUpdate.updateDate(
            endDateCalendar.get(Calendar.YEAR),
            endDateCalendar.get(Calendar.MONTH),
            endDateCalendar.get(Calendar.DAY_OF_MONTH)
        )

        val timeCalendar = Calendar.getInstance().apply {
            timeInMillis = args.currentDrug.time
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            binding.timeInputUpdate.hour = timeCalendar.get(Calendar.HOUR_OF_DAY)
            binding.timeInputUpdate.minute = timeCalendar.get(Calendar.MINUTE)
        } else {
            binding.timeInputUpdate.currentHour = timeCalendar.get(Calendar.HOUR_OF_DAY)
            binding.timeInputUpdate.currentMinute = timeCalendar.get(Calendar.MINUTE)
        }





        binding.updateButton.setOnClickListener {
            val hour = binding.timeInputUpdate.hour
            val minute = binding.timeInputUpdate.minute
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            val time = calendar.timeInMillis

            val endDateCalendar = Calendar.getInstance()
            endDateCalendar.set(
                binding.endDatePickerUpdate.year,
                binding.endDatePickerUpdate.month,
                binding.endDatePickerUpdate.dayOfMonth
            )
            val endDate = endDateCalendar.timeInMillis

            val updateDrug = Drug(args.currentDrug.id,binding.drugNameUpdate.text.toString(),binding.descriptionUpdate.text.toString(),
                time,endDate,binding.perDayUpdate.text.toString().toInt()
            )
            drugViewModel.update(updateDrug)
            findNavController().navigate(R.id.action_fragmentUpdateDrug_to_drugListFragment)
            drugViewModel.readAllData.observe(viewLifecycleOwner,{
                it?.let {
                    it.forEach {
                        Log.d("DrugRepo", "Drug: ${it.id}, Name: ${args.currentDrug.name}")
                    }
                    if(it.size==0){
                        Toast.makeText(context,"liste boş", Toast.LENGTH_LONG).show()
                    }

                }
            })
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_tool, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteall)
        { drugViewModel.delete(args.currentDrug)
           findNavController().navigate(R.id.action_fragmentUpdateDrug_to_drugListFragment)
        }
        return super.onOptionsItemSelected(item)

    }


}