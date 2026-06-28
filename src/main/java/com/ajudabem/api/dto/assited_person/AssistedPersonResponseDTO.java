package com.ajudabem.api.dto.assited_person;

import com.ajudabem.api.domains.assisted_person.AssistedPerson;
import com.ajudabem.api.domains.assisted_person.Gender;
import com.ajudabem.api.domains.assisted_person.RiskLevel;
import com.ajudabem.api.dto.user.UserResponseSummaryDTO;

public record AssistedPersonResponseDTO(Long id, String full_name, Integer age, Gender gender, RiskLevel riskLevel, UserResponseSummaryDTO author, String notes, String street, String number, String neighborhood, String city, String state, String zip_code, String country) {
    public static AssistedPersonResponseDTO fromEntity(AssistedPerson person) {
        return new AssistedPersonResponseDTO(
                person.getId(),
                person.getFull_name(),
                person.getAge(),
                person.getGender(),
                person.getRiskLevel(),
                UserResponseSummaryDTO.fromEntity(person.getAuthor()),
                person.getNotes(),
                person.getStreet(),
                person.getNumber(),
                person.getNeighborhood(),
                person.getCity(),
                person.getState(),
                person.getZip_code(),
                person.getCountry()

        );
    }
}