package com.ithirteeng.secondpatternsclientproject.domain.exchange.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs")
data class CurrencyRate(
    @field:Attribute(name = "Date")
    var date: String = "",
    @field:Attribute(name = "name")
    var name: String = "",
    @field:ElementList(inline = true, required = false)
    var valuteStrings: ArrayList<ValuteString> = arrayListOf(),
)
