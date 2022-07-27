package com.sliwinski.exhibitions.entity;

import javax.persistence.*;

@Entity
@Table(name="HALLS")
public class Hall {
    @Id
    @GeneratedValue
    @Column(name = "HALL_ID", nullable = false)
    private int id;
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    public Hall() {
    }

    public Hall(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
