/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.services;
 
import edu.eci.pdsw.samples.entities.*;
import edu.eci.pdsw.samples.persistence.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author Hugo Alvarez
 */
public class ServiciosPacientesDAO extends ServiciosPacientes{

    public DaoFactory daof = null;
    public ServiciosPacientesDAO() {
        InputStream input = null;
        try {
            input = getClass().getClassLoader().getResource("applicationconfig.properties").openStream();
            Properties properties=new Properties();
            properties.load(input);
            daof=DaoFactory.getInstance(properties);
        } catch (IOException ex) {
            Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(input!=null) input.close();
            } catch (IOException ex) {
                Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        Paciente p = null;
        try {
            daof.beginSession();
            DaoPaciente paciente = daof.getDaoPaciente();
            p = paciente.load(idPaciente, tipoid);
            daof.commitTransaction();        
            daof.endSession();
        } catch (PersistenceException ex) {
            if(daof!=null){
                try {
                    daof.rollbackTransaction();
                    daof.endSession();
                } catch (PersistenceException ex1) {
                    Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        return p;
    }

    @Override
    public void registrarNuevoPaciente(Paciente p) throws ExcepcionServiciosPacientes {
        try {
            daof.beginSession();
            DaoPaciente paciente = daof.getDaoPaciente();
            String check =null;
            Paciente p1 = null;
            try{
                p1 = paciente.load(p.getId(),p.getTipo_id());
            }catch(PersistenceException ex){
                check = ex.getMessage();
            }
            if(check!=null){
                paciente.save(p);
            }else paciente.update(p);
            daof.commitTransaction();        
            daof.endSession();
        } catch (PersistenceException ex) {
            if(daof!=null){
                try {
                    daof.rollbackTransaction();
                    daof.endSession();
                } catch (PersistenceException ex1) {
                    Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

    @Override
    public void agregarConsultaAPaciente(int idPaciente, String tipoid, Consulta c) throws ExcepcionServiciosPacientes {
        try {
            daof.beginSession();
            DaoPaciente paciente = daof.getDaoPaciente();
            String check = null;
            Paciente p1 = null;
            try{
                p1 = paciente.load(idPaciente,tipoid);
            }catch(PersistenceException ex){
                System.out.println("");
                check = ex.getMessage();
            }
            if(check == null){
                Set<Consulta> cons = p1.getConsultas();
                cons.add(c);
                p1.setConsultas(cons);
                paciente.update(p1);
                
            }
            
            daof.commitTransaction();        
            daof.endSession();
        } catch (PersistenceException ex) {
            if(daof!=null){
                try {
                    daof.rollbackTransaction();
                    daof.endSession();
                } catch (PersistenceException ex1) {
                    Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

    @Override
    public ArrayList<Paciente> consultarPacientes() {
        ArrayList<Paciente> p = null;
        try {
            daof.beginSession();
            DaoPaciente paciente = daof.getDaoPaciente();
            p = paciente.loadAll();
            daof.commitTransaction();        
            daof.endSession();
        } catch (PersistenceException ex) {
            if(daof!=null){
                try {
                    daof.rollbackTransaction();
                    daof.endSession();
                } catch (PersistenceException ex1) {
                    Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        return p;
    }

    @Override
    public ArrayList<Consulta> consultarConsultas(Paciente p) {
        ArrayList<Consulta> cons = null;
        try {
            daof.beginSession();
            DaoPaciente paciente = daof.getDaoPaciente();
            String check =null;
            Paciente p1 = null;
            try{
                p1 = paciente.load(p.getId(),p.getTipo_id());
            }catch(PersistenceException ex){
                check = ex.getMessage();
            }
            if(check == null){
                cons = new ArrayList<>(p1.getConsultas());
            }
            daof.commitTransaction();        
            daof.endSession();
        } catch (PersistenceException ex) {
            if(daof!=null){
                try {
                    daof.rollbackTransaction();
                    daof.endSession();
                } catch (PersistenceException ex1) {
                    Logger.getLogger(ServiciosPacientesDAO.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        return cons;
    }
    
}
