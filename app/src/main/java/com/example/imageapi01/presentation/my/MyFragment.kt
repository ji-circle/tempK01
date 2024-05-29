package com.example.imageapi01.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.imageapi01.R
import com.example.imageapi01.app.AppContainer
import com.example.imageapi01.app.ImageApiApplication
import com.example.imageapi01.app.MyContainer
import com.example.imageapi01.databinding.FragmentMyBinding
import com.example.imageapi01.domain.entity.DocumentEntity
import com.example.imageapi01.presentation.main.MainViewModel
import com.example.imageapi01.presentation.model.DocumentModel

class MyFragment : Fragment() {

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!

    private lateinit var appContainer: AppContainer
    private lateinit var myViewModel: MyViewModel


    private val mainViewModel: MainViewModel by activityViewModels()

    private val myAdapter by lazy {
        MyAdapter {
            myViewModel.setSelectedItem(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initView()
        setObserver()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        appContainer.myContainer = null
        super.onDestroy()
    }

    private fun initContainer() {
        appContainer = (requireActivity().application as ImageApiApplication).appContainer
        appContainer.myContainer = MyContainer(appContainer.imageRepository)
    }


    private fun initView() {
        binding.apply {
            rvMyList.adapter = myAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    private fun initData() {
        appContainer.myContainer?.let {
            myViewModel =
                ViewModelProvider(this, it.myViewModelFactory)[MyViewModel::class.java]
        }
    }

    private fun setObserver() {
        with(myViewModel) {
            favoriteList.observe(viewLifecycleOwner) {
                myAdapter.itemList = it
            }
        }
    }

}