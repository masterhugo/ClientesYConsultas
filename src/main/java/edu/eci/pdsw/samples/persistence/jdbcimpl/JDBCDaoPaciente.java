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
package edu.eci.pdsw.samples.persistence.jdbcimpl;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
public class JDBCDaoPaciente implements DaoPaciente {

    Connection con;

    public JDBCDaoPaciente(Connection con) {
        this.con = con;
    }
        

    @Override
    public Paciente load(int idpaciente, String tipoid) throws PersistenceException {
        PreparedStatement ps;
        String select = "select pac.nombre, pac.fecha_nacimiento, con.idCONSULTAS, con.fecha_y_hora, con.resumen from PACIENTES as pac left join CONSULTAS as con on con.PACIENTES_id=pac.id and con.PACIENTES_tipo_id=pac.tipo_id where (con.PACIENTES_id is null or con.PACIENTES_tipo_id is null or con.PACIENTES_id is not null) and pac.id=? and pac.tipo_id=?";
        Paciente p = null;
        String nombre = null;
        Date fechanam = null;
        ResultSet rs;
        Set<Consulta> cons = new HashSet<Consulta>();
        try {
            ps = con.prepareStatement(select,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, idpaciente);
            ps.setString(2, tipoid);
            rs = ps.executeQuery();
            if(!rs.first()) throw new PersistenceException("No encontro ningun resultado.");
            rs.beforeFirst();
            while(rs.next()){
                nombre = rs.getString("nombre");
                fechanam = rs.getDate("fecha_nacimiento");
                if(rs.getDate("fecha_y_hora")!= null) cons.add(new Consulta(rs.getInt("idCONSULTAS"),rs.getDate("fecha_y_hora"), rs.getString("resumen")));
            }
            if(nombre != null){
                p = new Paciente(idpaciente,tipoid,nombre,fechanam);
                p.setConsultas(cons);
            }else throw new PersistenceException("No encontro ningun resultado.");
            
        } catch (SQLException ex) {
            throw new PersistenceException("Un error ocurrio cuando se intento cargar el paciente: "+idpaciente+" "+ex.getMessage(),ex);
        }
        return p;
    }

    @Override
    public void save(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        String insert = "insert into PACIENTES(id,tipo_id,nombre,fecha_nacimiento) values(?,?,?,?)";
        try {
            int id = p.getId();
            String tipo = p.getTipo_id();
            //registro el paciente
            Date fecha = p.getFechaNacimiento();
            String nombre = p.getNombre();
            ps = con.prepareStatement(insert);
            ps.setInt(1, id);
            ps.setString(2, tipo);
            ps.setString(3, nombre);
            ps.setDate(4, fecha);
            ps.executeUpdate();
            
            Set<Consulta> cons = p.getConsultas();
            //registro las consultas del paciente
            insert = "insert into CONSULTAS(fecha_y_hora,resumen,PACIENTES_id,PACIENTES_tipo_id) values(?,?,?,?)";
            ps = con.prepareStatement(insert);
            for (Consulta con1 : cons) {
                ps.setDate(1, con1.getFechayHora());
                ps.setString(2, con1.getResumen());
                ps.setInt(3, id);
                ps.setString(4, tipo);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("No inserto los datos, revisar la base de datos",ex);
        }
        

    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        String delete1 = "delete from CONSULTAS where PACIENTES_id = ? and PACIENTES_tipo_id = ?";
        String delete2 = "delete from PACIENTES where id = ? and tipo_id = ?";
        try {
            ps = con.prepareStatement(delete1);
            ps.setInt(1, p.getId());
            ps.setString(2, p.getTipo_id());
            ps.executeUpdate();
            ps = con.prepareStatement(delete2);
            ps.setInt(1, p.getId());
            ps.setString(2, p.getTipo_id());
            ps.executeUpdate();
            this.save(p);
            
        } catch (SQLException ex) {
            System.out.println("Entro a la excepcion con "+ex.getMessage());
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        } 
        
    }

    @Override
    public ArrayList<Paciente> loadAll() throws PersistenceException {
        PreparedStatement ps;
        String select = "select * from PACIENTES";
        ResultSet rs;
        int id;
        String nombre,tipo;
        Date fechanam;
        ArrayList<Paciente> pas = new ArrayList<>();
        try {
            ps = con.prepareStatement(select,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = ps.executeQuery();
            if(!rs.first()) throw new PersistenceException("No encontro ningun resultado.");
            rs.beforeFirst();
            while(rs.next()){
                id = rs.getInt("id");
                tipo = rs.getString("tipo_id");
                nombre = rs.getString("nombre");
                fechanam = rs.getDate("fecha_nacimiento");
                pas.add(new Paciente(id, tipo, nombre, fechanam));
            }         
        } catch (SQLException ex) {
            System.out.println("Hubo un error en: "+ex.getMessage());
        }
        return pas;
    }
    
}
