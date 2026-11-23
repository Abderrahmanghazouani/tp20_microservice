package com.example.car.services;

import com.example.car.entities.Car;
import com.example.car.models.CarResponse;
import com.example.car.models.Client;
import com.example.car.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final RestTemplate restTemplate;
    // http://localhost:8888/SERVICE-CLIENT/api/client/ (via la Gateway) - configurable dans application.yml
    private final String clientServiceUrl;

    public CarService(CarRepository carRepository, RestTemplate restTemplate,
                      @Value("${client-service.url}") String clientServiceUrl) {
        this.carRepository = carRepository;
        this.restTemplate = restTemplate;
        this.clientServiceUrl = clientServiceUrl;
    }

    public List<CarResponse> findAll() {
        return carRepository.findAll().stream().map(this::mapToCarResponse).toList();
    }

    public CarResponse findById(Long id) throws Exception {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new Exception("Voiture non trouvée avec l'ID: " + id));
        return mapToCarResponse(car);
    }

    // Car (base locale) + Client (autre microservice, appel HTTP) -> CarResponse
    CarResponse mapToCarResponse(Car car) {
        Client client = null;
        try {
            client = restTemplate.getForObject(clientServiceUrl + car.getClient_id(), Client.class);
        } catch (Exception e) {
            // Service client indisponible : on renvoie la voiture avec client = null
            System.err.println("Erreur lors de la récupération du client: " + e.getMessage());
        }
        return CarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .matricule(car.getMatricule())
                .client(client)
                .build();
    }
}
