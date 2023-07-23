package com.qucoon.rubiesnigeria.base

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.qucoon.rubiesnigeria.repository.rest.WorkerRepository
import com.qucoon.rubiesnigeria.storage.room.data_source.ContactsDataSource
import com.qucoon.rubiesnigeria.utils.CustomProgressDialog
import com.qucoon.rubiesnigeria.views.activity.MainActivity
import org.koin.android.ext.android.inject

interface NavigationActions {
    fun openFragment(action: Int, showBottomNavigation: Boolean = false)
    fun popFragment()
    fun openDialog(action: Int)
}

open class BaseFragment : Fragment(), NavigationActions {

    private val progressDialog by lazy { CustomProgressDialog(requireContext()) }
    private val workerRepository: WorkerRepository by inject()
     val contactsDataSource: ContactsDataSource by inject()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    fun showLoaderDialog(status: Boolean) {
        println("final_status $status")
        if (status) {
            progressDialog.start("please wait")
        } else {
            progressDialog.stop()
        }
    }

    fun getContacts(){
       if((activity as MainActivity).checkForPermission()){
           workerRepository.getAllContacts()
       }
    }

    fun setupObserver(baseSocketViewModel: BaseSocketViewModel) {

    }

     private fun setupErrorMessage(errorMessage: MutableLiveData<String>) {
         errorMessage.observe(this) {
             showToast(it)
         }
     }

    private fun setupLoading(showLoading: MutableLiveData<Boolean>) {
        showLoading.observe(this) {
            showLoaderDialog(it)
        }
    }
     fun makeActiveCalls(action:()->Unit){
         (activity as MainActivity).makeSocketCall{
             action.invoke()
         }
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

