package com.fjr619.newsloc.presentation.onboarding

import assertk.assertThat
import assertk.assertions.isTrue
import com.fjr619.newsloc.MainCoroutineExtension
import com.fjr619.newsloc.data.preference.AppPreferenceImplFake
import com.fjr619.newsloc.domain.usecase.appentry.AppEntryUseCases
import com.fjr619.newsloc.domain.usecase.appentry.ReadAppEntry
import com.fjr619.newsloc.domain.usecase.appentry.SaveAppEntry
import com.fjr619.newsloc.presentation.onboarding.components.OnboardingEvent
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class OnboardingViewModelTest {

  private lateinit var appEntryUseCases: AppEntryUseCases
  private lateinit var readAppEntry: ReadAppEntry
  private lateinit var saveAppEntry: SaveAppEntry
  private lateinit var appPreferenceImplFake: AppPreferenceImplFake
  private lateinit var onboardingViewModel: OnboardingViewModel

  @BeforeEach
  fun setUp() {
    appPreferenceImplFake = AppPreferenceImplFake()
    readAppEntry = ReadAppEntry(appPreferenceImplFake)
    saveAppEntry = SaveAppEntry(appPreferenceImplFake)
    appEntryUseCases = AppEntryUseCases(
      readAppEntry, saveAppEntry
    )
    onboardingViewModel = OnboardingViewModel(appEntryUseCases)
  }


  @Test
  fun `Test saving appEntry`() = runBlocking {
    onboardingViewModel.onEvent(OnboardingEvent.SaveAppEntry)
    //why this need to  be invoke?

    saveAppEntry.invoke()
    assertThat(appPreferenceImplFake.data).isTrue()
  }

}