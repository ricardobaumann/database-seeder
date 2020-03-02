package com.github.ricardobaumann.databaseseeder.models

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Person(
        @Id val id: Long? = null,
        val name: String? = null
)