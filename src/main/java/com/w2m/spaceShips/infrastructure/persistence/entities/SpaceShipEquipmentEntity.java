package com.w2m.spaceShips.infrastructure.persistence.entities;

import com.w2m.spaceShips.domain.enums.Equipment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author javiloguai
 */
@Entity
@Table(name = "SPACE_SHIP_EQUIPMENT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceShipEquipmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "ID")
    private long id;

    @Column(name = "SPACE_SHIP_ID", nullable = false)
    private Long spaceShipId;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHIP_EQUIPMENT", nullable = false)
    private Equipment shipEquipment;

}
