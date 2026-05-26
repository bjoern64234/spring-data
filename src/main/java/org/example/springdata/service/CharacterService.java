package org.example.springdata.service;

import org.example.springdata.dto.CharacterDto;
import org.example.springdata.model.Character;
import org.example.springdata.repository.CharacterRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepo characterRepo;

    public CharacterService(CharacterRepo characterRepo) {
        this.characterRepo = characterRepo;
    }

    public List<Character> getAllCharacters(String name) {

        if (name == null) {
            return this.characterRepo.findAll();
        }

        return this.characterRepo.findCharacterByNameContainsIgnoreCase(name);
    }

    public Character getCharacterById(String id) {
        return this.characterRepo.findById(id).orElse(null);
    }


    public Character saveCharacter(String id, CharacterDto requestBody) {
        Character newCharacter = Character.builder().build()
                .withId(id)
                .withName(requestBody.name())
                .withAge(requestBody.age())
                .withProfession(requestBody.profession());

        this.characterRepo.save(newCharacter);

        return newCharacter;
    }

    public Character updateCharacter(String id, CharacterDto requestBody) {
        Character character = this.characterRepo.findById(id).orElse(null);

        if (character == null) {
            return null;
        }

        Character updatedCharacter = character
                .withName(requestBody.name())
                .withAge(requestBody.age())
                .withProfession(requestBody.profession());

        this.characterRepo.save(updatedCharacter);

        return updatedCharacter;
    }

    public boolean deleteCharacter(String id) {
        Character character = this.characterRepo.findById(id).orElse(null);

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
