/*
 * Copyright (C) 2019 by onlymash <im@fiepi.me>, All rights reserved
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package onlymash.flexbooru

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import onlymash.flexbooru.App.Companion.app

class Settings(private val sp: SharedPreferences) {
    companion object {
        private var instance: Settings? = null
        fun instance(): Settings {
            if (instance == null) {
                instance = Settings(app.sp)
            }
            return instance!!
        }
        const val SAFE_MODE_KEY = "settings_safe_mode"
        const val PAGE_SIZE_KEY = "settings_page_size"
        const val BROWSE_SIZE_KEY = "settings_browse_size"
        const val DOWNLOAD_SIZE_KEY = "settings_download_size"
        const val POST_SIZE_SAMPLE = "sample"
        const val POST_SIZE_LARGER = "larger"
        const val POST_SIZE_ORIGIN = "origin"
        const val THEME_MODE_KEY = "settings_theme_mode"
        const val THEME_MODE_SYSTEM = "system"
        const val THEME_MODE_AUTO = "auto"
        const val THEME_MODE_DAY = "day"
        const val THEME_MODE_NIGHT = "night"
        const val GRID_WIDTH = "settings_grid_width"
        const val GRID_WIDTH_SMALL = "small"
        const val GRID_WIDTH_NORMAL = "normal"
        const val GRID_WIDTH_LARGE = "large"
    }

    var activeBooruUid: Long
        get() = sp.getLong(Constants.ACTIVE_BOORU_UID_KEY, -1)
        set(value) = sp.edit().putLong(Constants.ACTIVE_BOORU_UID_KEY, value).apply()

    var safeMode: Boolean
        get() = sp.getBoolean(SAFE_MODE_KEY, true)
        set(value) = sp.edit().putBoolean(SAFE_MODE_KEY, value).apply()

    var pageSize: Int
        get() = sp.getString(PAGE_SIZE_KEY, "10")!!.toInt()
        set(value) = sp.edit().putString(PAGE_SIZE_KEY, value.toString()).apply()

    var browseSize: String
        get() = sp.getString(BROWSE_SIZE_KEY, POST_SIZE_SAMPLE) ?: POST_SIZE_SAMPLE
        set(value) = sp.edit().putString(BROWSE_SIZE_KEY, value).apply()

    var downloadSize: String
        get() = sp.getString(DOWNLOAD_SIZE_KEY, POST_SIZE_SAMPLE) ?: POST_SIZE_SAMPLE
        set(value) = sp.edit().putString(DOWNLOAD_SIZE_KEY, value).apply()

    private var themeModeString: String
        get() = sp.getString(THEME_MODE_KEY, THEME_MODE_SYSTEM) ?: THEME_MODE_SYSTEM
        set(value) = sp.edit().putString(THEME_MODE_KEY, value).apply()

    @AppCompatDelegate.NightMode
    val themeMode: Int
        get() = when (themeModeString) {
            THEME_MODE_AUTO -> AppCompatDelegate.MODE_NIGHT_AUTO
            THEME_MODE_DAY -> AppCompatDelegate.MODE_NIGHT_NO
            THEME_MODE_NIGHT -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

    var gridWidth: String
        get() = sp.getString(GRID_WIDTH, GRID_WIDTH_NORMAL) ?: GRID_WIDTH_NORMAL
        set(value) = sp.edit().putString(GRID_WIDTH, value).apply()
}