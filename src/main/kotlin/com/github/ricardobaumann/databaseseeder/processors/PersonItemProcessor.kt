package com.github.ricardobaumann.databaseseeder.processors

import com.github.ricardobaumann.databaseseeder.models.IndexDocument
import com.github.ricardobaumann.databaseseeder.models.Person
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class PersonItemProcessor : ItemProcessor<Person, IndexDocument> {
    override fun process(item: Person) =
            item.takeIf { it.id != null && it.name != null }
                    ?.let {
                        IndexDocument(id = it.id!!, name = it.name!!)
                    }
}