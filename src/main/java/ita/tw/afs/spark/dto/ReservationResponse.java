package ita.tw.afs.spark.dto;

public class ReservationResponse {
    private Long reservationNumber;
    private Long key;
    private String status;
    private String applicationTime;
    private String reservedTime;
    private String fullName;
    private String plateNumber;
    private Long parkingLotId;
    private Long carOwnerId;
    private Integer position;
    private String parkingLotName;
    private String parkingLotLocation;
    private Double rate;

    public Long getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(Long reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
