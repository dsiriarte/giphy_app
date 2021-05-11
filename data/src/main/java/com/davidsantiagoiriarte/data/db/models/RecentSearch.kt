package com.davidsantiagoiriarte.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentSearch(@PrimaryKey val searchQuery: String)
