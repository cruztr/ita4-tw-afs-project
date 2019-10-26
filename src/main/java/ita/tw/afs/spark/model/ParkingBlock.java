package ita.tw.afs.spark.model;

import javax.persistence.*;

@Entity
public class ParkingBlock {

    public static final String AVAILABLE = "AVAILABLE";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer position;
    private String status;
    private Long parkingLotId;

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public ParkingBlock() {
    }

    public ParkingBlock(Long id, Integer position, ParkingLot parkingLot) {
        this.id = id;
        this.position = position;
        this.status = AVAILABLE;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
