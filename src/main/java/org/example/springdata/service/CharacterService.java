package org.example.springdata.service;

import org.example.springdata.model.Character;
import org.example.springdata.repository.CharacterRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CharacterService {

    private final CharacterRepo characterRepo;

    public CharacterService(CharacterRepo characterRepo) {
        this.characterRepo = characterRepo;
    }

    public List<org.example.springdata.model.Character> getAllCharacters(String name) {

        if (name == null) {
            return this.characterRepo.findAll();
        }

        return this.characterRepo.findCharacterByNameContainsIgnoreCase(name);
    }

    public org.example.springdata.model.Character getCharacterById(String id) {
        return this.characterRepo.findById(id).orElse(null);
    }


    public org.example.springdata.model.Character saveCharacter(Character requestBody) {
        org.example.springdata.model.Character newCharacter = org.example.springdata.model.Character.builder().build()
                .withId(UUID.randomUUID().toString())
                .withName(requestBody.name())
                .withAge(requestBody.age())
                .withProfession(requestBody.profession());

        this.characterRepo.save(newCharacter);

        return newCharacter;
    }

    public org.example.springdata.model.Character updateCharacter(String id, Character requestBody) {
        org.example.springdata.model.Character character = this.characterRepo.findById(id).orElse(null);

        if (character == null) {
            return null;
        }

        org.example.springdata.model.Character updatedCharacter = character.withName(requestBody.name()).withAge(requestBody.age()).withProfession(requestBody.profession());

        this.characterRepo.save(updatedCharacter);

        return updatedCharacter;
    }

    public boolean deleteCharacter(String id) {
        org.example.springdata.model.Character character = this.characterRepo.findById(id).orElse(null);

        if (character == null) {
            return false;
        }

        this.characterRepo.delete(character);

        return true;
    }

    public double getAverageAgeByProfession(String profession) {
        return this.characterRepo.findCharactersByProfessionContainsIgnoreCase(profession).stream()
                .mapToInt(Character::age)
                .average()
                .orElse(0.0);
    }
}
