package com.beeva.views;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonView {

	private String id;
	private String name;
	private Long dateOfBirth;

	public PersonView() {

	}

	@JsonProperty
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty
	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	@NotNull
	public Long getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@JsonProperty
	public Long getAge() {

	    return ChronoUnit.YEARS.between(
                Instant.ofEpochMilli(dateOfBirth * 1000).atZone(ZoneId.systemDefault()).toLocalDate() ,
                LocalDate.now( ZoneId.systemDefault() )
        );
	}

	public void setAge(Long age) {

	}

    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PersonView that = (PersonView) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (!name.equals(that.name)) return false;
		return dateOfBirth.equals(that.dateOfBirth);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + name.hashCode();
		result = 31 * result + dateOfBirth.hashCode();
		return result;
	}

}
