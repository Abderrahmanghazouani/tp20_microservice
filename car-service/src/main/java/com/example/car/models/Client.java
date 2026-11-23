package com.example.car.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO (pas une entite) : represente le JSON renvoye par SERVICE-CLIENT.
// Les noms DOIVENT correspondre au JSON du service client : "nom" et "age" (Float).
// (l'enonce ecrit name/Integer -> le nom resterait toujours null)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private Long id;
    private String nom;
    private Float age;
}
