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
package edu.eci.pdsw.samples.services;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author hcadavid
 */
public abstract class ServiciosPacientes implements Serializable{
    
    
    private static ServiciosPacientes instance=new ServiciosPacientesDAO();
    
    
    protected ServiciosPacientes(){        

    }
    
    public static ServiciosPacientes getInstance() throws RuntimeException{        
        return instance;
    }

    /**
     * Consultar un paciente dado su identificador.
     * @param idPaciente identificador del paciente
     * @param tipoid tipo de identificación del paciente
     * @return el paciente con el identificador dado
     * @throws ExcepcionServiciosPacientes  si el paciente no existe
     */
    public abstract Paciente consultarPaciente(int idPaciente,String tipoid) throws ExcepcionServiciosPacientes;
    
    
    /**
     * Registra un nuevo paciente en el sistema
     * @param p, El nuevo paciente
     * @throws ExcepcionServiciosPacientes si se presenta algún error lógico
     * o de persistencia (por ejemplo, si el paciente ya existe).
     */
    public abstract void registrarNuevoPaciente(Paciente p) throws ExcepcionServiciosPacientes;
    
    /**
     * Agrega una consulta a un paciente ya registrado
     * @param idPaciente el identificador del paciente
     * @param tipoid el tipo de identificación
     * @param c la consulta a ser agregada
     * @throws ExcepcionServiciosPacientes si se presenta algún error de persistencia o si el paciente no existe.
     */
    public abstract void agregarConsultaAPaciente(int idPaciente,String tipoid,Consulta c) throws ExcepcionServiciosPacientes;
    
    /**
     * Obtener todos los pacientes del sistema
     * @return la lista de pacientes encontrados en el sistema
     */
    public abstract ArrayList<Paciente> consultarPacientes();
    
    /**
     * Obtener todos las consultas del paciente
     * @param p, paciente a revisar sus consultas
     * @return la lista de consultas del paciente encontrados en el sistema
     */
    public abstract ArrayList<Consulta> consultarConsultas(Paciente p);
}
