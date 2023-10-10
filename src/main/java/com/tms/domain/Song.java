package com.tms.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity(name = "songs")
public class Song {
    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName = "songs_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "mySeqGen")
    private Integer id;
    @Column(name = "song_name")
    private String songName;

    @Column(name = "album")
    private String album;

    @Column(name = "year_of_release")
    private Integer yearOfRelease;

    @ManyToMany(mappedBy = "songs",fetch = FetchType.EAGER)
    private Collection<UserInfo> users;
}