package com.BandManagement.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BandGenreDto {

    private UUID band_id;
    private String bandName;
    private Long year;
    private String genreName;

}
