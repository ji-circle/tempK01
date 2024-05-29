package com.example.imageapi01.presentation.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imageapi01.R
import com.example.imageapi01.app.AppContainer
import com.example.imageapi01.app.ImageApiApplication
import com.example.imageapi01.app.SearchContainer
import com.example.imageapi01.databinding.FragmentSearchBinding
import com.example.imageapi01.domain.entity.DocumentEntity
import com.example.imageapi01.presentation.main.MainViewModel
import com.example.imageapi01.presentation.model.DocumentModel
import com.example.imageapi01.presentation.model.toModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private val availableLanguages = arrayOf("English", "한국어")
    private val languageCodes = arrayOf("en-US", "ko-KR")
    private var selectedLanguageCode = "ko-KR"


//    private val searchAdapter: SearchAdapter by lazy {
//        SearchAdapter {
//            documentEntity, position -> itemOnClick(documentEntity, position)        }
//    }

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter { documentModel, position -> itemOnClick(documentModel, position)}
    }

    private fun itemOnClick(documentModel: DocumentModel, position: Int) {
        searchViewModel.setSelectedItem(documentModel)
        searchViewModel.toggleLike()
    }

    private lateinit var appContainer: AppContainer

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer()
    }

    private fun initContainer() {
        appContainer = (requireActivity().application as ImageApiApplication).appContainer
        appContainer.searchContainer = SearchContainer(appContainer.searchRepository, appContainer.imageRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initView()
        observeViewModel()
        setUpListeners()

        searchViewModel.isLikeStatus.observe(viewLifecycleOwner) { isLiked ->
            updateLikeButton(isLiked)
        }

        searchViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun setUpListeners() {
        with(binding){
            searchButton.setOnClickListener {
                val searchWord = searchEditText.text.toString()
                if (searchWord.isNotEmpty()) {
                    searchViewModel.getImageList(searchWord)
                    hideKeyboard()
                } else {
                    Toast.makeText(requireContext(), "검색어를 입력해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
            binding.btnVoice.setOnClickListener {
                showSelectionDialog()
            }
        }
    }

    private fun initViewModel(){
        appContainer.searchContainer?.let {
            searchViewModel = ViewModelProvider(this, it.searchViewModelFactory)[SearchViewModel::class.java]
        }
        mainViewModel= ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    private fun initView() {
        binding.searchRecyclerView.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
    private fun observeViewModel() {
        searchViewModel.getImageModelList.observe(viewLifecycleOwner) {
            searchAdapter.itemList = it
            searchAdapter.notifyDataSetChanged()
        }
    }

//    private fun observeVideoById() {
//        searchViewModel.videoById.observe(viewLifecycleOwner) { video ->
//            video?.let {
//                mainViewModel.setSelectedItem(it)
//                searchViewModel.clearVideoById()
//            }
//        }
//    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

//    private fun showSelectionDialog() {
//        AlertDialog.Builder(requireContext())
//            .setTitle("검색어를 입력해 주세요")
//            .setItems(availableLanguages) { dialog, which ->
//                selectedLanguageCode = languageCodes[which]
//                startSpeechToText()
//            }
//            .show()
//    }
//
//    private fun startSpeechToText() {
//        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        intent.putExtra(
//            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//        )
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, selectedLanguageCode)
//
//        try {
//            speechResultLauncher.launch(intent)
//        } catch (e: Exception) {
//            Toast.makeText(
//                requireContext(),
//                "stt를 지원하지 않는 기기입니다",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//    private val speechResultLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val data = result.data
//                val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//                if (results != null && results.isNotEmpty()) {
//                    binding.searchEditText.setText(results[0])
//                }
//            } else {
//                Toast.makeText(requireContext(), "인식 실패", Toast.LENGTH_SHORT).show()
//            }
//        }

//    private fun updateLikeButton(isLiked: Boolean) {
//        if (isLiked) {
//            binding..text = getString(R.string.dislike)
//            binding.btnItemIsLike.setBackgroundResource(R.drawable.bg_btn_dislike)
//        } else {
//            binding.btnItemIsLike.text = getString(R.string.like)
//            binding.btnItemIsLike.setBackgroundResource(R.drawable.bg_btn_like)
//        }
//    }
}