package ita.tw.afs.spark.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationNumber;
    private String status;
    private String applicationTime;
    private String reservedTime;
    private Long parkingLotId;
    private Long carOwnerId;
    private Integer position;

    public Long getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(Long reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(String reservedTime) {
        this.reservedTime = reservedTime;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Long getCarOwnerId() {
        return carOwnerId;
    }

    public void setCarOwnerId(Long carOwnerId) {
        this.carOwnerId = carOwnerId;
    }
}
