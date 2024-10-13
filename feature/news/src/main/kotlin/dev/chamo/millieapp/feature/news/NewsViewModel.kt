package dev.chamo.millieapp.feature.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.chamo.millieapp.core.common.result.Result
import dev.chamo.millieapp.core.common.result.asResult
import dev.chamo.millieapp.core.data.repository.NewsRepository
import dev.chamo.millieapp.core.model.TopHeadline
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    private val _topHeadLinesUiState = MutableStateFlow<TopHeadLinesUiState>(TopHeadLinesUiState.Loading)
    val topHeadLinesUiState: StateFlow<TopHeadLinesUiState> = _topHeadLinesUiState

    init {
        loadTopHeadlines()
    }

    private fun loadTopHeadlines() {
        viewModelScope.launch {
            newsRepository.getTopHeadlines()
                .asResult()
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _topHeadLinesUiState.value = TopHeadLinesUiState.Success(result.data)
                        }
                        is Result.Error -> {
                            loadTopHeadLinesFromDatabase()
                        }
                        is Result.Loading -> {
                            _topHeadLinesUiState.value = TopHeadLinesUiState.Loading
                        }
                    }
                }
        }
    }

    private fun loadTopHeadLinesFromDatabase() {
        viewModelScope.launch {
            newsRepository.getTopHeadlines(isOffline = true)
                .asResult()
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _topHeadLinesUiState.value = TopHeadLinesUiState.Success(result.data)
                        }
                        is Result.Error -> {
                            _topHeadLinesUiState.value = TopHeadLinesUiState.Error
                        }
                        is Result.Loading -> {
                            _topHeadLinesUiState.value = TopHeadLinesUiState.Loading
                        }
                    }
                }
        }
    }

    fun setHeadLineSelected(url: String) {
        val currentState = _topHeadLinesUiState.value
        if (currentState is TopHeadLinesUiState.Success) {
            val newTopHeadlines = currentState.topHeadlines.map { topHeadLine ->
                if (topHeadLine.url == url) {
                    topHeadLine.copy(isSelected = true).also {
                        upsertTopHeadLine(it)
                    }
                } else {
                    topHeadLine
                }
            }

            _topHeadLinesUiState.value = currentState.copy(topHeadlines = newTopHeadlines)
        }
    }

    private fun upsertTopHeadLine(topHeadline: TopHeadline) {
        viewModelScope.launch {
            newsRepository.upsertTopHeadLine(topHeadline)
        }
    }
}

sealed interface TopHeadLinesUiState {
    data object Loading: TopHeadLinesUiState

    data object Error: TopHeadLinesUiState

    data class Success(
        val topHeadlines: List<TopHeadline>
    ): TopHeadLinesUiState {
        fun isEmpty(): Boolean = topHeadlines.isEmpty()
    }
}