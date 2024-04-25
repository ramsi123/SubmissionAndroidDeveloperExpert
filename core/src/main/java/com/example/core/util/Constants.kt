package com.example.core.util

import androidx.annotation.StringRes
import com.example.core.R

object Constants {
    const val BASE_URL = "https://api.github.com/"
    const val EXTRA_USER = "extra_user"
    const val ARG_SECTION_NUMBER = "section_number"
    const val ARG_USERNAME = "username"

    @StringRes
    val TAB_TITLES = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )
}