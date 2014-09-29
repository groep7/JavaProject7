/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.thomasmore.controller;

import be.thomasmore.model.Student;
import be.thomasmore.service.JavaProject7Service;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Dries
 */

@ManagedBean(name="javaProject7Controller")
public class JavaProject7Controller {
    
    private List<Student> studenten;
    
    @EJB
    private JavaProject7Service javaProject7Service;
    
    public List<Student> getStudenten() {
        return studenten;
    }
    
    public void setStudenten(List<Student> studenten) {
        this.studenten = studenten;
    }
    
}
