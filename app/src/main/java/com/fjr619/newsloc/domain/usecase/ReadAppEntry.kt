package com.fjr619.newsloc.domain.usecase

import com.fjr619.newsloc.domain.preferences.LocalUserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAppEntry @Inject constructor(
    private val localUserPreferences: LocalUserPreferences
) {
    suspend operator fun invoke(): Flow<Boolean> = localUserPreferences.readAppEntry()
}