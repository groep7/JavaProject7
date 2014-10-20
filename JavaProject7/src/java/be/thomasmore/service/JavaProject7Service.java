/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.thomasmore.service;

import be.thomasmore.model.Student;
import java.util.List;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

/**
 *
 * @author Dries
 */

public interface JavaProject7Service {
      
    public String testZin();
    
    @TransactionAttribute(REQUIRES_NEW)
    public List<Student> getAllStudenten();
    public void addStudent(Student student);
}
