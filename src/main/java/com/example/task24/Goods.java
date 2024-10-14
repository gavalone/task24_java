package com.example.task24;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Entity
public class Goods {
    private Long ID;
    @Getter
    private String GoodName;
    @Getter
    private String Contents;
    @Getter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date DateOfShipment;
    @Getter
    private String ShipmentCity;
    @Getter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date DateOfArrival;
    @Getter
    private String ArrivalCity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getID() {
        return ID;
    }
}
