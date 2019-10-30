package ita.tw.afs.spark.Util;

import ita.tw.afs.spark.dto.GeneralResponse;
import ita.tw.afs.spark.dto.TypeValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SparkUtil {
    public SparkUtil() {
    }

    public static GeneralResponse setGeneralResponseOk(String msg, List<TypeValuePair> typeValuePairs){
        GeneralResponse createdResponse = new GeneralResponse(200, msg);
        if(Objects.nonNull(typeValuePairs) && !typeValuePairs.isEmpty()){
            List<TypeValuePair> createdTypeValuePair = new ArrayList<>(typeValuePairs);
            createdResponse.setTypeValuePairs(createdTypeValuePair);
        }
        return createdResponse;
    }

    public static GeneralResponse setGeneralResponseInternalServerError(String msg, List<TypeValuePair> typeValuePairs){
        GeneralResponse createdResponse = new GeneralResponse(500, msg);
        if(Objects.nonNull(typeValuePairs) && !typeValuePairs.isEmpty()){
            List<TypeValuePair> createdTypeValuePair = new ArrayList<>(typeValuePairs);
            createdResponse.setTypeValuePairs(createdTypeValuePair);
        }
        return createdResponse;
    }
}
