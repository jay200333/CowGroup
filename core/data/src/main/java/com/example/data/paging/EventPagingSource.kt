package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.Event
import com.example.network.model.toEvent
import com.example.network.retrofit.CowGroupApi

class EventPagingSource(
    private val api: CowGroupApi
) : PagingSource<Int, Event>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Event> {
        return try {
            val page = params.key ?: 0
            val events = api.getEvents(page, params.loadSize).data.content.map { it.toEvent() }
            LoadResult.Page(
                data = events,
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (events.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Event>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { page ->
                page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
            }
        }
    }
}
