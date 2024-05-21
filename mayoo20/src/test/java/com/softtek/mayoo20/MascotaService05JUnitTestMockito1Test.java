package com.softtek.mayoo20;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.softtek.mayoo20.modelo.Mascota;
import com.softtek.mayoo20.modelo.Propietario;
import com.softtek.mayoo20.repository.MascotaRepository;
import com.softtek.mayoo20.service.ExternalService;
import com.softtek.mayoo20.service.MascotaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MascotaService05JUnitTestMockito1Test {
    MascotaService mascotaService;

    @Test
    @DisplayName("Registrar mascota correctamente")
    void testRegistrarMascotaCorrectamente() {


        MascotaRepository mascotaRepository = mock(MascotaRepository.class);
        ExternalService externalService = mock(ExternalService.class);


        mascotaService = new MascotaService(mascotaRepository, externalService);

        Propietario propietario = new Propietario();
        propietario.setNombre("Dany");
        propietario.setCiudad("Lima");
        propietario.setTelefono("9876543214");

        Mascota mascota = new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);
        when(mascotaRepository.findById(any(Integer.class))).thenReturn(Optional.of(mascota));
        when(mascotaRepository.guardar(any(Mascota.class))).thenReturn(mascota);

        Mascota registrada = mascotaService.registrarMascota(mascota);


        assertNotNull(registrada, "La mascota registrada no debería ser null.");


        assertSame(mascota, registrada, "La mascota registrada debería ser la misma que la mascota original.");
    }
}
