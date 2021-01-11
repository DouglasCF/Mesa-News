package br.com.fornaro.mesanews.data.source.local.cache

import br.com.fornaro.mesanews.domain.enums.FeedFilter

object FilterCache : Cache<FeedFilter> {

    override var value: FeedFilter? = FeedFilter.DATE

}