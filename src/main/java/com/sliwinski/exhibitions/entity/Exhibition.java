package com.sliwinski.exhibitions.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


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
    @JoinTable(name = "EXHIBITIONS_HALLS", joinColumns = @JoinColumn(name = "EXHIBITION_ID"), inverseJoinColumns = @JoinColumn(name = "HALL_ID"))
    private List<Hall> halls;

    public Exhibition() {
    }

    public Exhibition(int id, String theme, LocalDate startOfOperation, LocalDate endOfOperation, LocalTime workingStart, LocalTime workingEnd, float ticketPrice, List<Hall> halls) {
        this.id = id;
        this.theme = theme;
        this.startOfOperation = startOfOperation;
        this.endOfOperation = endOfOperation;
        this.workingStart = workingStart;
        this.workingEnd = workingEnd;
        this.ticketPrice = ticketPrice;
        this.halls = halls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public LocalDate getStartOfOperation() {
        return startOfOperation;
    }

    public void setStartOfOperation(LocalDate startOfOperation) {
        this.startOfOperation = startOfOperation;
    }

    public LocalDate getEndOfOperation() {
        return endOfOperation;
    }

    public void setEndOfOperation(LocalDate endOfOperation) {
        this.endOfOperation = endOfOperation;
    }

    public LocalTime getWorkingStart() {
        return workingStart;
    }

    public void setWorkingStart(LocalTime workingStart) {
        this.workingStart = workingStart;
    }

    public LocalTime getWorkingEnd() {
        return workingEnd;
    }

    public void setWorkingEnd(LocalTime workingEnd) {
        this.workingEnd = workingEnd;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }
}