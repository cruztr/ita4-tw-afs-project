package ita.tw.afs.spark.dto;

import java.util.List;

public class CustomError {
    private int code;
    private String message;
    public List<TypeValuePair> typeValuePairs;

    public CustomError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TypeValuePair> getTypeValuePairs() {
        return typeValuePairs;
    }

    public void setTypeValuePairs(List<TypeValuePair> typeValuePairs) {
        this.typeValuePairs = typeValuePairs;
    }
}
