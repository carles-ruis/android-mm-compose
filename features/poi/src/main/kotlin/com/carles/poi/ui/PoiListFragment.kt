package com.carles.poi.ui

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.carles.core.ui.view.BaseFragment
import com.carles.core.ui.view.consumeMenuClick
import com.carles.core.ui.viewmodel.ERROR
import com.carles.core.ui.viewmodel.LOADING
import com.carles.core.ui.viewmodel.ResourceState
import com.carles.core.ui.viewmodel.SUCCESS
import com.carles.poi.Poi
import com.carles.poi.R
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_poi_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class PoiListFragment : BaseFragment(R.layout.fragment_poi_list) {

    private val viewModel by viewModel<PoiListViewModel>()
    private lateinit var adapter: PoiListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val toolbar = poilist_toolbar as MaterialToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.poilist_title)
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)
        toolbar.setNavigationOnClickListener { activity?.finish() }

        adapter = PoiListAdapter { poi -> navigateToPoiDetail(poi.id) }
        poilist_recyclerview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        poilist_recyclerview.adapter = adapter

        viewModel.observablePoiList.observe(viewLifecycleOwner, Observer {
            it?.let { handlePoiList(it.state, it.data, it.message) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.poilist_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.poilist_settings_menu -> consumeMenuClick { navigateToSettings() }
        else -> super.onContextItemSelected(item)
    }

    private fun handlePoiList(state: ResourceState, poiList: List<Poi>?, errorMessage: String?) {
        when (state) {
            SUCCESS -> {
                hideProgress()
                adapter.setItems(poiList ?: emptyList())
            }
            ERROR -> navigateToError(errorMessage)
            LOADING -> showProgress()
        }
    }

    private fun navigateToPoiDetail(id: String) {
        findNavController().navigate(R.id.action_poiListFragment_to_poiDetailFragment, PoiDetailFragment.getBundle(id))
    }

    private fun navigateToSettings() {
        findNavController().navigate(Uri.parse(getString(R.string.navigation_settings_deeplink)))
    }

    private fun navigateToError(errorMessage: String?) {
        hideProgress()
        navigateForResult(
            R.id.action_poiListFragment_to_errorDialogFragment, ErrorDialogFragment.getBundle(errorMessage, true),
            REQUEST_CODE_RETRY
        )
    }

    override fun onNavigationResult(requestCode: String, result: Bundle) {
        super.onNavigationResult(requestCode, result)
        when (requestCode) {
            REQUEST_CODE_RETRY -> viewModel.retry()
        }
    }

    companion object {
        private const val REQUEST_CODE_RETRY = "request_code_retry"
    }
}
