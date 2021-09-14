package tj.ilhom.trip.Utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import tj.ilhom.trip.models.PagingResponse
import tj.ilhom.trip.network.Resource
import java.io.IOException

fun <T> PagingResponse<T>.getNextPageNumber() =
    if (next?.contains("&page_size=") == true)
        next.substringBefore("&page_size=")
            .substringAfter("?page=")
            .toIntOrNull()
    else next?.substringAfter("page=")
        ?.first()
        ?.digitToIntOrNull()

fun <T> PagingResponse<T>.getPrevPageNumber() =
    if (previous?.contains("&page_size=") == true)
        previous.substringBefore("&page_size=")
            .substringAfter("?page=")
            .toIntOrNull()
    else previous?.substringAfter("page=")
        ?.first()
        ?.digitToIntOrNull()

fun <T : Any> Resource<PagingResponse<T>>.toLoadResult(): PagingSource.LoadResult<Int, T> =
    when (this) {
        is Resource.Error -> PagingSource.LoadResult.Error(IOException(message))
        is Resource.Success -> PagingSource.LoadResult.Page(
            data = data.results,
            prevKey = data.getPrevPageNumber(),
            nextKey = data.getNextPageNumber()
        )
    }

fun <T : Any> PagingState<Int, T>.getRefreshKey(): Int? {
    return anchorPosition?.let { anchorPosition ->
        closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
}
