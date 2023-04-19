package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {
    @Autowired
    private FilmController filmController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @BeforeEach
//    public void initialization() {
//        filmController = new FilmController(new FilmService());
//    }

    @Test
    public void contextLoads() {
        assertThat(filmController).isNotNull();
    }

    @Test
    public void shouldAddFilm() throws Exception {
        Film film = Film.builder()
                .id(1)
                .name("Film")
                .description("Description")
                .releaseDate(LocalDate.of(2020, 1, 1))
                .duration(50)
                .build();

        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotAddFilm() throws Exception {
        Film film = Film.builder()
                .id(1)
                .name("")
                .description("Description")
                .releaseDate(LocalDate.of(2020, 1, 1))
                .duration(50)
                .build();

        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

        film = Film.builder()
                .id(1)
                .name("Name")
                .description("11111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111")
                .releaseDate(LocalDate.of(2020, 1, 1))
                .duration(50)
                .build();

        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

        film = Film.builder()
                .id(1)
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.of(1700, 1, 1))
                .duration(50)
                .build();

        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

        film = Film.builder()
                .id(1)
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.of(1700, 1, 1))
                .duration(-50)
                .build();

        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
}