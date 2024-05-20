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

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MascotaService02HamcrestTest {

    private MascotaService mascotaService;

    @Test
    @DisplayName("Registrar mascota correctamente")
    void testRegistrarMascotaCorrectamente() {
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotaService mascotaService = new MascotaService(mascotaRepository, externalService);

        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        Mascota registrada = mascotaService.registrarMascota(mascota);

        assertThat(registrada, is(notNullValue()));
        assertThat(registrada.getNombre(), is("Garfield"));
        assertThat(registrada.getPropietario(), is(notNullValue()));
        assertThat(registrada.getPropietario().getNombre(), is("Dany"));
        assertThat(registrada.getPropietario().getCiudad(), is("Lima"));
        assertThat(registrada.getPropietario().getTelefono(), is("987654321"));
        assertThat(registrada, is(sameInstance(mascota)));
        assertThat(registrada.getId(), is(greaterThan(0)));
    }

    @Test
    @DisplayName("El nombre de la mascota no puede estar vacío")
    void testNombreMascotaNoPuedeEstarVacio() {
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotaService mascotaService = new MascotaService(mascotaRepository, externalService);

        Mascota mascota = new Mascota();
        mascota.setNombre(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            mascotaService.registrarMascota(mascota);
        });
        assertThat(exception.getMessage(), is("El nombre de la mascota no puede estar vacío."));
    }

    @Test
    @DisplayName("El nombre de la mascota no puede estar vacío")
    void testNombreMascotaNoPuedeEstarEmpty() {
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotaService mascotaService = new MascotaService(mascotaRepository, externalService);

        Mascota mascota = new Mascota();
        mascota.setNombre("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            mascotaService.registrarMascota(mascota);
        });
        assertThat(exception.getMessage(), is("El nombre de la mascota no puede estar vacío."));
    }

    @Test
    @DisplayName("La mascota debe tener un propietario")
    void testMascotaDebeTenerPropietario() {
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotaService mascotaService = new MascotaService(mascotaRepository, externalService);

        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            mascotaService.registrarMascota(mascota);
        });
        assertThat(exception.getMessage(), is("La mascota debe tener un propietario."));
    }

    @Test
    @DisplayName("El propietario debe tener un teléfono registrado")
    void testPropietarioDebeTenerTelefono() {
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotaService mascotaService = new MascotaService(mascotaRepository, externalService);

        Propietario propietario = new Propietario("Dany", "Lima", null);
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            mascotaService.registrarMascota(mascota);
        });
        assertThat(exception.getMessage(), is("El propietario debe tener un teléfono registrado."));
    }

    @Test
    @DisplayName("El propietario debe tener un teléfono registrado")
    void testPropietarioDebeTenerTelefonoNoVacio() {
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotaService mascotaService = new MascotaService(mascotaRepository, externalService);

        Propietario propietario = new Propietario("Dany", "Lima", "");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            mascotaService.registrarMascota(mascota);
        });
        assertThat(exception.getMessage(), is("El propietario debe tener un teléfono registrado."));
    }

    @Test
    @DisplayName("La mascota debe tener todas las vacunas al día")
    void testMascotaDebeTenerVacunasAlDia() {
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        externalService = new ExternalService() {
            @Override
            public boolean validarVacunas(Mascota mascota) {
                return false;
            }

            @Override
            public boolean verificarRegistroMunicipal(Mascota mascota) {
                return true;
            }
        };
        MascotaService mascotaService = new MascotaService(mascotaRepository, externalService);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            mascotaService.registrarMascota(mascota);
        });
        assertThat(exception.getMessage(), is("La mascota no tiene todas las vacunas al día."));
    }

    @Test
    @DisplayName("La mascota debe estar registrada en el municipio")
    void testMascotaDebeEstarRegistradaEnMunicipio() {
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        externalService = new ExternalService() {
            @Override
            public boolean validarVacunas(Mascota mascota) {
                return true;
            }

            @Override
            public boolean verificarRegistroMunicipal(Mascota mascota) {
                return false;
            }
        };
        MascotaService mascotaService = new MascotaService(mascotaRepository, externalService);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            mascotaService.registrarMascota(mascota);
        });
        assertThat(exception.getMessage(), is("La mascota no está registrada en el municipio."));
    }

    @Test
    @DisplayName("La mascota no debe estar ya registrada")
    void testMascotaNoDebeEstarRegistrada() {
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        Propietario propietario = new Propietario("Dany", "Lima", "987654321");
        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        mascotaRepository = new MascotaRepositoryImpl() {
            @Override
            public Optional<Mascota> findById(Integer id) {
                return Optional.of(mascota);
            }
        };
        MascotaService mascotaService = new MascotaService(mascotaRepository, externalService);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            mascotaService.registrarMascota(mascota);
        });
        assertThat(exception.getMessage(), is("Esta mascota ya está registrada."));
    }

    @Test
    @DisplayName("Buscar mascota por ID existente")
    void testBuscarMascotaPorIdExistente() {
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotaService mascotaService = new MascotaService(mascotaRepository, externalService);

        Mascota mascota = new Mascota();
        mascota.setId(1);
        mascotaRepository.guardar(mascota);

        Optional<Mascota> resultado = mascotaService.buscarMascotaPorId(1);

        assertThat(resultado.isPresent(), is(true));
        assertThat(resultado.get().getId(), is(1));
    }

    @Test
    @DisplayName("Eliminar mascota por ID existente")
    void testEliminarMascotaPorIdExistente() {
        MascotaRepository mascotaRepository = new MascotaRepositoryImpl();
        ExternalService externalService = new ExternalServiceImpl();
        MascotaService mascotaService = new MascotaService(mascotaRepository, externalService);

        Mascota mascota = new Mascota();
        mascota.setId(1);
        mascotaRepository.guardar(mascota);

        mascotaService.eliminarMascotaPorId(1);

        assertThat(mascotaRepository.findById(1), is(Optional.empty()));
    }
}




