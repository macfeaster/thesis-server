package se.kth.mauritzz.thesis.batch;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@AllArgsConstructor
public class Scheduler {

    private final Job renewSubscriptions;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 0 9 * * *")
    public void run() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        run(9);
    }

    public void run(int hour) throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        Date date = getJobParameters(hour);

        jobLauncher.run(renewSubscriptions, new JobParametersBuilder()
                .addDate("date", date)
                .toJobParameters());
    }

    private Date getJobParameters(int hour) {
        LocalDateTime runningTime = LocalDate.now().atTime(hour, 0);
        Instant instant = runningTime.toInstant(ZoneId.systemDefault().getRules().getOffset(runningTime));
        return Date.from(instant);
    }

}
