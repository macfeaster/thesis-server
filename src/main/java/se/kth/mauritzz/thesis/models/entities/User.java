package se.kth.mauritzz.thesis.models.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.kth.mauritzz.thesis.tinkapi.TinkApi;
import se.kth.mauritzz.thesis.tinkapi.unauthenticated.TokenResponse;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class User implements UserDetails {

    public static final String PASSWORD = "thesis";

    @Id
    private String id;

    public User(String id) {
        this.id = id;
    }

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Transient
    private TinkApi api;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void initApi(TokenResponse tokenResponse) {
        this.api = new TinkApi(tokenResponse.getAccessToken());
    }

    public TinkApi getApi() {
        return Optional.ofNullable(api).orElseThrow(NoSuchElementException::new);
    }
}
