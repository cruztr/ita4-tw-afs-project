package ita.tw.afs.spark.dto;

public class OrdersResponse {
    private Long orderNumber;
    private Long key;
    private String plateNumber;
    private String parkingLotName;
    private Long parkingLotId;
    private Integer parkingBlockPosition;
    private String price;
    private String timeIn;

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Integer getParkingBlockPosition() {
        return parkingBlockPosition;
    }

    public void setParkingBlockPosition(Integer parkingBlockPosition) {
        this.parkingBlockPosition = parkingBlockPosition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }
}
