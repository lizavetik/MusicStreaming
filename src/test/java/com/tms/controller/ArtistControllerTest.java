package com.tms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.domain.Artist;
import com.tms.domain.Song;
import com.tms.security.filter.JwtAuthenticationFilter;
import com.tms.service.ArtistService;
import com.tms.service.SongService;
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
public class ArtistControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ArtistService artistService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    static List<Artist> artists= new ArrayList<>();
    static Artist artist = new Artist();

    @BeforeAll
    public static void beforeAll() {
        artist.setId(5);
        artists.add(artist);
    }
    @Test
    public void getArtistsTest() throws Exception {
        Mockito.when(artistService.getArtists()).thenReturn(artists);
        mockMvc.perform(get("/artist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(5)));
    }

    @Test
    public void createArtistTest() throws Exception {
        SongService mockUS = Mockito.mock(SongService.class);
        Mockito.doNothing().when(mockUS).createSong(any());

        mockMvc.perform(post("/artist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artist)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteArtistTest() throws Exception {
        ArtistService mockUS = Mockito.mock(ArtistService.class);
        Mockito.doNothing().when(mockUS).deleteArtistById(anyInt());
        mockMvc.perform(delete("/artist/10")).andExpect(status().isNoContent());
    }
}
