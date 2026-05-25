package com.mycompany.proyecto_peluqueria_canina.persistencia;

import com.mycompany.proyecto_peluqueria_canina.logica.Duenio;
import com.mycompany.proyecto_peluqueria_canina.logica.Mascota;
import com.mycompany.proyecto_peluqueria_canina.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlPersistencia {

    DuenioJpaController duenioJPA = new DuenioJpaController();
    MascotaJpaController mascotaJPA = new MascotaJpaController();

    public void crearMascota(Mascota masco, Duenio due) {
        duenioJPA.crear(due);
        mascotaJPA.crear(masco);
    }

    public List<Mascota> listarMascotas() {
        return mascotaJPA.listarMascotas();
    }

    public void eliminarMascota(int num_cliente) {
        try {
            mascotaJPA.eliminar(num_cliente);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControlPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public Mascota traerMascota(int num_cliente) {
        return mascotaJPA.buscarMascota(num_cliente);
    }

    public void modificarMascota(Mascota masco) {
        mascotaJPA.editar(masco);
    }

    public Duenio traerDuenio(int idDuenio) {
        return duenioJPA.buscarDuenio(idDuenio);
    }

    public void modificarDuenio(Duenio duenio) {
        duenioJPA.editar(duenio);
    }


    
}
