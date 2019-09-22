package se.kth.mauritzz.thesis.tinkapi.provider.rpc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Images{

	@JsonProperty("icon")
	private String icon;

	@JsonProperty("banner")
	private String banner;
}