package com.quandoo.androidtask.ui.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.quandoo.androidtask.ui.main.MainActivity

/**
 *  A base class for every fragment that wanna implement viewBinding
 */
abstract class BaseBindingFragment<VB : ViewBinding> : Fragment() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    abstract fun initView()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun showInternetDialog(){
        //close the app when no internet
            context?.let {
                val builder = AlertDialog.Builder(it)   //TODO(M) Utils
                builder.setMessage("No internet connection!")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Close App"
                    ) { dialog: DialogInterface?, id: Int ->
                        (activity as MainActivity).finish()
                    }
                val alert = builder.create()
                alert.show()
                return

            }?:return
    }

    fun freeTableDialog( freeTable:()-> Unit){
        context?.let {
            //show dialog that removes the reservation
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Do you want to free the table?").setPositiveButton(
                "Yes"
            ) { dialog: DialogInterface?, which: Int ->
                freeTable()
                //Free table
            }.setNegativeButton("No", null).show()
        }
    }
}