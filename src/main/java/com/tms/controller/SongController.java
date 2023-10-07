package com.tms.controller;

import com.tms.exception.NotFoundException;
import com.tms.domain.Song;
import com.tms.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/song", produces = MediaType.APPLICATION_JSON_VALUE)
public class SongController {
    private final SongService songService;
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
        if(songs.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(songs, HttpStatus.OK);
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
}

