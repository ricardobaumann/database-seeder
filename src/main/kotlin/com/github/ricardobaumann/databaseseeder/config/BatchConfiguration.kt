package com.github.ricardobaumann.databaseseeder.config

import com.github.ricardobaumann.databaseseeder.listeners.JobCompletionNotificationListener
import com.github.ricardobaumann.databaseseeder.models.IndexDocument
import com.github.ricardobaumann.databaseseeder.models.Person
import com.github.ricardobaumann.databaseseeder.processors.PersonItemProcessor
import mu.KLogging
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManagerFactory

@Configuration
@EnableBatchProcessing
class BatchConfiguration(private val jobBuilderFactory: JobBuilderFactory,
                         private val stepBuilderFactory: StepBuilderFactory) {

    companion object : KLogging()

    @Bean
    fun jpaPagingItemReader(entityManagerFactory: EntityManagerFactory): JpaPagingItemReader<Person> = JpaPagingItemReader<Person>().also {
        it.setQueryString("select p from Person p")
        it.setEntityManagerFactory(entityManagerFactory)
        it.pageSize = 10
        it.afterPropertiesSet()
        it.isSaveState = true
    }

    @Bean
    fun writer() = ItemWriter<IndexDocument> { list ->
        list.forEach {
            logger.info("Writing {}", it)
        }
    }


    @Bean
    fun importPeopleJob(jobCompletionNotificationListener: JobCompletionNotificationListener, step1: Step) =
            jobBuilderFactory.get("importPeopleJob")
                    .incrementer(RunIdIncrementer())
                    .listener(jobCompletionNotificationListener)
                    .flow(step1)
                    .end()
                    .build()

    @Bean
    fun step1(writer: ItemWriter<IndexDocument>, jpaPagingItemReader: JpaPagingItemReader<Person>, personItemProcessor: PersonItemProcessor) =
            stepBuilderFactory.get("step1")
                    .chunk<Person, IndexDocument>(10)
                    .reader(jpaPagingItemReader)
                    .processor(personItemProcessor)
                    .writer(writer)
                    .build()
}