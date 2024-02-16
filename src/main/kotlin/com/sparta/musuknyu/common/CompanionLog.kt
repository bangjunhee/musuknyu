package com.sparta.musuknyu.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory


class CompanionLog {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
