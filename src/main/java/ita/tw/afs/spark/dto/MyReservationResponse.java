package ita.tw.afs.spark.dto;

public class MyReservationResponse {
    private Long reservationNumber;
    private Long parkingLotId;
    private Integer capacity;
    private Integer parkingBlockPosition;
    private String parkingLotName;
    private String parkingLotLocation;
    private String reservedTime;
    private String reservationStatus;

    public Integer getParkingBlockPosition() {
        return parkingBlockPosition;
    }

    public void setParkingBlockPosition(Integer parkingBlockPosition) {
        this.parkingBlockPosition = parkingBlockPosition;
    }

    public Long getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(Long reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public String getParkingLotLocation() {
        return parkingLotLocation;
    }

    public void setParkingLotLocation(String parkingLotLocation) {
        this.parkingLotLocation = parkingLotLocation;
    }

    public String getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(String reservedTime) {
        this.reservedTime = reservedTime;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
