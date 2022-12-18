package com.quandoo.androidtask.ui.tables

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.quandoo.androidtask.R
import com.quandoo.androidtask.databinding.FragmentTablesBinding
import com.quandoo.androidtask.domain.model.RestaurantData
import com.quandoo.androidtask.domain.model.Table
import com.quandoo.androidtask.ui.base.BaseBindingFragment
import com.quandoo.androidtask.ui.main.MainActivity
import com.quandoo.androidtask.utils.*
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class TablesFragment : BaseBindingFragment<FragmentTablesBinding>() , Logger {

    private val adapter :TablesRvAdapter by lazy {
        TablesRvAdapter { table: Table ->
            tablesClickListener(
                table
            )
        }
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTablesBinding =
        { layoutInflater, viewGroup, b ->
            FragmentTablesBinding.inflate(layoutInflater, viewGroup, b)
        }

    override fun initView() {
        activity ?: return
        activity?.title = getString(R.string.tables)
        (activity as MainActivity).mainViewModel.fetchData()
        binding.recyclerView.layoutManager = LinearLayoutManager(
            context
        )
        binding.recyclerView.adapter =adapter

        (activity as MainActivity).mainViewModel.getRestaurantData().observe(
            viewLifecycleOwner,
            Observer { result ->

                when(result) {
                    is Resource.Success -> {
                        (activity as MainActivity).setProgressVisibility(false)
                        handleResult(result.data)
                    }
                    is Resource.Loading -> {
                        (activity as MainActivity).setProgressVisibility(true)
                    }
                    is Resource.Error -> {
                        (activity as MainActivity).setProgressVisibility(false)
                        if (!AppStatus.getInstance(context).isOnline) {
                            showInternetDialog()
                        } else {
                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })


    }

    private fun handleResult(data: RestaurantData?) {
        data ?: return
        adapter.replaceModel(data.tables ?: emptyList(), data.customers ?: emptyList())
        updateAdapter()
        data.reservations?.let {
            PersistanceUtil.writeReservationsToFile(it)
        }
        data.tables?.let {
            PersistanceUtil.writeTablesToFile(it)
        }
    }


    private fun tablesClickListener(table: Table) {
        //show dialog for reserved table
        if (table.reservedBy != null) {
            freeTableDialog {
                table.reservedBy = null
                (activity as MainActivity).mainViewModel.getTables()?.let {
                    PersistanceUtil.writeTablesToFile(it)
                }
                updateAdapter()
            }
        } else {
            val action =
                TablesFragmentDirections.actionTablesFragmentToCustomersFragment(table.id)
            findNavController(binding.root).navigate(action)
        }
    }

    private fun updateAdapter() {
        (activity as MainActivity).mainViewModel.getTables()?.let {
                adapter.notifyDataSetChanged()
        }
    }
}