package com.example.skillcinema.ui.pages.gallery_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.skillcinema.App
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.data.apiImages.Item
import com.example.skillcinema.model.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class GalleryViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    init {
        App.getAppComponent().inject(this)
        getImages()
    }

    private val still = repository.stillImages.flow.cachedIn(viewModelScope)
    private val shooting = repository.shootingImages.flow.cachedIn(viewModelScope)
    private val poster = repository.posterImages.flow.cachedIn(viewModelScope)
    private val fanArt = repository.fanArtImages.flow.cachedIn(viewModelScope)
    private val promo = repository.promoImages.flow.cachedIn(viewModelScope)
    private val concept = repository.conceptImages.flow.cachedIn(viewModelScope)
    private val wallpaper = repository.wallpaperImages.flow.cachedIn(viewModelScope)
    private val cover = repository.coverImages.flow.cachedIn(viewModelScope)
    private val screenShort = repository.screenShortImages.flow.cachedIn(viewModelScope)

    val listPaging =
        listOf(still, shooting, poster, fanArt, promo, concept, wallpaper, cover, screenShort)

    val listSize = repository.lisSize

    private fun getImages() {
        viewModelScope.launch {
            repository.getImagesStill(sharedPreferencesManager.getMovieId(), true)
        }
    }

    override fun onCleared() {
        repository.onCleared()
        super.onCleared()
    }


}