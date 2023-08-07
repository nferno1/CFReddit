package nferno1.cfreddit.data.remote.response

import nferno1.cfreddit.domain.model.AbstractRedditEntity

interface MapData {

    fun map(): AbstractRedditEntity
}