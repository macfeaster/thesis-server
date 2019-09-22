package se.kth.mauritzz.thesis.batch;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.kth.mauritzz.thesis.models.entities.Transaction;
import se.kth.mauritzz.thesis.models.entities.User;

import java.util.List;

@Configuration
@AllArgsConstructor
public class BatchConfig {

    private final UserReader userReader;
    private final TinkApiProcessor tinkApiProcessor;
    private final TransactionWriter transactionWriter;

    @Bean
    public Job fetchData(JobBuilderFactory jobBuilderFactory, Step fetchTransactions) {
        return jobBuilderFactory.get("fetchTransactions")
                .start(fetchTransactions)
                .build();
    }

    @Bean
    public Step fetchTransactions(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("fetchTransactions")
                .<User, List<Transaction>> chunk(10)
                .reader(userReader)
                .processor(tinkApiProcessor)
                .writer(transactionWriter)
                .build();
    }

}
