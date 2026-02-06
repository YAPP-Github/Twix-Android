package com.twix.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

internal val Context.authDataStore: DataStore<AuthConfigure> by dataStore(
    fileName = "auth-configure.json",
    serializer = AuthConfigureSerializer,
)
