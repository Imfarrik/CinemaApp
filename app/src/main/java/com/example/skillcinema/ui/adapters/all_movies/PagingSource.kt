package com.example.skillcinema.ui.adapters.all_movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.model.data.apiTop.Film
import com.example.skillcinema.model.network.ServiceApi
import retrofit2.HttpException

class PagingSource(private val serviceApi: ServiceApi, private val isTop250: Boolean) :
    PagingSource<Int, Film>() {
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        try {
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(20)
            val response =
                if (!isTop250) {
                    serviceApi.getTopPage(page = currentPage)
                } else {
                    serviceApi.getTopPage(page = currentPage, type = Constants.TOP_250_BEST_FILMS)
                }
            return if (response.isSuccessful) {
                val data = checkNotNull(response.body()).films
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