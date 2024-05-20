package com.softtek.mayoo20;

import com.softtek.mayoo20.modelo.Mascota;
import com.softtek.mayoo20.modelo.Propietario;
import com.softtek.mayoo20.repository.MascotaRepository;
import com.softtek.mayoo20.repository.MascotaRepositoryImpl;
import com.softtek.mayoo20.service.ExternalService;
import com.softtek.mayoo20.service.ExternalServiceImpl;
import com.softtek.mayoo20.service.MascotaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MascotaService01JUnitTest {

    private MascotaService mascotaService;

    @Test
    @DisplayName("Registrar mascota correctamente")
    void testRegistrarMascotaCorrectamente() {

        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        mascotaService = new MascotaService(mascotaRepository, externalService);

        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);


        Mascota registrada = mascotaService.registrarMascota(mascota);




        assertNotNull(registrada, "La mascota registrada no debería ser null.");


        assertSame(mascota, registrada, "La mascota registrada debería ser la misma que la ingresada.");

        assertEquals("Garfield", registrada.getNombre(), "El nombre de la mascota registrada debería ser 'Garfield'.");

        assertSame(propietario, registrada.getPropietario(), "El propietario de la mascota registrada debería ser el mismo que se proporcionó.");

        assertEquals("Dany", registrada.getPropietario().getNombre(), "El nombre del propietario debería ser 'Dany'.");
        assertEquals("Lima", registrada.getPropietario().getCiudad(), "La ciudad del propietario debería ser 'Lima'.");
        assertEquals("987654321", registrada.getPropietario().getTelefono(), "El teléfono del propietario debería ser '987654321'.");

        assertTrue(registrada.getId() > 0, "ID debe ser positivo");

        assertAll("Propiedades de la mascota",
                () -> assertEquals("Garfield", registrada.getNombre(), "El nombre debería coincidir."),
                () -> assertNotNull(registrada.getPropietario(), "El propietario no debe ser null."),
                () -> assertEquals("Dany", registrada.getPropietario().getNombre(), "El nombre del propietario debe ser Dany"),
                () -> assertEquals("Lima", registrada.getPropietario().getCiudad(), "La ciudad del propietario debe ser Lima"),
                () -> assertEquals("987654321", registrada.getPropietario().getTelefono(), "El teléfono del propietario debe ser 987654321")
        );

        assertDoesNotThrow(() -> registrada.getNombre(), "Obtener el nombre de la mascota no debería lanzar una excepción.");
        assertDoesNotThrow(() -> registrada.getPropietario().getCiudad(), "Obtener la ciudad del propietario no debería lanzar una excepción.");
    }
}
