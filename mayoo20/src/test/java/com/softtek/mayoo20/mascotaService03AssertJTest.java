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
import static org.assertj.core.api.Assertions.assertThat;

class MascotaService03AssertJTest {

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




        assertThat(registrada).isNotNull();
        assertThat(registrada.getNombre()).isEqualTo("Garfield");
        assertThat(registrada.getPropietario()).isNotNull();
        assertThat(registrada.getPropietario().getNombre()).isEqualTo("Dany");
        assertThat(registrada.getPropietario().getCiudad()).isEqualTo("Lima");
        assertThat(registrada.getPropietario().getTelefono()).isEqualTo("987654321");
        assertThat(registrada).isSameAs(mascota);


        assertThat(registrada.getId()).isPositive();
    }
}
