package com.tms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.FetchType;

import lombok.Data;

import java.util.Collection;

@Data
@Entity(name = "songs")
public class Song {
    @Id
    @SequenceGenerator(name = "songSeqGen", sequenceName = "songs_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "songSeqGen")
    private Integer id;
    @Column(name = "song_name")
    private String songName;

    @Column(name = "album")
    private String album;

    @Column(name = "year_of_release")
    private Integer yearOfRelease;

    @ManyToMany(mappedBy = "songs", fetch = FetchType.EAGER)
    private Collection<UserInfo> users;
}