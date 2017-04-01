package com.orogersilva.superpub.dublin.data.local

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.model.Pub
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by orogersilva on 3/31/2017.
 */
@RunWith(AndroidJUnit4::class)
class PubLocalDataSourceTest {

    // region SETUP / TEARDOWN CLASS METHODS

    companion object {

        private lateinit var context: Context
        private var pubLocalDataSource: PubDataSource? = null

        @BeforeClass @JvmStatic fun setupClass() {

            context = InstrumentationRegistry.getTargetContext()

            Realm.init(context)

            val realmConfiguration = RealmConfiguration.Builder()
                    .name("superpub.realm")
                    .build()

            val realm = Realm.getInstance(realmConfiguration)

            pubLocalDataSource = PubLocalDataSource.getInstance(realm)
        }

        @AfterClass @JvmStatic fun teardownClass() {

            PubLocalDataSource.destroyInstance()
            pubLocalDataSource = null
        }
    }

    // endregion

    // region TEST METHODS

    @Test fun getPubs_whenDatabaseIsClean_doesNotReturnsPubs() {

        val testObserver = TestObserver<List<Pub>>()

        pubLocalDataSource?.getPubs()?.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        assertTrue(testObserver.values()[0].isEmpty())
    }

    // endregion
}