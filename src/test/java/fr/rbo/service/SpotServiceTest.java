package fr.rbo.service;

import fr.rbo.model.Spot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpotServiceTest {
    private Spot spot = null;

    @Autowired
    SpotServiceInterface spotServiceInterface;

    @Before
    public void creationSpot(){
        spot = new Spot();
        spot.setNomSpot("spottest");
        spot = spotServiceInterface.saveSpot(spot);
    }

    @Test
    public void testRecuperationSpotById() {
        Long id = spot.getIdSpot();
        assertEquals("spottest",spotServiceInterface.findSpot(id).getNomSpot());
    }

    @After
    public void suppressionSpot(){
        Long id = spot.getIdSpot();
        assertEquals(true, spotServiceInterface.deleteSpot(id));
    }

}
