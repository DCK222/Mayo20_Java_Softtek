package com.softtek.mayoo20.repository;

import com.softtek.mayoo20.modelo.Mascota;

import java.util.Optional;

public interface MascotaRepository {
    Mascota guardar(Mascota mascota);
    Optional<Mascota> findById(Integer id);
    void deleteById(Integer id);
}
