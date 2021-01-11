package br.com.fornaro.mesanews.domain.models

import br.com.fornaro.mesanews.domain.enums.FeedFilter

data class FilterContent(
    var type: FeedFilter = FeedFilter.DATE,
    var text: String = ""
)
