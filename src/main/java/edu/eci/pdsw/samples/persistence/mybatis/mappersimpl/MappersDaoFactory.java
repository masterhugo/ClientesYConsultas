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
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author hcadavid
 */
public class MappersDaoFactory extends DaoFactory {
    
    

    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession sqlss = null;
    public MappersDaoFactory(Properties appProperties){
        sqlSessionFactory = null;
        try {
            InputStream inputStream;
            //inputStream = getClass().getClassLoader().getResource(config).openStream();
            inputStream = Resources.getResourceAsStream(appProperties.getProperty("config"));
            System.out.println("<<<<<<<<<<<-------"+appProperties);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException ex) {
            System.out.println(ex.getMessage()+" "+ex.getCause());
            throw new RuntimeException(ex.getCause());
        }
    }
    
    @Override
    public void beginSession() throws PersistenceException {
        //if(sqlss == null){
            sqlss = sqlSessionFactory.openSession();
        //}
    }


    
    
    @Override
    public void endSession() throws PersistenceException {
        if(sqlss!=null){
            sqlss.close();
        }
    }

    @Override
    public void commitTransaction() throws PersistenceException {
        if(sqlss!=null){
            sqlss.commit();
        }
    }

    @Override
    public void rollbackTransaction() throws PersistenceException {
        if(sqlss!=null){
            sqlss.rollback();
        }
    }

    @Override
    public DaoPaciente getDaoPaciente() {
        return new MappersDaoPaciente(sqlss);
    }
    
}
