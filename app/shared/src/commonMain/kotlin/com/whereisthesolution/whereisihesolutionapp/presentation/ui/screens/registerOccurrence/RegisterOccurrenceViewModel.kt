package com.whereisthesolution.whereisihesolutionapp.presentation.ui.screens.registerOccurrence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereisthesolution.whereisihesolutionapp.config.media.ImageResult
import com.whereisthesolution.whereisihesolutionapp.domain.model.post.image.ImageUpload
import com.whereisthesolution.whereisihesolutionapp.network.dto.CreatePostRequest
import com.whereisthesolution.whereisihesolutionapp.repository.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class RegisterOccurrenceViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OccurrenceUiState())
    val uiState: StateFlow<OccurrenceUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<OccurrenceUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onTitleChange(title: String) {
        _uiState.update {
            it.copy(title = title)
        }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update {
            it.copy(description = description)
        }
    }

    fun selectedImage(image: ImageResult) {
        _uiState.update {
            it.copy(selectedImage = image)
        }
    }

    fun createPost() {
        val state = _uiState.value

        val title = state.title.trim()
        val description = state.description.trim()
        val location = state.location.trim()
        val images = state.images


        val request = CreatePostRequest(
            title = title,
            description = description,
            location = location,
            images = images
        )

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            try {
                repository.createPost(request)
                    .onSuccess {
                        _uiState.update { it.copy(loading = false) }
                        _uiEvent.send(OccurrenceUiEvent.CreatePostMessage("Ocorrência criada com sucesso!"))
                        delay(8.milliseconds)
                        _uiEvent.send(OccurrenceUiEvent.ReturnFeedScreen)
                    }
                    .onFailure { e ->
                        _uiEvent.send(OccurrenceUiEvent.CreatePostMessage("Erro ao criar ocorrência: ${e.message}"))
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(loading = false, errorMessage = e.message) }
                _uiEvent.send(OccurrenceUiEvent.CreatePostMessage("Erro ao criar ocorrência: ${e.message}"))
            }
        }
    }
}


data class OccurrenceUiState(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val selectedImage: ImageResult? = null,
    val images: List<ImageUpload>? = null
)

sealed interface OccurrenceUiEvent {
    data class CreatePostMessage(val message: String) : OccurrenceUiEvent
    data object ReturnFeedScreen : OccurrenceUiEvent
}