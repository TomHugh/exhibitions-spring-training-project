package com.sliwinski.exhibitions.service.utility;

import java.time.LocalDate;

public class Search {
    private LocalDate from = null;
    private LocalDate to = null;
    private SortType sort = SortType.DATE_ASC;

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public SortType getSort() {
        return sort;
    }

    public void setSort(SortType sort) {
        this.sort = sort;
    }
}
