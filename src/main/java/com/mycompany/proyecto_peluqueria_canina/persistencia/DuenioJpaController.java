package com.mycompany.proyecto_peluqueria_canina.persistencia;

import com.mycompany.proyecto_peluqueria_canina.logica.Duenio;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DuenioJpaController implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PeluqueriaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void crear(Duenio duenio) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(duenio);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Duenio buscarDuenio(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Duenio.class, id);
        } finally {
            em.close();
        }
    }

    public void editar(Duenio duenio) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(duenio);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void eliminar(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Duenio duenio = em.find(Duenio.class, id);
            if (duenio != null) {
                em.remove(duenio);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public List<Duenio> listarDuenios() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT d FROM Duenio d");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
}