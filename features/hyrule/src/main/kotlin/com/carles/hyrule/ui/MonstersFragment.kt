package com.carles.hyrule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import com.carles.common.Navigate
import com.carles.common.ui.BaseFragment
import com.carles.common.ui.ERROR
import com.carles.common.ui.LOADING
import com.carles.common.ui.SUCCESS
import com.carles.common.ui.consumeMenuClick
import com.carles.hyrule.R
import com.carles.hyrule.databinding.FragmentMonstersBinding
import com.carles.hyrule.ui.ErrorDialogFragment.Companion.REQUEST_CODE_RETRY
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MonstersFragment : BaseFragment<FragmentMonstersBinding>() {

    private val viewModel: MonstersViewModel by viewModel()
    private val navigate: Navigate by lazy {
        (requireActivity() as AndroidScopeComponent).scope.get { parametersOf(requireActivity()) }
    }

    override val progress: View
        get() = binding.monstersProgress.progress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResultListener()
    }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMonstersBinding {
        return FragmentMonstersBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        binding.monstersRecycler.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        binding.monstersRecycler.adapter = MonstersAdapter { monster -> navigate.toMonsterDetailFromMonsters(monster.id) }
        observeMonsters()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.monsters_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.monsters_settings_menu -> consumeMenuClick { navigate.toSettings() }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setResultListener() {
        setFragmentResultListener(REQUEST_CODE_RETRY) { _, _ ->
            viewModel.retry()
        }
    }

    private fun initToolbar() {
        setHasOptionsMenu(true)
        val toolbar = binding.monstersToolbar.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.monsters_title)
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)
        toolbar.setNavigationOnClickListener { activity?.finish() }
    }

    private fun observeMonsters() {
        viewModel.monsters.observe(viewLifecycleOwner) { result ->
            when (result.state) {
                SUCCESS -> {
                    hideProgress()
                    (binding.monstersRecycler.adapter as MonstersAdapter).submitList(result.data)
                }
                ERROR -> {
                    hideProgress()
                    navigate.toErrorFromMonsters(result.message)
                }
                LOADING -> showProgress()
            }
        }
    }
}