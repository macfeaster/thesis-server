package se.kth.mauritzz.thesis.tinkapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import se.kth.mauritzz.thesis.annotations.JsonObject;

@Data
@JsonObject
public class Profile{

	@JsonProperty("cashbackEnabled")
	private boolean cashbackEnabled;

	@JsonProperty("market")
	private String market;

	@JsonProperty("periodAdjustedDay")
	private int periodAdjustedDay;

	@JsonProperty("periodMode")
	private String periodMode;

	@JsonProperty("timeZone")
	private String timeZone;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("locale")
	private String locale;

}