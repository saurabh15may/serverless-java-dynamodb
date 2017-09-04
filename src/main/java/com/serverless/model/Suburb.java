package com.serverless.model;
import lombok.Data;

@Data
public class Suburb {
    private String name;
    private int postcode;

    public Suburb(){}
    
    public Suburb(String name,int postcode){
        this.name = name;
        this.postcode = postcode;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }
}
