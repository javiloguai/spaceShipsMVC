package com.w2m.spaceShips.restapi.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

/**
 * @author javiloguai
 */
@Entity
@Table(name = "SPACE_SHIP")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceShipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "MEDIA_SHOW")
    private String mediaShow;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "spaceShipId")
    private List<SpaceShipEquipmentEntity> equipment;

}
