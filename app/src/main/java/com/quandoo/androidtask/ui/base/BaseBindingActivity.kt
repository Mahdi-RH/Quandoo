package com.quandoo.androidtask.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.quandoo.androidtask.R
import com.quandoo.androidtask.databinding.ActivityBaseBinding

/**
 *  A parent for every activity that wanna implement viewBinding.
 *
 */
abstract class
BaseBindingActivity<VB : ViewBinding> : AppCompatActivity() {

    abstract fun bindingInflater (inflater :LayoutInflater) : VB
    abstract fun initView()

    private var _binding: ViewBinding? = null
    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    private lateinit var baseBinding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        baseBinding = ActivityBaseBinding.inflate( layoutInflater)
        setContentView(requireNotNull(_binding).root)

    }

    /**
     *  BaseContainer will hold view of subclasses
     */

    override fun setContentView(layoutResID: Int) {
        super.setContentView(baseBinding.root)
        baseBinding.containerBase.removeAllViews()
        baseBinding.containerBase.addView(layoutInflater.inflate(layoutResID, null))
        initView()
    }


    override fun setContentView(view: View?) {
        super.setContentView(baseBinding.root)
        baseBinding.containerBase.removeAllViews()
        baseBinding.containerBase.addView(view)
        initView()
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(baseBinding.root, params)
        baseBinding.containerBase.removeAllViews()
        baseBinding.containerBase.addView(view)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setProgressVisibility(isVisible: Boolean){
        baseBinding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}