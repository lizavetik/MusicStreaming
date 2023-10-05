package com.tms.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "artist")
public class Artist {
    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName ="artist_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "mySeqGen")
    private Integer id;
    @Column(name = "artist_name")
    private String artistName;
    @Column(name = "country")
    private String country;
    @Column(name = "style")
    private String style;
}
