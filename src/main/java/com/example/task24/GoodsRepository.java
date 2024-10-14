package com.example.task24;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    @Query("SELECT p FROM Goods p WHERE CONCAT(p.goodName, '', p.contents, '', p.dateOfShipment, '', p.shipmentCity, '', p.dateOfArrival, '', p.arrivalCity) LIKE %?1%")
    List<Goods> searchGoods(String keyword);
}
