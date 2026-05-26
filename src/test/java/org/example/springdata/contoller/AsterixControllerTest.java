package org.example.springdata.contoller;

import org.example.springdata.model.Character;
import org.example.springdata.repository.CharacterRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AsterixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterRepo characterRepo;

    @Test
    void getAllCharacters() throws Exception {
        mockMvc.perform(get("/api/asterix/characters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[]"));
    }

    @Test
    void getCharacterById() throws Exception {
        Character character = Character.builder().id("1").name("Max").age(28).profession("Mage").build();
        characterRepo.save(character);
        mockMvc.perform(get("/api/asterix/character/" + character.id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                      {"id":"1","name":"Max","age":28,"profession":"Mage"}
                                                    """));
    }

    @Test
    void saveCharacter() throws Exception {
        mockMvc.perform(post("/api/asterix/character/new").contentType(MediaType.APPLICATION_JSON).content("""
                                                                                                                        {"name":"Max","age":28,"profession":"Mage"}
                                                                                                                        """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                      {"name":"Max","age":28,"profession":"Mage"}
                                                    """)).andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void updateCharacter() throws Exception {
        Character character = Character.builder().id("1").name("Max").age(28).profession("Mage").build();
        characterRepo.save(character);
        mockMvc.perform(put("/api/asterix/character/" + character.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {"name":"Maria","age":30,"profession":"Mage"}
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                      {"id": "1","name":"Maria","age":30,"profession":"Mage"}
                                                    """));
    }

    @Test
    void deleteCharacter() throws Exception {
        Character character = Character.builder().id("1").name("Max").age(28).profession("Mage").build();
        characterRepo.save(character);
        mockMvc.perform(delete("/api/asterix/character/" + character.id()))
                .andExpect(status().isOk());
    }

    @Test
    void getAverageAgeByProfession() throws Exception {
        Character c1 = Character.builder().id("1").name("Max").age(20).profession("Mage").build();
        Character c2 = Character.builder().id("2").name("Max").age(30).profession("Mage").build();
        characterRepo.save(c1);
        characterRepo.save(c2);
        mockMvc.perform(get("/api/asterix/character/average-age?profession=" + c1.profession()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageAge").value(25.0));
    }
}