package se.kth.mauritzz.thesis.batch;

import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import se.kth.mauritzz.thesis.models.entities.User;
import se.kth.mauritzz.thesis.models.repositories.UserRepository;

import java.util.List;

@Component
public class UserReader extends AbstractItemCountingItemStreamItemReader<User> {

    private UserRepository userRepository;
    private List<User> users;

    @Autowired
    public UserReader(UserRepository userRepository) {
        this.userRepository = userRepository;
        setName("userReader");
    }

    @Nullable
    @Override
    protected User doRead() {
        return (getCurrentItemCount() <= users.size())
                ? users.get(getCurrentItemCount() - 1)
                : null;
    }

    @Override
    protected void doOpen() {
        users = userRepository.findAll();
        int count = users.size();

        if (count > 0)
            setMaxItemCount(count);
    }

    @Override
    protected void doClose() {
        users = null;
    }

}