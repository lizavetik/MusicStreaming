package com.tms.repository;

import com.tms.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM user_info WHERE songName = :sn")
    Optional<Song> findSongByName(String sn);

}
