package com.fjr619.newsloc.data.preference

import com.fjr619.newsloc.domain.preferences.LocalUserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AppPreferenceImplFake: LocalUserPreferences {

  var data = false

  override suspend fun saveAppEntry() {
    data = true
  }

  override fun readAppEntry(): Flow<Boolean> {
    return flowOf(data)
  }
}