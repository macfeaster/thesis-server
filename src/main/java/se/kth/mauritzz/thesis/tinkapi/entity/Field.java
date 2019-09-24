package se.kth.mauritzz.thesis.tinkapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import se.kth.mauritzz.thesis.annotations.JsonObject;

import java.util.List;

@JsonObject
@Getter
public class Field {

	@JsonProperty("helpText")
	private String helpText;

	@JsonProperty("patternError")
	private String patternError;

	@JsonProperty("defaultValue")
	private String defaultValue;

	@JsonProperty("masked")
	private boolean masked;

	@JsonProperty("minLength")
	private int minLength;

	@JsonProperty("pattern")
	private String pattern;

	@JsonProperty("description")
	private String description;

	@JsonProperty("numeric")
	private boolean numeric;

	@JsonProperty("optional")
	private boolean optional;

	@JsonProperty("sensitive")
	private boolean sensitive;

	@JsonProperty("immutable")
	private boolean immutable;

	@JsonProperty("hint")
	private String hint;

	@JsonProperty("additionalInfo")
	private String additionalInfo;

	@JsonProperty("checkbox")
	private boolean checkbox;

	@JsonProperty("name")
	private String name;

	@JsonProperty("options")
	private List<String> options;

	@JsonProperty("value")
	private String value;

	@JsonProperty("maxLength")
	private int maxLength;

	@JsonIgnore
	public String getDisplayType() {
		if (masked) return "password";
		if (numeric) return "number";
		return "text";
	}

	public int getMaxLength() {
		return (maxLength == 0) ? 1000 : maxLength;
	}
}