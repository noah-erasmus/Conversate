package com.example.conversate.model

import com.google.type.DateTime
import java.time.LocalDateTime
import java.util.*

class Memo (
    val content: String = "",
    val user: String = "",
    val datetime: String = "",
    val isChecked: Boolean = false
)