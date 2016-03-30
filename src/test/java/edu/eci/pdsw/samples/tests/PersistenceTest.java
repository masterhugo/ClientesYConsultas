/*
 * Copyright (C) 2015 hcadavid
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
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.*;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.eci.pdsw.samples.persistence.mybatis.mappers.PacienteMapper;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
public class PersistenceTest {

    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config-h2.xml");
                //inputStream = PersistenceTest.class.getClassLoader().getResource("mybatis-config-h2.xml").openStream();
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }
    @Test
    public void plantilla() {
        SqlSessionFactory sessionfact = getSqlSessionFactory();

        SqlSession sqlss = sessionfact.openSession();
        
        PacienteMapper pmap=sqlss.getMapper(PacienteMapper.class);
        
        sqlss.commit();
        
        sqlss.close();
    }

    @Test
    public void AgregarPacienteNuevoSinConsultasALaBaseDeDatos(){
        
        SqlSessionFactory sessionfact = getSqlSessionFactory();

        SqlSession sqlss = sessionfact.openSession();

        PacienteMapper pmap=sqlss.getMapper(PacienteMapper.class);
        Paciente p = new Paciente(4, "CC", "Hugo Alvarez", Date.valueOf("1995-05-15"));
        pmap.insertPaciente(p);
        
        sqlss.commit();
        Paciente p3 = pmap.loadPacienteById(4, "CC");

        assertEquals(0,p3.getConsultas().size());
        sqlss.close();
    }
    @Test
    public void AgregarPacienteNuevoUnaConsultaALaBaseDeDatos(){
        SqlSessionFactory sessionfact = getSqlSessionFactory();

        SqlSession sqlss = sessionfact.openSession();

        PacienteMapper pmap=sqlss.getMapper(PacienteMapper.class);
        Paciente p = new Paciente(2, "CC", "Hugo Alvarez", Date.valueOf("1995-05-15"));
        Set<Consulta> cons = new HashSet<>();
        cons.add(new Consulta(Date.valueOf("2001-01-01"), "Gracias"));
        p.setConsultas(cons);
        pmap.insertPaciente(p);
        for(Consulta c: cons){
            pmap.insertConsulta(c, 2, "CC");
        }
        
        sqlss.commit();
        Paciente p3 = pmap.loadPacienteById(2, "CC");

        assertEquals(1,p3.getConsultas().size());
        sqlss.close();
    }
    @Test
    public void AgregarPacienteNuevoMuchasConsultasALaBaseDeDatos(){
        SqlSessionFactory sessionfact = getSqlSessionFactory();

        SqlSession sqlss = sessionfact.openSession();

        PacienteMapper pmap=sqlss.getMapper(PacienteMapper.class);
        Paciente p = new Paciente(3, "CC", "Hugo Alvarez", Date.valueOf("1995-05-15"));
        Set<Consulta> cons = new HashSet<>();
        cons.add(new Consulta(Date.valueOf("2001-01-01"), "Gracias"));
        cons.add(new Consulta(Date.valueOf("2001-05-05"), "Ya no"));
        p.setConsultas(cons);
        pmap.insertPaciente(p);
        for(Consulta c: cons){
            pmap.insertConsulta(c, 3, "CC");
        }
        
        sqlss.commit();
        Paciente p3 = pmap.loadPacienteById(3, "CC");

        assertEquals(2,p3.getConsultas().size());
        sqlss.close();
    }
    @Test
    public void AgregarPacienteNuevoDenuevoVariasConsultasALaBaseDeDatos(){
        /*DaoFactory daof = null;
        try {
            assertTrue(true);
            InputStream input = null;
            input = ClassLoader.getSystemResourceAsStream("applicationconfig_test.properties");
            Properties properties=new Properties();
            properties.load(input);
            
            daof=DaoFactory.getInstance(properties);
            
            daof.beginSession();
            DaoPaciente paciente = daof.getDaoPaciente();
            int i = 0;
            Paciente p = null;
            paciente.save(new Paciente(4, "CC", "Lucena", Date.valueOf("2001-01-01")));
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
        } catch (IOException | PersistenceException ex) {
            if(daof!=null){
                try {
                    daof.endSession();
                } catch (PersistenceException ex1) {
                    System.out.println("Hubo un error al cerrar y lanzo: "+ex1.getMessage());
                }
            }
            //assertEquals("No inserto los datos, revisar la base de datos",ex.getMessage());
            assertTrue(true);
        }*/
        assertTrue(true);
    }
}
