package com.sliwinski.exhibitions.entity;

import javax.persistence.*;

//@Table(name="EXHIBITIONS_HALLS")
public class ExhibitionsHalls {
//    @Column(name = "EXHIBITION_ID")
    private int exhibitionId;
//    @Column(name = "HALL_ID")
    private int hallId;

    public ExhibitionsHalls() {
    }

    public ExhibitionsHalls(int exhibitionId, int hallId) {
        this.exhibitionId = exhibitionId;
        this.hallId = hallId;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }
}
