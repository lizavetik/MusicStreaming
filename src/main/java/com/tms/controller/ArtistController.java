package com.tms.controller;

import com.tms.ErrorResponse;
import com.tms.exception.NotAuthorizedException;
import com.tms.exception.NotFoundException;
import com.tms.domain.Artist;
import com.tms.service.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/artist", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtist(@PathVariable Integer id) {
    Artist artist = artistService.getArtist(id).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> artists = artistService.getArtists();
        if(artists.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(artists, HttpStatus.OK);
        }
    }
    @PostMapping
    public ResponseEntity<HttpStatus> createArtist(@RequestBody Artist artist) {
        artistService.createArtist(artist);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(@RequestBody Integer id) {
        artistService.deleteArtistById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler (NotAuthorizedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
