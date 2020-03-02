package com.github.ricardobaumann.databaseseeder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DatabaseSeederApplication

fun main(args: Array<String>) {
	runApplication<DatabaseSeederApplication>(*args)
}
