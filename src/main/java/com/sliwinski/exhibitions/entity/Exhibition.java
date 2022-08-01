package com.sliwinski.exhibitions.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="EXHIBITIONS")
public class Exhibition {
    @Id
    @GeneratedValue
    @Column(name = "EXHIBITION_ID", nullable = false)
    private int id;
    @Column(name = "THEME", nullable = false, unique = true)
    private String theme;
    @Column(name = "START_OF_OPERATION", nullable = false)
    private LocalDate startOfOperation;
    @Column(name = "END_OF_OPERATION", nullable = false)
    private LocalDate endOfOperation;
    @Column(name = "WORKING_START", nullable = false)
    private LocalTime workingStart;
    @Column(name = "WORKING_END", nullable = false)
    private LocalTime workingEnd;
    @Column(name = "TICKET_PRICE", nullable = false)
    private float ticketPrice;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "EXHIBITIONS_LOCATIONS", joinColumns = @JoinColumn(name = "EXHIBITION_ID"), inverseJoinColumns = @JoinColumn(name = "LOCATION_ID"))
    private List<Location> locations;
}