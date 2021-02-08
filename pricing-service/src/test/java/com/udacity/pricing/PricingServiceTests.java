package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class PricingServiceTests {
    private final Integer VALID_VEHICLE_ID = 5;
    private final Integer INVALID_VEHICLE_ID = 100;
    @Autowired
    private TestRestTemplate rTemplate;

    @LocalServerPort
    private int port;

    public ResponseEntity accessUrl(Integer vehicleId) {
         String url;

        if (vehicleId == 0) {
            url = "http://localhost:" + port + "/prices/";
        } else {
            url = "http://localhost:" + port + "/prices/" + vehicleId;
        }

        ResponseEntity<String> res = this.rTemplate.getForEntity(url, String.class);
        return res;
    }

    @Test
    public void getAllVehiclePrices() {
        ResponseEntity<String> res = accessUrl(0);
        assertThat(res.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getValidVehiclePrice() {
        ResponseEntity<String> res = accessUrl(VALID_VEHICLE_ID);
        assertThat(res.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getInvalidVehiclePrice() {
        ResponseEntity<String> res = accessUrl(INVALID_VEHICLE_ID);
        assertThat(res.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }
}
