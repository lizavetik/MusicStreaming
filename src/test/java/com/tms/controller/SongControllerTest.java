package com.tms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.domain.Song;
import com.tms.domain.UserInfo;
import com.tms.security.filter.JwtAuthenticationFilter;
import com.tms.service.SongService;
import com.tms.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SongControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    SongService songService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    static List<Song> songs= new ArrayList<>();
    static Song song = new Song();

    @BeforeAll
    public static void beforeAll() {
        song.setId(5);
        songs.add(song);
    }
    @Test
    public void getSongsTest() throws Exception {
        Mockito.when(songService.getSongs()).thenReturn(songs);
        mockMvc.perform(get("/song"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(5)));
    }

    @Test
    public void createSongTest() throws Exception {
        SongService mockUS = Mockito.mock(SongService.class);
        Mockito.doNothing().when(mockUS).createSong(any());

        mockMvc.perform(post("/song")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(song)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteSongTest() throws Exception {
        SongService mockUS = Mockito.mock(SongService.class);
        Mockito.doNothing().when(mockUS).deleteSongById(anyInt());

        mockMvc.perform(delete("/song/10")).andExpect(status().isNoContent());
    }
}
