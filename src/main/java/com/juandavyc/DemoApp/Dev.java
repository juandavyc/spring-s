package com.juandavyc.DemoApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Dev {

    //@Autowired field injection
    //private final Laptop laptop; / no depends of laptop

    @Autowired
    @Qualifier("laptop")
    private Computer computer;

//    public Dev(Laptop laptop) {
//        this.laptop = laptop;
//    }

    public void build() {
        computer.compile();
        System.out.println("Working on Awesome project");
    }
}
