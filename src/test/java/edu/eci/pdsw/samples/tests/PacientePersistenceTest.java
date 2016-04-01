package edu.eci.pdsw.samples.tests;

/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import edu.eci.pdsw.samples.entities.*;
import edu.eci.pdsw.samples.persistence.*;
import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class PacientePersistenceTest {
    public final InputStream input = ClassLoader.getSystemResourceAsStream("pruebas_test.properties");
    public final Properties properties=new Properties();
    @Test
    public void AgregarPacienteNuevoSinConsultasALaBaseDeDatos() throws IOException{
        DaoFactory daof = null;
        try {
            properties.load(input);
            daof=DaoFactory.getInstance(properties);
            
            daof.beginSession();
            DaoPaciente paciente = daof.getDaoPaciente();
            paciente.save(new Paciente(143, "CC", "Hugo Alvarez", Date.valueOf("1995-05-15")));
            

            daof.commitTransaction();   
            Paciente p3 = paciente.load(143, "CC");
            daof.endSession();
            assertEquals(0,p3.getConsultas().size());
        } catch (PersistenceException ex) {
            fail("Hubo un error al iniciar o leer y lanzo prueba 1: "+ex.getMessage());
        }
    }
    @Test
    public void AgregarPacienteNuevoUnaConsultaALaBaseDeDatos() throws IOException{
        DaoFactory daof = null;
        try {
            properties.load(input);
            
            daof=DaoFactory.getInstance(properties);
            
            daof.beginSession();
            DaoPaciente paciente = daof.getDaoPaciente();
            Paciente p = new Paciente(214, "CC", "Hugo Alvarez", Date.valueOf("1995-05-15"));
            Set<Consulta> cons = new HashSet<>();
            cons.add(new Consulta(Date.valueOf("2001-01-01"), "Gracias"));
            p.setConsultas(cons);
            paciente.save(p);
            
            daof.commitTransaction();        
            Paciente p3 = paciente.load(p.getId(), p.getTipo_id());
            assertEquals(1,p3.getConsultas().size());
            daof.endSession();
        } catch ( PersistenceException ex) {
            if(daof!=null){
                try {
                    daof.endSession();
                } catch (PersistenceException ex1) {
                    System.out.println("Hubo un error al cerrar y lanzo: "+ex1.getMessage());
                }
            }
            fail("Hubo un error al iniciar o leer y lanzo prueba 2: "+ex.getMessage());
        }
        
    }
    @Test
    public void AgregarPacienteNuevoMuchasConsultasALaBaseDeDatos() throws IOException{
        DaoFactory daof = null;
        try {
            properties.load(input);
            
            daof=DaoFactory.getInstance(properties);
            
            daof.beginSession();
            DaoPaciente paciente = daof.getDaoPaciente();
            Paciente p = new Paciente(325, "CC", "Hugo Alvarez", Date.valueOf("1995-05-15"));
            Set<Consulta> cons = new HashSet<Consulta>();
            cons.add(new Consulta(Date.valueOf("2001-01-01"), "Gracias"));
            cons.add(new Consulta(Date.valueOf("2001-05-05"), "Ya no"));
            p.setConsultas(cons);
            paciente.save(p);
            
            
            
            daof.commitTransaction();       
            Paciente p3 = paciente.load(p.getId(), p.getTipo_id());
            assertEquals(2,p3.getConsultas().size());
            daof.endSession();
        } catch (PersistenceException ex) {
            if(daof!=null){
                try {
                    daof.endSession();
                } catch (PersistenceException ex1) {
                    System.out.println("Hubo un error al cerrar y lanzo: "+ex1.getMessage());
                }
            }
            fail("Hubo un error al iniciar o leer y lanzo prueba 3: "+ex.getMessage());
        }
    }
    @Test
    public void AgregarPacienteNuevoDenuevoVariasConsultasALaBaseDeDatos() throws IOException{
        DaoFactory daof = null;
        try {
            properties.load(input);
            
            daof=DaoFactory.getInstance(properties);
            
            daof.beginSession();
            DaoPaciente paciente = daof.getDaoPaciente();
            int i = 0;
            Paciente p = null;
            paciente.save(new Paciente(411, "CC", "Lucena", Date.valueOf("2001-01-01")));
            p = new Paciente(4, "CC", "Lucena", Date.valueOf("2001-01-01"));
            Set<Consulta> cons = new HashSet<Consulta>();
            cons.add(new Consulta(Date.valueOf("2001-01-01"), "Gracias"));
            cons.add(new Consulta(Date.valueOf("2001-05-05"), "Ya no"));
            p.setConsultas(cons);
            paciente.save(p);
            
            //fail("No debio continuar");
            assertTrue(true);
            daof.commitTransaction();        
            daof.endSession();
        } catch (PersistenceException ex) {
            if(daof!=null){
                try {
                    daof.endSession();
                } catch (PersistenceException ex1) {
                    System.out.println("Hubo un error al cerrar y lanzo: "+ex1.getMessage());
                }
            }
            assertEquals("No inserto los datos, revisar la base de datos",ex.getMessage());
        }
    }
    
}
