package org.example.springdata.contoller;

import org.example.springdata.model.Character;
import org.example.springdata.repository.CharacterRepo;
import org.example.springdata.service.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/asterix")
public class AsterixController {

    private final CharacterService characterService;

    public AsterixController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/characters")
    public List<Character> getAllCharacters(@RequestParam(required = false) String name) {
        return this.characterService.getAllCharacters(name);
    }

    @GetMapping("/character/{id}")
    public Character getCharacterById(@PathVariable String id) {
        return this.characterService.getCharacterById(id);
    }

    @PostMapping("/character/new")
    public Character saveCharacter(@RequestBody Character requestBody) {
        return this.characterService.saveCharacter(requestBody);
    }

    @PutMapping("/character/{id}")
    public Character updateCharacter(@PathVariable String id, @RequestBody Character requestBody) {
        return this.characterService.updateCharacter(id, requestBody);
    }

    @DeleteMapping("/character/{id}")
    public boolean deleteCharacter(@PathVariable String id) {
        return this.characterService.deleteCharacter(id);
    }

    @GetMapping("/character/average-age")
    public double getAverageAgeByProfession(@RequestParam String profession) {
        return this.characterService.getAverageAgeByProfession(profession);
    }
}
