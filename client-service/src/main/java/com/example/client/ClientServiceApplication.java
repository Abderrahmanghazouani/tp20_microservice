package com.example.client;

import com.example.client.entities.Client;
import com.example.client.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient // Active l'enregistrement aupres d'Eureka (@EnableEurekaClient n'existe plus)
public class ClientServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    // Jeu de donnees de depart (seulement si la table est vide)
    @Bean
    CommandLineRunner initClients(ClientRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Client(null, "Salma", 22f));
                repo.save(new Client(null, "Ahmed", 30f));
                repo.save(new Client(null, "Yassine", 27f));
            }
        };
    }
}
