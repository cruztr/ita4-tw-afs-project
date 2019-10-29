package ita.tw.afs.spark.dto;

import ita.tw.afs.spark.model.ParkingBlock;

import java.util.List;

public class ParkingLotResponse {
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private List<ParkingBlock> parkingBlocks;
    private Double rate;
    private Long key;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }
    public List<ParkingBlock> getParkingBlocks() { return parkingBlocks; }
    public void setParkingBlocks(List<ParkingBlock> parkingBlocks) {
        this.parkingBlocks = parkingBlocks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
