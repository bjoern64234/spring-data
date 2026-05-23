package org.example.springdata.contoller;

import org.example.springdata.model.Character;
import org.example.springdata.repository.CharacterRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/asterix")
public class AsterixController {

    private final CharacterRepo characterRepo;

    public AsterixController(CharacterRepo characterRepo) {
        this.characterRepo = characterRepo;
    }

    @GetMapping("/characters")
    public List<Character> getAllCharacters(@RequestParam(required = false) String name) {

        if (name == null) {
            return this.characterRepo.findAll();
        }

        return this.characterRepo.findCharacterByNameContainsIgnoreCase(name);
    }

    @GetMapping("/character/{id}")
    public Character getCharacterById(@PathVariable String id) {
        return this.characterRepo.findById(id).orElse(null);
    }

    @PostMapping("/character/new")
    public Character saveCharacter(@RequestBody Character requestBody) {
        Character newCharacter = Character.builder().build()
                .withId(UUID.randomUUID().toString())
                .withName(requestBody.name())
                .withAge(requestBody.age())
                .withProfession(requestBody.profession());

        this.characterRepo.save(newCharacter);

        return newCharacter;
    }

    @PutMapping("/character/{id}")
    public Character updateCharacter(@PathVariable String id, @RequestBody Character requestBody) {
        Character character = this.characterRepo.findById(id).orElse(null);

        if (character == null) {
            return null;
        }

        Character updatedCharacter = character.withName(requestBody.name()).withAge(requestBody.age()).withProfession(requestBody.profession());

        this.characterRepo.save(updatedCharacter);

        return updatedCharacter;
    }

    @DeleteMapping("/character/{id}")
    public boolean deleteCharacter(@PathVariable String id) {
        Character character = this.characterRepo.findById(id).orElse(null);

        if (character == null) {
            return false;
        }

        this.characterRepo.delete(character);

        return true;
    }

    @GetMapping("/character/average-age")
    public double getAverageAgeByProfession(@RequestParam String profession) {
        return this.characterRepo.findCharactersByProfessionContainsIgnoreCase(profession).stream()
                .mapToInt(Character::age)
                .average()
                .orElse(0.0);
    }
}
