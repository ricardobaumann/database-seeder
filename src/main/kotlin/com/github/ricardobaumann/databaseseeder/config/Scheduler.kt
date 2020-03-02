package com.github.ricardobaumann.databaseseeder.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.*

@Configuration
@EnableScheduling
class Scheduler(private val jobLauncher: JobLauncher,
                private val importPeopleJob: Job) {

    @Scheduled(fixedRate = 5000L)
    fun schedule() {
        jobLauncher.run(
                importPeopleJob,
                JobParametersBuilder().addDate("startedAt", Date()).toJobParameters()
        )
    }

}