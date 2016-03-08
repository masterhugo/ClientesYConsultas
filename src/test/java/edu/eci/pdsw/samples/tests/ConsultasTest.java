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

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.eci.pdsw.samples.entities.*;
import edu.eci.pdsw.samples.services.*;
import java.sql.*;
import java.text.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author hcadavid
 */
public class ConsultasTest {
  /**
   * Clases de Equivalencia
   * Que agergue la consulta bien
   * Que el id exista pero no el tipo de id
   * que el tipo de id exista pero no el id
   * Que ninguno de los dos exista
   * Que el id sea negativo o cero
   * Que el Tipo de id sea desconocido
   * Que la fecha sea mayor a la actual
   * Que la descripcion sea nula
   */
  @Test
  public void CE1RegistroConsultaPacientesError(){
      try {
          ServiciosPacientes sp = new ServiciosPacientesStub();
          String paramDateAsString = "1995-05-15";
          sp.agregarConsultaAPaciente(1, "cc", new Consulta(Date.valueOf(paramDateAsString), "El paciente esta muerto"));
          fail("No debio agregarlo");
      } catch (ExcepcionServiciosPacientes ex) {
          assertTrue(true);
      }
  }
}
