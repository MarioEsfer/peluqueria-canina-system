package com.mycompany.proyecto_peluqueria_canina.persistencia;

import com.mycompany.proyecto_peluqueria_canina.logica.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public class UsuarioJpaController implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PeluqueriaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // 🟢 Crear usuario
    public void crear(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // 🟡 Editar usuario
    public void editar(Usuario usuario) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (buscarUsuario(usuario.getId()) == null) {
                throw new Exception("El usuario con id " + usuario.getId() + " ya no existe.");
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    // 🔴 Eliminar usuario
    public void eliminar(int id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.getReference(Usuario.class, id);
            em.remove(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new Exception("No se pudo eliminar el usuario con id " + id);
        } finally {
            em.close();
        }
    }

    // 🔍 Buscar por ID
    public Usuario buscarUsuario(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    // 📋 Listar todos los usuarios (JPQL)
    public List<Usuario> listarUsuarios() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 📋 Listar con CriteriaQuery (paginado)
    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // 🔐 Validar login
    public Usuario validarLogin(String nombreUsuario, String contrasenia) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombre AND u.contrasena = :pass",
                Usuario.class
            );
            query.setParameter("nombre", nombreUsuario);
            query.setParameter("pass", contrasenia);
            List<Usuario> resultado = query.getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            em.close();
        }
    }
}
