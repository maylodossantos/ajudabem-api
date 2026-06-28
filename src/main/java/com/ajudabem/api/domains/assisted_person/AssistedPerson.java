package com.ajudabem.api.domains.assisted_person;

import com.ajudabem.api.domains.EntityBase;
import com.ajudabem.api.domains.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assisted_people")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssistedPerson extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "created_user_id", nullable = false)
    private User author;

    private String full_name;
    private Integer age;
    private Gender gender;

    private RiskLevel riskLevel;

    private String notes;

    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String zip_code;
    private String country;
}
