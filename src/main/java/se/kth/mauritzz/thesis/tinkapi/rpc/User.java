package se.kth.mauritzz.thesis.tinkapi.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import se.kth.mauritzz.thesis.annotations.JsonObject;
import se.kth.mauritzz.thesis.tinkapi.rpc.entity.Profile;

import java.util.List;

@Data
@JsonObject
public class User {

	@JsonProperty("password")
	private String password;

	@JsonProperty("nationalId")
	private String nationalId;

	@JsonProperty("created")
	private String created;

	@JsonProperty("profile")
	private Profile profile;

	@JsonProperty("flags")
	private List<String> flags;

	@JsonProperty("id")
	private String id;

	@JsonProperty("username")
	private String username;
}