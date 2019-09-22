package se.kth.mauritzz.thesis.batch;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import se.kth.mauritzz.thesis.models.entities.Transaction;
import se.kth.mauritzz.thesis.models.entities.User;
import se.kth.mauritzz.thesis.tinkapi.unauthenticated.TinkUnauthenticatedApi;

import java.util.List;

@Component
@AllArgsConstructor
public class TinkApiProcessor implements ItemProcessor<User, List<Transaction>> {

    private TinkUnauthenticatedApi unauthenticatedApi;

    @Override
    public List<Transaction> process(User user) {
        var token = unauthenticatedApi.authenticateUser(user);
        user.initApi(token);

        return user.getApi().getTransactions();
    }

}
