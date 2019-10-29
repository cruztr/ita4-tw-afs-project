package ita.tw.afs.spark.mapper;

import ita.tw.afs.spark.dto.ParkingLotResponse;
import ita.tw.afs.spark.model.ParkingLot;

public class ParkingLotMapper {
    ParkingLot parkingLot;
    public ParkingLotMapper(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingLotResponse mappedResponse(){

        ParkingLotResponse parkingLotResponse = new ParkingLotResponse();
        parkingLotResponse.setId(parkingLot.getId());
        parkingLotResponse.setName(parkingLot.getName());
        parkingLotResponse.setLocation(parkingLot.getLocation());
        parkingLotResponse.setCapacity(parkingLot.getCapacity());
        parkingLotResponse.setParkingBlocks(parkingLot.getParkingBlocks());
        parkingLotResponse.setRate(parkingLot.getRate());
        parkingLotResponse.setKey(parkingLot.getId());
        return parkingLotResponse;
    }


}
