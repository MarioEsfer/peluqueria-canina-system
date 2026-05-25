package com.mycompany.proyecto_peluqueria_canina.logica;

import com.mycompany.proyecto_peluqueria_canina.persistencia.ControlPersistencia;
import java.util.List;


public class ControlLogica {

    ControlPersistencia controlPersis = new ControlPersistencia();

    public void crearMascota(String nombreMasc, String raza, String color, boolean alergico, 
                               boolean especial, String nomDuenio, String celular, String obervaciones) {
        
        Duenio due = new Duenio();
        due.setNombre(nomDuenio);
        due.setCelDuenio(celular);
        
        
        Mascota masco = new Mascota();
        masco.setNombre_perro(nombreMasc);
        masco.setRaza(raza);
        masco.setColor(color);
        masco.setAlergico(alergico);
        masco.setAtencion_especial(especial);
        masco.setObservaciones(obervaciones);
        masco.setDue(due);
        
        controlPersis.crearMascota(masco, due);
    }

    public List<Mascota> listarMascotas() {
        return controlPersis.listarMascotas();
    }

    public void eliminarMascota(int num_cliente) {
        controlPersis.eliminarMascota(num_cliente);
    }

    public Mascota traerMascota(int num_cliente) {
        return controlPersis.traerMascota(num_cliente);
    }

    public void modificarMascota(Mascota masco, String nombreMasc, String raza, String color, 
                                 boolean alergico, boolean especial, String nomDuenio, String celular, 
                                 String obervaciones) {
        
        masco.setNombre_perro(nombreMasc);
        masco.setRaza(raza);
        masco.setColor(color);
        masco.setAlergico(alergico);
        masco.setAtencion_especial(especial);
        masco.setObservaciones(obervaciones);
        
        controlPersis.modificarMascota(masco);
        
        Duenio duenio = this.traerDuenio(masco.getDue().getIdDuenio());
        duenio.setNombre(nomDuenio);
        duenio.setCelDuenio(celular);
        
        this.modificarDuenio(duenio);
        
    }

    private Duenio traerDuenio(int idDuenio) {
        return controlPersis.traerDuenio(idDuenio);
    }

    private void modificarDuenio(Duenio duenio) {
        controlPersis.modificarDuenio(duenio);
    }

    

    

    
}
