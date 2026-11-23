package com.example.car;

import com.example.car.entities.Car;
import com.example.car.repositories.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient // Active l'enregistrement aupres d'Eureka
public class CarApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarApplication.class, args);
    }

    // RestTemplate : client HTTP SYNCHRONE pour la communication inter-services
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000); // 5 s pour la connexion
        requestFactory.setReadTimeout(5000);    // 5 s pour la lecture
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    @Bean
    CommandLineRunner initCars(CarRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Car(null, "Toyota", "Yaris", "A-12345", 1L));
                repo.save(new Car(null, "Renault", "Clio", "B-67890", 1L));
                repo.save(new Car(null, "Dacia", "Logan", "C-24680", 2L));
            }
        };
    }
}
