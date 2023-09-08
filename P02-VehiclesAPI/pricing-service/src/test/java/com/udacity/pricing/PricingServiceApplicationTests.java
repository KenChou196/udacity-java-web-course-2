package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.udacity.pricing.domain.price.PriceRepository;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@SpringBootTest
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {

	@Test
	public void contextLoads() {
	}
	@Autowired

	private MockMvc mockMvc;
	@Autowired
	private PriceRepository pricingRepository;

	@Test
	public void testCreatePrice() {

		// Tạo một đối tượng Price mới
		Price price = new Price();
		price.setVehicleId(1L);
		price.setCurrency("USD");
		price.setPrice(BigDecimal.valueOf(10000));

		// Lưu Price vào cơ sở dữ liệu
		Price savedPrice = pricingRepository.save(price);

		// Kiểm tra xem Price đã được lưu thành công hay chưa
		assertNotNull(savedPrice);
		assertNotNull(savedPrice.getPrice());
	}
//	@Test
//	public void testGetPrice() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/prices/{vehicleId}", 1)
//						.accept(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.vehicleId").value(1));
//	}


}
