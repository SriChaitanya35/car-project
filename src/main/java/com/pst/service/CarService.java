package com.pst.service;

import com.pst.helper.CarList;
import com.pst.model.Car;
import com.pst.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> searchCars(Double length, Double weight, Double velocity, String color) {
        return carRepository.findAll()
                .stream()
                .filter(car -> length == null || car.getLength() == length)
                .filter(car -> weight == null || car.getWeight() == weight)
                .filter(car -> velocity == null || car.getVelocity() == velocity)
                .filter(car -> color == null || car.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());

    }

    public String convertCarsToXml(List<Car> cars) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CarList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        CarList carList = new CarList();
        carList.setCars(cars);

        StringWriter writer = new StringWriter();
        marshaller.marshal(carList, writer);

        return writer.toString();
    }

}
