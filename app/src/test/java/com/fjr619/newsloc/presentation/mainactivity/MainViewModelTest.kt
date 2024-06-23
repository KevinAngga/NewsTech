package com.fjr619.newsloc.presentation.mainactivity

import androidx.compose.material.icons.materialIcon
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.fjr619.newsloc.MainCoroutineExtension
import com.fjr619.newsloc.data.preference.AppPreferenceImplFake
import com.fjr619.newsloc.domain.usecase.appentry.AppEntryUseCases
import com.fjr619.newsloc.domain.usecase.appentry.ReadAppEntry
import com.fjr619.newsloc.domain.usecase.appentry.SaveAppEntry
import com.fjr619.newsloc.presentation.navgraph.Route
import com.fjr619.newsloc.presentation.onboarding.OnboardingViewModel
import com.fjr619.newsloc.presentation.onboarding.components.OnboardingEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class MainViewModelTest {

  private lateinit var appEntryUseCases: AppEntryUseCases
  private lateinit var readAppEntry: ReadAppEntry
  private lateinit var saveAppEntry: SaveAppEntry
  private lateinit var appPreferenceImplFake: AppPreferenceImplFake
  private lateinit var mainViewModel: MainViewModel


  @BeforeEach
  fun setUp() {
    appPreferenceImplFake = AppPreferenceImplFake()
    readAppEntry = ReadAppEntry(appPreferenceImplFake)
    saveAppEntry = SaveAppEntry(appPreferenceImplFake)
    appEntryUseCases = AppEntryUseCases(
      readAppEntry, saveAppEntry
    )
    mainViewModel = MainViewModel(appEntryUseCases)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `Test read appEntry with value true`() = runTest {


  }

}