package com.pst.controller;

import com.pst.model.Car;
import com.pst.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.util.List;

@RestController
@RequestMapping
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/cars/search")
    public ResponseEntity searchCars(@RequestParam(required = false) Double length,
                                @RequestParam(required = false) Double weight,
                                @RequestParam(required = false) Double velocity,
                                @RequestParam(required = false) String color) {
        List<Car> cars = carService.searchCars(length, weight, velocity, color);
        if (cars!=null & cars.size()>0) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } else {
            return new ResponseEntity("No cars available", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cars/export/xml")
    public ResponseEntity<String> exportCarsToXml(@RequestParam(required = false) Double length,
                                                  @RequestParam(required = false) Double weight,
                                                  @RequestParam(required = false) Double velocity,
                                                  @RequestParam(required = false) String color) throws JAXBException, JAXBException {
        List<Car> cars = carService.searchCars(length, weight, velocity, color);
        if(cars!=null & cars.size()>0){
            String xmlContent = carService.convertCarsToXml(cars);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cars.xml");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/xml");
            return new ResponseEntity<>(xmlContent, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No cars available", HttpStatus.NOT_FOUND);
        }
    }
}
