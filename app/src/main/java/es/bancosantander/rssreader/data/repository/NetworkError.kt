package es.bancosantander.rssreader.data.repository

enum class NetworkError {
    SUCCESS,
    DISCONNECTED,
    BAD_URL,
    NOT_A_FEED,
    UNKNOWN
}
