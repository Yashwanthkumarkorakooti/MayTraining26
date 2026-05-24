package com.main.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Insurance {
//    @Autowired  --> problem when we do testing
    private CarInsurance carInsurance;
//    @Autowired
    private BikeInsurance bikeInsurance;

//    @Autowired  -> Prefer Constructor method
    public Insurance(CarInsurance carInsurance, BikeInsurance bikeInsurance) {
            this.carInsurance = carInsurance;
            this.bikeInsurance = bikeInsurance;
    }

//    @Autowired
//    public void setCarInsurance(CarInsurance carInsurance) {
//        this.carInsurance = carInsurance;
//    }
//    @Autowired
//    public void setBikeInsurance(BikeInsurance bikeInsurance) {
//        this.bikeInsurance = bikeInsurance;
//    }

    public  void details(){
        System.out.println("Insurance details");
        carInsurance.details();
        bikeInsurance.details();
    }
}
