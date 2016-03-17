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
package edu.eci.pdsw.samples.managedbeans;

import edu.eci.pdsw.samples.services.*;
import edu.eci.pdsw.samples.entities.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author hcadavid
 */
@ManagedBean(name="registro")
@SessionScoped
public class RegistroConsultaBean implements Serializable{
    private int id;
    private String tipo_id;
    private String nombre,campo;
    private String fechaNacimiento;
    private List<Paciente> pacientes;
    private String fechayHora;
    private String resumen;
    private List<Consulta> consultas =  new LinkedList<Consulta>();
    private final ServiciosPacientes sp=ServiciosPacientes.getInstance();
    private Paciente selectPaciente,pre;
    
    public void agregarConsulta(){
        try {
            sp.agregarConsultaAPaciente(selectPaciente.getId(), selectPaciente.getTipo_id(), new Consulta(Date.valueOf(fechayHora),resumen));
        } catch (ExcepcionServiciosPacientes ex) {
            campo = "No se pudo agregar :(";
        }
    }
    
    public List<Consulta> getConsultas(){
        return new ArrayList<>(selectPaciente.getConsultas());
    }

    public String getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(String fechayHora) {
        this.fechayHora = fechayHora;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }
    
    public String getCampo() {
        return campo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(String tipo_id) {
        this.tipo_id = tipo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Paciente getSelectPaciente() {
        return selectPaciente;
    }

    public void setSelectPaciente(Paciente selectPaciente) {
        this.selectPaciente = selectPaciente;
    }
    
    public void agregarPaciente(){
        try {
            sp.registrarNuevoPaciente(new Paciente(id,tipo_id,nombre,Date.valueOf(fechaNacimiento)));
        } catch (ExcepcionServiciosPacientes ex) {
            campo = "No se pudo agregar :(";
        }
    }
    
    public List<Paciente> getPacientes(){
        return sp.consultarPacientes();
    }
    
}
