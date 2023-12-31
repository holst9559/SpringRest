package com.example.springrest.place;

import com.example.springrest.category.Category;
import com.example.springrest.utility.Point2DSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.geolatte.geom.Point;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.geolatte.geom.G2D;
import org.hibernate.proxy.HibernateProxy;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@Entity
@Table
@Getter
@Setter
public class Place implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column
    private String name;

    @Column(nullable = false)
    private String userId;
    private Boolean visible = false;

    @JsonSerialize(using = Point2DSerializer.class)
    private Point<G2D> coordinate;

    @Size(max = 255)
    private String description;

    @ManyToOne
    private Category category;

    @CreationTimestamp
    @Column
    private LocalDateTime createdDateTime;
    @UpdateTimestamp
    @Column
    private LocalDateTime updatedDateTime;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Place place = (Place) o;
        return getId() != null && Objects.equals(getId(), place.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public static Place of(PlaceDto placeDto) {
        Place place = new Place();

        Point<G2D> coordinate = new Point<>(new G2D(placeDto.lon(), placeDto.lat()), WGS84);
        place.setCoordinate(coordinate);

        place.setName(placeDto.name());
        place.setDescription(placeDto.description());
        place.setVisible(placeDto.visible());
        Category category = new Category();
        category.setName(placeDto.category());
        place.setCategory(category);

        return place;
    }
}
