package com.tms.service;

import com.tms.domain.Song;
import com.tms.repository.SongRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {
    private final Integer ID_VALUE = 5;

    @InjectMocks
    private SongService songService;

    @Mock
    private SongRepository songRepository;

    @BeforeAll
    public static void beforeAll() {
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
    }

    @Test
    public void getUserTest() {
        songService.getSong(ID_VALUE);
        Mockito.verify(songRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void createUser() {
        songService.createSong(new Song());
        Mockito.verify(songRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void deleteUser() {
        songService.deleteSongById(ID_VALUE);
        Mockito.verify(songRepository, Mockito.times(1)).deleteById(anyInt());
    }
}
