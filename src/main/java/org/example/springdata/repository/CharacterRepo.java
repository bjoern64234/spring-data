package org.example.springdata.repository;

import org.example.springdata.model.Character;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepo extends MongoRepository<Character, String> {
    List<Character> findCharacterByName(String name);
}
