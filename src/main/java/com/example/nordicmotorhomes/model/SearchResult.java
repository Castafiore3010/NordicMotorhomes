package com.example.nordicmotorhomes.model;

import java.time.LocalDateTime;
/**
 * @author Marc,Emma,Samavia, Michael
 * @version 1.0
 */
public class SearchResult {
    private LocalDateTime start_datetime;
    private LocalDateTime end_datetime;
    private int capacity;

    public SearchResult() {}



    public SearchResult(LocalDateTime start_datetime, LocalDateTime end_datetime, int capacity) {
        this.start_datetime = start_datetime;
        this.end_datetime = end_datetime;
        this.capacity = capacity;
    }


    public LocalDateTime getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(LocalDateTime start_datetime) {
        this.start_datetime = start_datetime;
    }

    public LocalDateTime getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(LocalDateTime end_datetime) {
        this.end_datetime = end_datetime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
