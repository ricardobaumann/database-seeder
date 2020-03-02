package com.github.ricardobaumann.databaseseeder.listeners

import mu.KLogging
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.stereotype.Component

@Component
class JobCompletionNotificationListener : JobExecutionListenerSupport() {

    companion object : KLogging()

    override fun afterJob(jobExecution: JobExecution) {
        logger.info("Job is {}", jobExecution.status)
    }

}