package com.frankmoley.lil.security.data;

import java.util.List;

import com.frankmoley.lil.security.util.DatabaseUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PresidentDAOTest {

    @BeforeClass
    public static void setUp() throws Exception {
        DatabaseUtil.loadFile("schema.sql");
        DatabaseUtil.loadFile("data.sql");
    }

    @Test
    public void getByLastName() {
        PresidentDAO dao = new PresidentDAO();
        List<President> results = dao.getByLastName("Washington");
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("George", results.get(0).getFirstName());
    }

    @Test
    public void getByLastName_Injections(){
        PresidentDAO dao = new PresidentDAO();
        List<President> results = dao.getByLastName("Trump' or '1' = '1");
        assertNotNull(results);
        assertEquals(0, results.size());
    }
}