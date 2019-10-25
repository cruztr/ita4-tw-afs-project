package ita.tw.afs.spark.service;

import ita.tw.afs.spark.exception.InvalidCredentialsException;
import ita.tw.afs.spark.model.ParkingBoy;
import ita.tw.afs.spark.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingBoyService {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    public ParkingBoy save(ParkingBoy parkingBoy) {
        return parkingBoyRepository.save(parkingBoy);
    }

    public ParkingBoy login(String username, String password) throws InvalidCredentialsException {
        ParkingBoy parkingBoyAccount = parkingBoyRepository.findByUsernameAndPassword(username, password);
        if(parkingBoyAccount != null){
            return parkingBoyAccount;
        }
        throw new InvalidCredentialsException();
    }
}