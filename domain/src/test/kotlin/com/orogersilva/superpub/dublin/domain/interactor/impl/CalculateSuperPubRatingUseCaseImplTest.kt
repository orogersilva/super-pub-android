package com.orogersilva.superpub.dublin.domain.interactor.impl

import com.orogersilva.superpub.dublin.domain.interactor.CalculateSuperPubRatingUseCase
import com.orogersilva.superpub.dublin.domain.model.Pub
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

/**
 * Created by orogersilva on 7/17/2017.
 */
class CalculateSuperPubRatingUseCaseImplTest {

    // region PROPERTIES

    private lateinit var calculateSuperPubRatingUseCase: CalculateSuperPubRatingUseCase

    // endregion

    // region SETUP METHOD

    @Before fun setup() {

        calculateSuperPubRatingUseCase = CalculateSuperPubRatingUseCaseImpl()
    }

    // endregion

    // region TEST METHODS

    @Test fun `Calculate Super Pub rating, when there is not pubs, then returns nothing`() {

        // ARRANGE

        val EMITTED_EVENTS_COUNT = 1

        val emptyPubsList = listOf<Pub>()

        val testSubscriber = TestSubscriber<Pub>()

        // ACT

        calculateSuperPubRatingUseCase.calculateSuperPubRating(emptyPubsList)
                .subscribe(testSubscriber)

        // ASSERT

        testSubscriber.assertComplete()
        testSubscriber.assertNoValues()
    }

    // endregion
}