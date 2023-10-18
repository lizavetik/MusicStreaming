package com.tms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity(name = "artist")
@EqualsAndHashCode
@ToString
public class Artist {
    @Id
    @SequenceGenerator(name = "artistSeqGen", sequenceName = "artist_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "artistSeqGen")
    private Integer id;
    @Column(name = "artist_name")
    private String artistName;
    @Column(name = "country")
    private String country;
    @Column(name = "style")
    private String style;
}
