package com.softtek.mayoo20;

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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestMascotaService05JUnitTestMockito1Test {
    @InjectMocks
    MascotaService mascotaService;

    @Mock
    MascotaRepository mascotaRepository;
    @Mock
    ExternalService externalService;

    @Test
    @DisplayName("Registrar mascota correctamente")
    void  testRegitrarMascotaCorrectamente(){
        Propietario propietario =  new Propietario("Dany", "Lima", "987654321");
        Mascota mascota =  new Mascota();
        mascota.setNombre("Garfield");
        mascota.setPropietario(propietario);

        when(externalService.validarVacunas(any(Mascota.class))).thenReturn(true);
        when(externalService.verificarRegistroMunicipal(any(Mascota.class))).thenReturn(true);
        when(mascotaRepository.findById(any())).thenReturn(Optional.empty());
        when(mascotaRepository.guardar(any(Mascota.class))).thenReturn(mascota);
        Mascota registrada = mascotaService.registrarMascota(mascota);


        assertNotNull(registrada, "La mascota registrada no debería ser null.");


        assertSame(mascota, registrada, "La mascota registrada debería ser la misma que la ingresada.");


    }

}
