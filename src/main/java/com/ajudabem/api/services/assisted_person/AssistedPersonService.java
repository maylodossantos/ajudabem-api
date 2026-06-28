package com.ajudabem.api.services.assisted_person;

import com.ajudabem.api.domains.assisted_person.AssistedPerson;
import com.ajudabem.api.domains.assisted_person.AssistedPersonTag;
import com.ajudabem.api.domains.assisted_person.RiskLevel;
import com.ajudabem.api.domains.assisted_person.Tag;
import com.ajudabem.api.domains.user.User;
import com.ajudabem.api.dto.assited_person.AssistedPersonRequestDTO;
import com.ajudabem.api.dto.assited_person.AssistedPersonResponseDTO;
import com.ajudabem.api.repositories.AssistedPersonRepository;
import com.ajudabem.api.repositories.AssistedPersonTagRepository;
import com.ajudabem.api.repositories.TagRepository;
import com.ajudabem.api.services.user.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistedPersonService {

    private final AssistedPersonRepository assistedPersonRepository;
    private final AssistedPersonTagRepository assistedPersonTagRepository;
    private final CurrentUserService currentUserService;
    private final TagRepository tagRepository;

    public AssistedPersonResponseDTO createAssistedPerson(AssistedPersonRequestDTO dto) {
        User user = currentUserService.get();

        AssistedPerson newAssistedPerson = new AssistedPerson();
        List<Tag> tags = tagRepository.findAllById(dto.tagIds());

        newAssistedPerson.setAuthor(user);

        newAssistedPerson.setFull_name(dto.full_name());
        newAssistedPerson.setAge(dto.age());
        newAssistedPerson.setGender(dto.gender());
        newAssistedPerson.setNotes(dto.notes());

        newAssistedPerson.setRiskLevel(RiskLevel.MEDIUM);

        newAssistedPerson.setStreet(dto.street());
        newAssistedPerson.setNumber(dto.number());
        newAssistedPerson.setNeighborhood(dto.neighborhood());
        newAssistedPerson.setCity(dto.city());
        newAssistedPerson.setState(dto.state());
        newAssistedPerson.setCountry(dto.country());

        assistedPersonRepository.save(newAssistedPerson);

        List<AssistedPersonTag> assistedPersonTags = tags.stream()
                .map(tag -> new AssistedPersonTag(newAssistedPerson, tag))
                .toList();

        assistedPersonTagRepository.saveAll(assistedPersonTags);

        return AssistedPersonResponseDTO.fromEntity(newAssistedPerson, assistedPersonTags);
    }

    @Transactional
    public AssistedPersonResponseDTO updateAssistedPerson(AssistedPersonRequestDTO dto, Long id) {

        AssistedPerson assistedPerson = assistedPersonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assisted person is not exists"));

        List<AssistedPersonTag> newTags = assistedPersonTagRepository.findByAssistedPerson(assistedPerson);

        if (dto.full_name() != null) assistedPerson.setFull_name(dto.full_name());
        if (dto.age() != null) assistedPerson.setAge(dto.age());
        if (dto.gender() != null) assistedPerson.setGender(dto.gender());
        if (dto.notes() != null) assistedPerson.setNotes(dto.notes());
        if (dto.street() != null) assistedPerson.setStreet(dto.street());
        if (dto.number() != null) assistedPerson.setNumber(dto.number());
        if (dto.neighborhood() != null) assistedPerson.setNeighborhood(dto.neighborhood());
        if (dto.city() != null) assistedPerson.setCity(dto.city());
        if (dto.state() != null) assistedPerson.setState(dto.state());
        if (dto.country() != null) assistedPerson.setCountry(dto.country());
        if (dto.tagIds() != null) {
            assistedPersonTagRepository.deleteByAssistedPerson(assistedPerson);
            List<Tag> tags = tagRepository.findAllById(dto.tagIds());
            newTags = tags.stream()
                    .map(tag -> new AssistedPersonTag(assistedPerson, tag))
                    .toList();
            assistedPersonTagRepository.saveAll(newTags);
        }

        assistedPersonRepository.save(assistedPerson);

        return AssistedPersonResponseDTO.fromEntity(assistedPerson, newTags);
    }

    public List<AssistedPersonResponseDTO> getAll() {
        List<AssistedPerson> assistedPersonList = assistedPersonRepository.findAll();
        List<AssistedPersonTag> assistedPersonTags = assistedPersonTagRepository.findAll();

        return assistedPersonList.stream()
                .map(person -> {
                    List<AssistedPersonTag> personTags = assistedPersonTags.stream()
                            .filter(apt -> apt.getAssistedPerson().getId().equals(person.getId()))
                            .toList();
                    return AssistedPersonResponseDTO.fromEntity(person, personTags);
                })
                .toList();
    }

    public AssistedPersonResponseDTO getAssistedPerson(Long id) {
        AssistedPerson assistedPerson = assistedPersonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assisted person is not exists"));
        List<AssistedPersonTag> tags = assistedPersonTagRepository.findByAssistedPerson(assistedPerson);

        return AssistedPersonResponseDTO.fromEntity(assistedPerson, tags);
    }

}
