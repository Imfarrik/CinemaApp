package com.example.skillcinema.ui.adapters.all_movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.data.apiFilms.Item
import com.example.skillcinema.model.data.apiTop.Film
import com.example.skillcinema.model.network.ServiceApi
import retrofit2.HttpException

class ItemPagingSource(
    private val serviceApi: ServiceApi,
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val isSerials: Boolean
) : PagingSource<Int, Item>() {
    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        try {
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(20)
            val response =
                if (!isSerials) {
                    serviceApi.getFilmsPage(
                        countries = sharedPreferencesManager.getCountryId(),
                        genres = sharedPreferencesManager.getGenreId(),
                        page = currentPage
                    )
                } else {
                    serviceApi.getSeriesPage(page = currentPage)
                }
            return if (response.isSuccessful) {
                val data = checkNotNull(response.body()).items
                val prevKey = if (currentPage == 1) null else currentPage - 1
                val nextKey = if (data.size < pageSize) null else currentPage + 1
                LoadResult.Page(data, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }
}