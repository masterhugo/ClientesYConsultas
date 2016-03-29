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
package edu.eci.pdsw.samples.persistence.mybatis.mappersimpl;

import edu.eci.pdsw.samples.persistence.jdbcimpl.*;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import edu.eci.pdsw.samples.persistence.mybatis.mappers.PacienteMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author hcadavid
 */
public class MappersDaoPaciente implements DaoPaciente {

    SqlSession sqlss;
    PacienteMapper mp;
    public MappersDaoPaciente(SqlSession sqlss) {
        this.sqlss = sqlss;
        mp = sqlss.getMapper(PacienteMapper.class);
    }
        

    @Override
    public Paciente load(int idpaciente, String tipoid) throws PersistenceException {
        if(mp.loadPacienteById(idpaciente, tipoid)==null) throw new PersistenceException("No existe el paciente");
        return mp.loadPacienteById(idpaciente, tipoid);
    }

    @Override
    public void save(Paciente p) throws PersistenceException {
        if(mp.loadPacienteById(p.getId(), p.getTipo_id())!=null) throw new PersistenceException("El Paciente ya existe");
        mp.insertPaciente(p);
        for (Consulta col : p.getConsultas()) {
            mp.insertConsulta(col, p.getId(), p.getTipo_id());
        }

    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        if(mp.loadPacienteById(p.getId(), p.getTipo_id())==null) throw new PersistenceException("No existe el paciente");
        Paciente p3 = mp.loadPacienteById(p.getId(), p.getTipo_id());
        for (Consulta col : p3.getConsultas()) {
            
        }
    }

    @Override
    public ArrayList<Paciente> loadAll() throws PersistenceException {
        return new ArrayList<>(mp.loadPacientes());
    }
    
}
