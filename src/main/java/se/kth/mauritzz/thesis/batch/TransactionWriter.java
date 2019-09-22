package se.kth.mauritzz.thesis.batch;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import se.kth.mauritzz.thesis.models.entities.Transaction;
import se.kth.mauritzz.thesis.models.repositories.TransactionRepository;

import java.util.List;

@AllArgsConstructor
@Component
public class TransactionWriter implements ItemWriter<List<Transaction>> {

    private static final Logger logger = LoggerFactory.getLogger(TransactionWriter.class);
    private final TransactionRepository transactionRepository;

    @Override
    public void write(List<? extends List<Transaction>> list) {
        list.forEach(transactionRepository::saveAll);
        logger.info("Wrote {} transactions for {} users", list.stream().mapToInt(List::size).sum(), list.size());
    }

}
