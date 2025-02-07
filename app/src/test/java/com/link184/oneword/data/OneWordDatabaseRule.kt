package com.link184.oneword.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.link184.oneword.OneWordDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class OneWordDatabaseRule: TestWatcher() {
    private lateinit var driver: JdbcSqliteDriver
    lateinit var database: OneWordDatabase
        private set

    override fun starting(description: Description) {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        OneWordDatabase.Schema.create(driver)
        database = OneWordDatabase(driver)
    }

    override fun finished(description: Description) {
        driver.close()
    }
}