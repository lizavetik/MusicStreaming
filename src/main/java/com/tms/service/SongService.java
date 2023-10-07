package com.tms.service;

import com.tms.domain.Song;
import com.tms.repository.SongRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }


    public List<Song> getSongs() {
        return songRepository.findAll(Sort.by("id"));
    }
    public Optional<Song> findSongByName(String songName) {
        return songRepository.findSongByName(songName);
    }

    public Optional<Song> getSong(Integer id) {
        return songRepository.findById(id);
    }

    public Song createSong(Song song) {
        return (Song) songRepository.save(song);
    }

    public void deleteSongById(Integer id) {
        songRepository.deleteById(id);
    }
}