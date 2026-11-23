package com.example.car.services;

import com.example.car.entities.Car;
import com.example.car.models.CarResponse;
import com.example.car.models.Client;
import com.example.car.repositories.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {
    static final String URL = "http://localhost:8888/SERVICE-CLIENT/api/client/";
    CarRepository repo = mock(CarRepository.class);
    RestTemplate rest = mock(RestTemplate.class);
    CarService service = new CarService(repo, rest, URL);

    @Test
    void voitureEnrichieAvecLeClient() throws Exception {
        when(repo.findById(1L)).thenReturn(Optional.of(new Car(1L, "Toyota", "Yaris", "A-12345", 1L)));
        when(rest.getForObject(URL + 1L, Client.class)).thenReturn(new Client(1L, "Salma", 22f));

        CarResponse r = service.findById(1L);

        assertEquals("Toyota", r.getBrand());
        assertEquals("Salma", r.getClient().getNom());
        verify(rest).getForObject(URL + "1", Client.class);   // appel via la Gateway
    }

    @Test
    void clientIndisponibleDonneClientNull() {
        when(repo.findAll()).thenReturn(List.of(new Car(2L, "Dacia", "Logan", "C-24680", 2L)));
        when(rest.getForObject(anyString(), eq(Client.class))).thenThrow(new ResourceAccessException("Connection refused"));

        List<CarResponse> all = service.findAll();

        assertEquals(1, all.size());
        assertNull(all.get(0).getClient());   // pas d'erreur 500 : resilience minimale
    }

    @Test
    void voitureInexistante() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.findById(99L));
    }
}
