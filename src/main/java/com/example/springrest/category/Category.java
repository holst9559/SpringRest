package com.example.springrest.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import com.example.springrest.place.Place;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String name;
    @NotBlank
    private String symbol;
    private String description;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "category",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )

    @JsonIgnore
    private List<Place> places = new ArrayList<>();

    public Category(){};

    public Category(String name, String symbol, String description) {
        this.name = name;
        this.symbol = symbol;
        this.description = description;
    }

    public void addPlace(Place place){
        places.add(place);
        place.setCategory(this);
    };

    public void removePlace(Place place){
        places.remove(place);
        place.setCategory(null);
    };
}