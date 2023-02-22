package com.carles.poi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.carles.core.Navigator
import com.carles.core.databinding.FragmentPoiListBinding
import com.carles.core.ui.BaseFragment
import com.carles.core.ui.ERROR
import com.carles.core.ui.LOADING
import com.carles.core.ui.ResourceState
import com.carles.core.ui.SUCCESS
import com.carles.core.ui.consumeMenuClick
import com.carles.poi.Poi
import com.carles.poi.R
import com.carles.poi.ui.ErrorDialogFragment.Companion.REQUEST_CODE_RETRY
import org.koin.android.ext.android.get
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.scope
import org.koin.core.parameter.parametersOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class PoiListFragment : BaseFragment<FragmentPoiListBinding>() {

    private val viewModel: PoiListViewModel by viewModel()
    private val navigate: Navigator by lazy {
        (requireActivity() as AndroidScopeComponent).scope.get { parametersOf(requireActivity()) }
    }
    private lateinit var adapter: PoiListAdapter

    override val progress
        get() = binding.poilistProgress.progress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResultListener()
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentPoiListBinding {
        return FragmentPoiListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val toolbar = binding.poilistToolbar.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.poilist_title)
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)
        toolbar.setNavigationOnClickListener { activity?.finish() }

        adapter = PoiListAdapter { poi -> navigate.toPoiDetailFromPoiList(poi.id) }
        binding.poilistRecyclerview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        binding.poilistRecyclerview.adapter = adapter

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
