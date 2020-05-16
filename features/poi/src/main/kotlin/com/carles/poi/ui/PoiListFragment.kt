package com.carles.poi.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.carles.core.Navigator
import com.carles.core.ui.BaseFragment
import com.carles.core.ui.ERROR
import com.carles.core.ui.LOADING
import com.carles.core.ui.ResourceState
import com.carles.core.ui.SUCCESS
import com.carles.core.ui.consumeMenuClick
import com.carles.poi.Poi
import com.carles.poi.R
import com.carles.poi.ui.ErrorDialogFragment.Companion.REQUEST_CODE_RETRY
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_poi_list.poilist_recyclerview
import kotlinx.android.synthetic.main.fragment_poi_list.poilist_toolbar
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PoiListFragment : BaseFragment(R.layout.fragment_poi_list) {

    private val viewModel: PoiListViewModel by viewModel()
    private val navigate: Navigator by lazy {
        requireActivity().lifecycleScope.get<Navigator> { parametersOf(requireActivity()) }
    }
    private lateinit var adapter: PoiListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResultListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val toolbar = poilist_toolbar as MaterialToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.poilist_title)
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)
        toolbar.setNavigationOnClickListener { activity?.finish() }

        adapter = PoiListAdapter { poi -> navigate.toPoiDetailFromPoiList(poi.id) }
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
        R.id.poilist_settings_menu -> consumeMenuClick { navigate.toSettings() }
        else -> super.onContextItemSelected(item)
    }

    private fun handlePoiList(state: ResourceState, poiList: List<Poi>?, errorMessage: String?) {
        when (state) {
            SUCCESS -> {
                hideProgress()
                adapter.setItems(poiList ?: emptyList())
            }
            ERROR -> {
                hideProgress()
                navigate.toErrorFromPoiList(errorMessage)
            }
            LOADING -> showProgress()
        }
    }

    private fun setResultListener() {
        this.setFragmentResultListener(REQUEST_CODE_RETRY) { _, _ ->
            viewModel.retry()
        }
    }
}
