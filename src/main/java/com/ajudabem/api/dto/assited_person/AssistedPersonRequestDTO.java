package com.ajudabem.api.dto.assited_person;

import com.ajudabem.api.domains.assisted_person.Gender;

public record AssistedPersonRequestDTO (String full_name, Integer age, Gender gender, String notes, String street, String number, String neighborhood, String city, String state, String zip_code, String country) { }