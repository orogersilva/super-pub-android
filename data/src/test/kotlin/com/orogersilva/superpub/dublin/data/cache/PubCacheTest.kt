package com.orogersilva.superpub.dublin.data.cache

import com.orogersilva.superpub.dublin.data.BaseTestCase
import com.orogersilva.superpub.dublin.data.cache.impl.PubCacheImpl
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by orogersilva on 5/29/2017.
 */
class PubCacheTest : BaseTestCase() {

    // region PROPERTIES

    private lateinit var cacheCore: MutableMap<Long, PubEntity>

    private var pubCache: PubCache? = null

    // endregion

    // region SETUP METHODS

    @Before fun setup() {

        cacheCore = mutableMapOf()

        pubCache = PubCacheImpl(cacheCore)
    }

    // endregion

    // region TEST METHODS

    @Test fun `Get pubs, when there is not cached data, then returns no pub`() {

        // ACT

        val data = pubCache?.getPubs()

        // ASSERT

        data?.isEmpty()?.let { assertTrue(it) }
    }

    @Test fun `Get pubs, when there is cached data, then returns pubs`() {

        // ARRANGE

        val expectedData = createTestData()

        expectedData.forEach {
            cacheCore.put(it.id, it)
        }

        // ACT

        val data = pubCache?.getPubs()

        // ASSERT

        data?.forEach {
            assertTrue(expectedData.contains(it))
        }
    }

    @Test fun `Save pub, when try cache data, then pub is cached`() {

        // ARRANGE

        val data = createTestData()

        // ACT

        pubCache?.savePub(data[0])

        // ASSERT

        assertEquals(data[0], cacheCore[data[0].id])
    }

    @Test fun `Save pub, when data is cached twice, then pub is updated`() {

        // ARRANGE

        val data = createTestData()

        val UPDATED_COVER_IMAGE_URL = "http://www.orogersilva.com/4"
        val UPDATED_PICTURE_IMAGE_URL = "http://www.orogersilva.com/14"

        // ACT / ASSERT

        pubCache?.savePub(data[0])

        assertEquals(data[0], cacheCore[data[0].id])

        data[0].coverImageUrl = UPDATED_COVER_IMAGE_URL
        data[0].pictureImageUrl = UPDATED_PICTURE_IMAGE_URL

        pubCache?.savePub(data[0])

        assertEquals(data[0], cacheCore[data[0].id])
    }

    @Test fun `Clear, when cache is clear, then there is not more cached pubs`() {

        // ARRANGE

        val data = createTestData()

        data.forEach {
            cacheCore.put(it.id, it)
        }

        // ACT

        pubCache?.clear()

        // ASSERT

        assertTrue(cacheCore.isEmpty())
    }

    // endregion

    // region TEARDOWN METHODS

    @After fun teardown() {

        cacheCore.clear()

        pubCache = null
    }

    // endregion
}