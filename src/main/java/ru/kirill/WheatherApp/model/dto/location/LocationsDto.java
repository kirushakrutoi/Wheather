package ru.kirill.WheatherApp.model.dto.location;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationsDto {

    private String name;

    @JsonSetter("main")
    private MainInfoDto mainInfo;

    private List<WeatherDto> weather;

    private  WindDto wind;

    private CoordDto coord;

}
