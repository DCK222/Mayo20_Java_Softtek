package com.softtek.mayoo20.service;

import com.softtek.mayoo20.modelo.Mascota;

public interface ExternalService {
    boolean validarVacunas(Mascota mascota);
    boolean verificarRegistroMunicipal(Mascota mascota);
}
