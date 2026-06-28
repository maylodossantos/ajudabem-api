package com.ajudabem.api.dto.assited_person;

import com.ajudabem.api.domains.assisted_person.Gender;

import java.util.List;

public record AssistedPersonRequestDTO (String full_name, Integer age, Gender gender, List<Long> tagIds, String notes, String street, String number, String neighborhood, String city, String state, String zip_code, String country) { }