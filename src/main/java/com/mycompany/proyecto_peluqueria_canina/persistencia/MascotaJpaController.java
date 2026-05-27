package com.mycompany.proyecto_peluqueria_canina.persistencia;

import com.mycompany.proyecto_peluqueria_canina.logica.Mascota;
import com.mycompany.proyecto_peluqueria_canina.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class MascotaJpaController implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PeluqueriaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void crear(Mascota mascota) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(mascota);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Mascota buscarMascota(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mascota.class, id);
        } finally {
            em.close();
        }
    }

    public void editar(Mascota mascota) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(mascota);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void eliminar(int id) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Mascota mascota = em.find(Mascota.class, id);
            if (mascota == null) {
                throw new NonexistentEntityException(
                        "La mascota con id " + id + " no existe."
                );
            }
            em.remove(mascota);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public List<Mascota> listarMascotas() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT m FROM Mascota m");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
}