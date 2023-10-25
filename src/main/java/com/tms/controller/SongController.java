package com.tms.controller;

import com.tms.ErrorResponse;
import com.tms.exception.NotAuthorizedException;
import com.tms.exception.NotFoundException;
import com.tms.domain.Song;
import com.tms.service.SongService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(value = "/song", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Bearer Authentication")
public class SongController {
    private final SongService songService;
    private final Path ROOT_FILE_PATH = Paths.get("data");

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSong(@PathVariable Integer id) {
        Song song = songService.getSong(id).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping("/song_name/{songName}")
    public ResponseEntity<Song> getSongByName(@PathVariable String songName) {
        Song song = songService.findSongByName(songName).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.getSongs();
        if (songs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateSong(@RequestBody Song song, @RequestParam("file") MultipartFile file) {
        songService.updateSong(song);
        try {
            Files.copy(file.getInputStream(), this.ROOT_FILE_PATH.resolve(file.getOriginalFilename()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addSong(@RequestBody Song song) {
        songService.createSong(song);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(@RequestBody Integer id) {
        songService.deleteSongById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(NotAuthorizedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
