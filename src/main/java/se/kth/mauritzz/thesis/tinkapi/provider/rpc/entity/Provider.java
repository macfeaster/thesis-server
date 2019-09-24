package se.kth.mauritzz.thesis.tinkapi.provider.rpc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import se.kth.mauritzz.thesis.tinkapi.entity.Field;
import se.kth.mauritzz.thesis.tinkapi.provider.ProviderRepository;

import java.util.List;

@Getter
public class Provider{

	@JsonProperty("displayDescription")
	private String displayDescription;

	@JsonProperty("images")
	private Images images;

	@JsonProperty("capabilities")
	private List<String> capabilities;

	@JsonProperty("authenticationFlow")
	private String authenticationFlow;

	@JsonProperty("displayName")
	private String displayName;

	@JsonProperty("credentialsType")
	private String credentialsType;

	@JsonProperty("type")
	private String type;

	@JsonProperty("financialInstitutionName")
	private String financialInstitutionName;

	@JsonProperty("passwordHelpText")
	private String passwordHelpText;

	@JsonProperty("accessType")
	private String accessType;

	@JsonProperty("market")
	private String market;

	@JsonProperty("groupDisplayName")
	private String groupDisplayName;

	@JsonProperty("financialInstitutionId")
	private String financialInstitutionId;

	@JsonProperty("multiFactor")
	private Object multiFactor;

	@JsonProperty("name")
	private String name;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("fields")
	private List<Field> fields;

	@JsonProperty("popular")
	private Object popular;

	@JsonProperty("transactional")
	private Object transactional;

	@JsonProperty("status")
	private String status;

	@JsonIgnore
	public boolean isAuto() {
		return ProviderRepository.autoProviders.contains(name);
	}
}