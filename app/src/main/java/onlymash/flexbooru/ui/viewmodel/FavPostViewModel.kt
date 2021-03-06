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

package onlymash.flexbooru.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import onlymash.flexbooru.entity.PostDan
import onlymash.flexbooru.entity.PostMoe
import onlymash.flexbooru.repository.browse.PostLoadedLiveDataListener
import onlymash.flexbooru.repository.browse.PostLoader

class FavPostViewModel(private val postLoader: PostLoader) : ViewModel() {

    var postsDan: MediatorLiveData<MutableList<PostDan>> = MediatorLiveData()

    var postsMoe: MediatorLiveData<MutableList<PostMoe>> = MediatorLiveData()

    private val postLoadedLiveDataListener = object : PostLoadedLiveDataListener {

        override fun onDanItemsLoaded(posts: LiveData<MutableList<PostDan>>) {
            postsDan.addSource(posts) {
                postsDan.postValue(it)
            }
        }
        override fun onMoeItemsLoaded(posts: LiveData<MutableList<PostMoe>>) {
            postsMoe.addSource(posts) {
                postsMoe.postValue(it)
            }
        }
    }
    init {
        postLoader.setPostLoadedLiveDataListener(postLoadedLiveDataListener)
    }

    fun loadDanFav(host: String, username: String) {
        postLoader.loadDanPostsLiveData(host, "fav:$username")
    }

    fun loadMoeFav(host: String, username: String) {
        postLoader.loadMoePostsLiveData(host, "vote:3:$username order:vote")
    }
}