package com.davidsantiagoiriarte.data

import com.davidsantiagoiriarte.data.db.daos.RecentSearchDao
import com.davidsantiagoiriarte.data.db.models.RecentSearch
import com.davidsantiagoiriarte.data.repositories.RecentSearchRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class RecentSearchRepositoryTest {

    @MockK
    lateinit var recentSearchDao: RecentSearchDao

    private val recentSearchFakeList = listOf(
        RecentSearch("Search1"),
        RecentSearch("Search2"),
        RecentSearch("Search3")
    )

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { recentSearchDao.getRecentSearch() } returns flow {
            emit(recentSearchFakeList)
        }
        every { runBlocking { recentSearchDao.insert(any()) } } returns Unit
    }

    @Test
    fun shouldGeRecentSearchResults() = runBlocking {
        //Given the RecentSearchRepositoryImpl
        val repository = RecentSearchRepositoryImpl(recentSearchDao)

        // When the repository emits values
        val resultList =
            repository.getRecentSearch().toList().first()// Returns the first item in the flow

        // Then should get 3 results
        assertEquals(3, resultList.size)
    }

    @Test
    fun shouldGeRecentSearchValues() = runBlocking {
        //Given the RecentSearchRepositoryImpl
        val repository = RecentSearchRepositoryImpl(recentSearchDao)

        // When the repository emits values
        val resultList =
            repository.getRecentSearch().toList().first()// Returns the first item in the flow

        // Then should get match expected results after transformation
        assertEquals("Search1", resultList[0])
        assertEquals("Search2", resultList[1])
        assertEquals("Search3", resultList[2])
    }

    @Test
    fun shouldInsertSearch() = runBlocking {
        //Given the RecentSearchRepositoryImpl
        val repository = RecentSearchRepositoryImpl(recentSearchDao)
        try {
            // When insert fun is called
            repository.insert("SearchTest")
            //Then should insert with no errors
            assert(true)
        } catch (ex: Exception) {
            assert(false)
        }

    }
}