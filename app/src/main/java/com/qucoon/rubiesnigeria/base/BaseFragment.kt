package com.qucoon.rubiesnigeria.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qucoon.rubiesnigeria.R
import com.qucoon.rubiesnigeria.activity.MainActivity

interface NavigationActions {
    fun openFragment(action: Int, showBottomNavigation: Boolean = false)
    fun popFragment()
    fun openDialog(action: Int)
}

open class BaseFragment : Fragment(), NavigationActions {
    private var loadingMessage: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun openFragment(action: Int, showBottomNavigation: Boolean) {
        findNavController().navigate(action)
        showBottomNavigation(showBottomNavigation)
    }

    override fun openDialog(action: Int) {
        findNavController().navigate(action)
    }

    fun showBottomNavigation(status: Boolean) {
        (activity as MainActivity).toggleBottomNavigationView(status)
    }

    override fun popFragment() {
        findNavController().popBackStack()
    }

    fun showToast(errorMessage: String, gravity: Int = Gravity.CENTER) {
        val toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
        toast.setGravity(gravity, 0, 0)
        toast.show()
    }


}

