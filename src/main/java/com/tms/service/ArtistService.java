package com.tms.service;

import com.tms.domain.Artist;
import com.tms.repository.ArtistRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getArtists() {
        return artistRepository.findAll(Sort.by("id"));
    }

    public void updateArtist(Artist artist) {
        artistRepository.saveAndFlush(artist);
    }

    public Optional<Artist> getArtist(Integer id) {
        return artistRepository.findById(id);
    }

    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public void deleteArtistById(Integer id) {
        artistRepository.deleteById(id);
    }
}

