package se.kth.mauritzz.thesis.models.repositories;

import org.springframework.data.repository.CrudRepository;
import se.kth.mauritzz.thesis.models.entities.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {

    List<User> findAll();

}
