package se.kth.mauritzz.thesis.tinkapi.unauthenticated;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class TokenResponse {

	@JsonIgnore
	private LocalDateTime expires;

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("id_hint")
	private Object idHint;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private int expiresIn;

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
		this.expires = LocalDateTime.now().plusSeconds(expiresIn - 60);
	}

	@JsonIgnore
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expires);
	}

	@JsonIgnore
	public String getTokenPart() {
		return Optional.ofNullable(accessToken)
				.map(token -> token.substring(token.length() - 10, token.length() - 1))
				.orElse("");
	}

}