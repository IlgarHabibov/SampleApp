package com.ilgar.starexsampleapp.ui.features.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.ilgar.starexsampleapp.R
import com.ilgar.starexsampleapp.data.models.posts.PostItem
import com.ilgar.starexsampleapp.databinding.FragmentPostsBinding
import com.ilgar.starexsampleapp.ui.adapters.PostsAdapter
import com.ilgar.starexsampleapp.ui.core.CoreFragment
import com.ilgar.starexsampleapp.ui.features.posts.event.PostsEvent
import com.ilgar.starexsampleapp.ui.features.posts.state.MainInputValidationTypes
import com.ilgar.starexsampleapp.ui.features.posts.state.PostsViewState
import com.ilgar.starexsampleapp.utils.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.ArrayList

@AndroidEntryPoint
class PostsFragment : CoreFragment<PostsEvent, PostsViewModel>(
    R.layout.fragment_posts,
    PostsViewModel::class
) {

    private var binding: FragmentPostsBinding? = null
    private val postsAdapter by lazy { PostsAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectState()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun setupViews() {
        setupRecyclerView()
        binding?.inputName?.addTextChangedListener {
            binding?.inputLayoutName?.isErrorEnabled = false
        }
        binding?.buttonNext?.setOnClickListener {
            val text = binding?.inputName?.text?.toString()
            viewModel.obtainEvent(PostsEvent.InputValidation(text))
        }
    }

    private fun setupRecyclerView(){
        binding?.recyclerPosts?.adapter = postsAdapter
        binding?.recyclerPosts?.addItemDecoration(
            VerticalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.post_item_vertical_space)
            )
        )
    }

    private fun collectState() {
        lifecycleScope.launch {
            viewModel.viewState.collect { viewState ->
                when(viewState){
                    is PostsViewState.Loading -> setLoading(true)
                    is PostsViewState.OnPostsSuccess -> setDataToAdapter(viewState.posts)
                    is PostsViewState.OnPostsEmpty -> setEmptyView()
                    is PostsViewState.OnPostsError -> setErrorMessage()
                    is PostsViewState.OnValidationFail -> setInputValidationFail(viewState.failType)
                }
            }
        }
    }

    private fun setErrorMessage() {
        setLoading(false)
        // Can show any popup or ui for error
    }

    private fun setEmptyView() {
        setLoading(false)
        // Can show eny empty ui
    }

    private fun setInputValidationFail(failType: MainInputValidationTypes) {
        setLoading(false)
        when(failType){
            MainInputValidationTypes.EMPTY -> setInputFail(getString(R.string.error_text_empty))
            MainInputValidationTypes.LENGTH -> setInputFail(getString(R.string.error_entered_text_length))
        }
    }

    private fun setInputFail(errorText: String) {
        binding?.inputLayoutName?.error = errorText
    }

    private fun setDataToAdapter(posts: ArrayList<PostItem>) {
        setLoading(false)
        binding?.recyclerPosts?.isVisible = true
        postsAdapter.submitList(posts)
    }

    private fun setLoading(isLoading: Boolean){
        binding?.progressLoading?.isVisible = isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}