package es.bancosantander.rssreader.data.domain.mapper

interface Mapper<OUT, IN> {
    abstract fun map(input: IN): OUT
    abstract fun mapJSON(input: IN): OUT
}