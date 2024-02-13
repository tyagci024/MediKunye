package com.example.gitdeneme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitdeneme.adapter.DateAdapter
import com.example.gitdeneme.adapter.PharmacyAdapter
import com.example.gitdeneme.databinding.FragmentDateListBinding
import com.example.gitdeneme.databinding.FragmentPharmacyListBinding
import com.example.gitdeneme.viewmodel.DateViewModel
import com.example.gitdeneme.viewmodel.PharmacyViewModel


class FragmentDateList : Fragment() {
    private lateinit var binding: FragmentDateListBinding
    private lateinit var adapter: DateAdapter
    private val dateViewModel: DateViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(context, "Geri tuşuna basıldı!", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_fragmentDateList_to_fragmentHome)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDateListBinding.inflate(inflater,container,false)
        binding.recyclerView.layoutManager=LinearLayoutManager(context)

        adapter = DateAdapter()
        binding.recyclerView.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentDateList_to_fragmentSetDate)
        }

        observeData()
        return binding.root
    }

    fun observeData(){
        dateViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setDates(it)
                if(it.size==0){
                    Toast.makeText(context,"liste boş", Toast.LENGTH_LONG).show()
                }

            }

        })
    }


}