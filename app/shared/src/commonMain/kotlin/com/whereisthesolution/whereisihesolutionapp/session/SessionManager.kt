package com.whereisthesolution.whereisihesolutionapp.session

class SessionManager {

    private var loggedUserId: Long? = null

    fun login(userId: Long) {
        loggedUserId = userId
    }

    fun getLoggedUserId(): Long? {
        return loggedUserId
    }

    fun logout() {
        loggedUserId = null
    }

}