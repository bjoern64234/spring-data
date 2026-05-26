package org.example.springdata.service;

import org.example.springdata.dto.CharacterDto;
import org.example.springdata.model.Character;
import org.example.springdata.repository.CharacterRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CharacterServiceTest {

    private CharacterRepo mockRepo = Mockito.mock(CharacterRepo.class);
    private IdService mockIdSercive = Mockito.mock(IdService.class);

    @Test
    void getAllCharacters() {
        // Given
        CharacterService characterService = new CharacterService(mockRepo, mockIdSercive);
        Character c = Character.builder().id("1").name("Max").age(28).profession("Mage").build();
        List<Character> expected = new ArrayList<>(List.of(c));
        when(mockRepo.findAll()).thenReturn(expected);
        // When
        List<Character> actual = characterService.getAllCharacters(null);
        // Then
        assertEquals(expected, actual);
        verify(mockRepo).findAll();
    }

    @Test
    void getCharacterById() {
        // Given
        CharacterService characterService = new CharacterService(mockRepo, mockIdSercive);
        Character expected = Character.builder().id("1").name("Max").age(28).profession("Mage").build();
        when(mockRepo.findById("1")).thenReturn(Optional.of(expected));
        // When
        Character actual = characterService.getCharacterById("1");
        // Then
        assertEquals(expected, actual);
        verify(mockRepo).findById("1");
    }

    @Test
    void saveCharacter() {
        // Given
        CharacterService characterService = new CharacterService(mockRepo, mockIdSercive);
        String id = "1";
        CharacterDto dto = CharacterDto.builder().name("Max").age(28).profession("Mage").build();
        Character expected = Character.builder().id(id).name("Max").age(28).profession("Mage").build();
        when(mockIdSercive.generateId()).thenReturn(id);
        when(mockRepo.save(expected)).thenReturn(expected);
        // When
        Character actual = characterService.saveCharacter(dto);
        // Then
        assertEquals(expected, actual);
        verify(mockRepo).save(expected);
        verify(mockIdSercive).generateId();
    }

    @Test
    void updateCharacter() {
        // Given
        CharacterService characterService = new CharacterService(mockRepo, mockIdSercive);
        String id = "1";
        CharacterDto dto = CharacterDto.builder().name("Max").age(28).profession("Mage").build();
        Character expected = Character.builder().id(id).name("Max").age(28).profession("Mage").build();
        when(mockRepo.findById("1")).thenReturn(Optional.of(expected));
        when(mockRepo.save(expected)).thenReturn(expected);
        // When
        Character actual = characterService.updateCharacter(id, dto);
        // Then
        assertEquals(expected, actual);
        verify(mockRepo).findById("1");
        verify(mockRepo).save(expected);
    }

    @Test
    void deleteCharacter() {
        // Given
        CharacterService characterService = new CharacterService(mockRepo, mockIdSercive);
        String id = "1";
        Character expected = Character.builder().id(id).name("Max").age(28).profession("Mage").build();
        when(mockRepo.findById(id)).thenReturn(Optional.of(expected));
        // When
        characterService.deleteCharacter(id);
        // Then
        verify(mockRepo).delete(expected);
    }

    @Test
    void getAverageAgeByProfession() {
        // Given
        CharacterService characterService = new CharacterService(mockRepo, mockIdSercive);
        Character c1 = Character.builder().id("1").name("Max").age(28).profession("Mage").build();
        Character c2 = Character.builder().id("2").name("Miriam").age(30).profession("Mage").build();
        List<Character> expected = new ArrayList<>(List.of(c1,c2));
        when(mockRepo.findCharactersByProfessionContainsIgnoreCase("Mage")).thenReturn(expected);
        // When
        ResponseEntity<Map<String, Double>> actual  = characterService.getAverageAgeByProfession("Mage");
        // Then
        assert actual.getBody() != null;
        assertEquals(29, actual.getBody().get("averageAge"));
        verify(mockRepo).findCharactersByProfessionContainsIgnoreCase("Mage");
    }
}