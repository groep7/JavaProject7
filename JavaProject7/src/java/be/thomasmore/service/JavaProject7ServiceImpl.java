/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.thomasmore.service;

import be.thomasmore.model.Klas;
import be.thomasmore.model.Student;
import be.thomasmore.model.Test;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Dries
 */
@Stateless
public class JavaProject7ServiceImpl implements JavaProject7Service {

    @PersistenceContext
    private EntityManager em;

    public String testZin() {
        return "Dit werkt";
    }

    @TransactionAttribute(REQUIRES_NEW)
    public List<Student> getAllStudenten() {
        Query q = em.createQuery("SELECT s FROM Student s");
        return (List<Student>) q.getResultList();
    }

    public void addStudent(Student student) {
        em.persist(student);
    }
    
    public void addKlas(Klas klas) {
        em.persist(klas);
    }

    public List<Klas> getAllKlassen() {
        Query q = em.createQuery("SELECT k FROM Klas k");
        return (List<Klas>) q.getResultList();
    }
}
