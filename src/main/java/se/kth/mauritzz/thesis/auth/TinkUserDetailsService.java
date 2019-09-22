package se.kth.mauritzz.thesis.auth;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import se.kth.mauritzz.thesis.models.repositories.UserRepository;
import se.kth.mauritzz.thesis.tinkapi.unauthenticated.TinkUnauthenticatedApi;

@AllArgsConstructor
public class TinkUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(TinkUserDetailsService.class);
    private final UserRepository userRepository;
    private final TinkUnauthenticatedApi unauthenticatedApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Received request for user {}", username);
        var user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("ID not found."));
        var token = unauthenticatedApi.authenticateUser(user);
        user.initApi(token);

        return user;
    }

}
