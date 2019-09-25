package se.kth.mauritzz.thesis.batch;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import se.kth.mauritzz.thesis.models.entities.Transaction;
import se.kth.mauritzz.thesis.models.entities.User;
import se.kth.mauritzz.thesis.tinkapi.entity.Credential;
import se.kth.mauritzz.thesis.tinkapi.unauthenticated.TinkUnauthenticatedApi;

import java.util.List;

@Component
@AllArgsConstructor
public class TinkApiProcessor implements ItemProcessor<User, List<Transaction>> {

    private static final Logger logger = LoggerFactory.getLogger(TinkApiProcessor.class);
    private static final int MAX_POLLS = 30;
    private TinkUnauthenticatedApi unauthenticatedApi;

    @Override
    public List<Transaction> process(User user) throws InterruptedException {
        var token = unauthenticatedApi.authenticateUser(user);
        user.initApi(token);

        // Trigger refresh for all credentials
        user.getApi()
                .getCredentials()
                .stream()
                .filter(Credential::isAuto)
                .map(Credential::getId)
                .forEach(id -> user.getApi().refreshCredential(id));

        for (int i = 0; i < MAX_POLLS; i++) {
            boolean allCredentialsUpdated = user.getApi()
                    .getCredentials()
                    .stream()
                    .peek(c -> logger.info("{} [{}]: {}", c.getProviderName(), c.getId(), c.getStatus()))
                    .allMatch(c -> c.getStatus() == Credential.Status.UPDATED);

            if (allCredentialsUpdated) break;
            Thread.sleep(2000);
        }

        return user.getApi().getTransactions();
    }

}
