package com.example.client.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marque cette classe comme une entite JPA
@Data // getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementation
    private Long id;
    private String nom;  // Nom du client
    private Float age;   // Age du client
}
