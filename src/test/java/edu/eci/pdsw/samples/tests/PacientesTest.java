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
package edu.eci.pdsw.samples.tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.eci.pdsw.samples.entities.*;
import edu.eci.pdsw.samples.services.*;
import java.sql.Date;
/**
 *
 * @author hcadavid
 */
public class PacientesTest {
    
    /**
     * Clases de equvalencia
     * 
     */
    
    @Test
    public void CE1RegistroConsultaPacientesError() throws ExcepcionServiciosPacientes{
        ServiciosPacientes sp = new ServiciosPacientesStub();
        String paramDateAsString = "1995-05-15";
        Paciente p = new Paciente(0,"cc" , "Hugo Alvarez",Date.valueOf(paramDateAsString));
        sp.registrarNuevoPaciente(p);
        assertEquals(p.getNombre(),sp.consultarPaciente(0, "cc").getNombre());
    }
}
