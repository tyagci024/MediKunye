package com.example.gitdeneme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitdeneme.adapter.DrugAdapter
import com.example.gitdeneme.databinding.FragmentDrugListBinding
import com.example.gitdeneme.viewmodel.DrugViewModel

class DrugListFragment : Fragment() {
    private lateinit var binding : FragmentDrugListBinding
    private lateinit var drugAdapter: DrugAdapter
    private val drugViewModel: DrugViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(context, "Geri tuşuna basıldı!", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_drugListFragment_to_fragmentHome)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_drug_list,container, false)
        binding.listNesnesi=this



        drugViewModel.readAllData.observe(viewLifecycleOwner,{
            it?.let {
                drugAdapter= DrugAdapter()
                binding.adapterNesnesi=drugAdapter
                drugAdapter.setDrugs(it)
                if(it.size==0){
                    Toast.makeText(context,"liste boş", Toast.LENGTH_LONG).show()
                }

            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_tool,menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.deleteall){
            drugViewModel.deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    fun buttonAction (){
        findNavController().navigate(R.id.action_drugListFragment_to_fragmentSetDrug)

    }




}