package com.quandoo.androidtask.ui.customers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.quandoo.androidtask.R
import com.quandoo.androidtask.databinding.FragmentCustomersBinding
import com.quandoo.androidtask.domain.model.Reservation
import com.quandoo.androidtask.ui.base.BaseBindingFragment
import com.quandoo.androidtask.ui.main.MainActivity
import com.quandoo.androidtask.utils.Logger
import com.quandoo.androidtask.utils.PersistanceUtil
import dagger.hilt.android.AndroidEntryPoint

private const val EXTRA_TABLE_ID = "tableId"
private const val NON_EXISTING_TABLE_ID = -1L

@AndroidEntryPoint
class CustomersFragment : BaseBindingFragment<FragmentCustomersBinding>() , Logger {

    private var selectedTableId: Long = NON_EXISTING_TABLE_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedTableId = it.getLong(EXTRA_TABLE_ID)
        }

        if (this.selectedTableId == NON_EXISTING_TABLE_ID) {
            throw RuntimeException("Selected table ID cannot be found !")
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCustomersBinding =
        { layoutInflater, viewGroup, b ->
            FragmentCustomersBinding.inflate(layoutInflater, viewGroup, b)
        }

    override fun initView() {

        activity ?: return
        activity?.title = getString(R.string.customers)

        val mainViewModel = (activity as MainActivity).mainViewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(context)



        mainViewModel.getCustomers()?.let {
            binding.recyclerView.adapter = CustomersRvAdapter(it) { customer ->
                log("customer clicked $customer")
                //Reserve table
                mainViewModel.getTables()?.find { table -> table.id == selectedTableId }
                    ?.let { table ->
                        //create reservation
                        val reservations: List<Reservation>? = mainViewModel.getReservations()
                        (reservations as ArrayList).add(
                            Reservation(
                                customer.id,
                                table.id,
                                customer.id + table.id
                            )
                        )
                        table.reservedBy = customer.firstName + " " + customer.lastName
                    }

                mainViewModel.getReservations()?.let { list ->
                    PersistanceUtil.writeReservationsToFile(list)
                }
                mainViewModel.getTables()?.let { list ->
                    PersistanceUtil.writeTablesToFile(list)
                }

                (activity as MainActivity).onBackPressed()

            }
        }

    }
}