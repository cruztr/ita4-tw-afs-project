package ita.tw.afs.spark.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String location;
    private Integer capacity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parkingLot")
    private List<ParkingBlock> parkingBlocks;

    public List<ParkingBlock> getParkingBlocks() {
        return parkingBlocks;
    }

    public void setParkingBlocks(List<ParkingBlock> parkingBlocks) {
        this.parkingBlocks = parkingBlocks;
    }

    public ParkingLot() {
    }

    public ParkingLot(Long id, String name, Integer capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

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
}