package dev.dandeac.data_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dandeac.data_api.dtos.ProductDTO;
import dev.dandeac.data_api.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductsApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductService productService;

	private UUID productId;
	private ProductDTO productDTO;


	@Test
	void testCreateProduct() throws Exception {
		ProductDTO newProductDTO = new ProductDTO();
		newProductDTO.setName("New Test Product");
		newProductDTO.setPrice(200.0);

		mockMvc.perform(post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newProductDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(newProductDTO.getName()))
				.andExpect(jsonPath("$.price").value(newProductDTO.getPrice()));
	}

	@Test
	void testGetProduct() throws Exception {
		productDTO = new ProductDTO();
		productDTO.setName("Test Product11");
		productDTO.setPrice(100.0);

		ProductDTO savedProduct = productService.addProduct(productDTO);
		productId = savedProduct.getProductId();

		mockMvc.perform(get("/product/" + productId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(productId.toString()))
				.andExpect(jsonPath("$.name").value(productDTO.getName()))
				.andExpect(jsonPath("$.price").value(productDTO.getPrice()));
	}

	@Test
	void testUpdateProduct() throws Exception {
		productDTO = new ProductDTO();
		productDTO.setName("Test Product23");
		productDTO.setPrice(100.0);

		ProductDTO savedProduct = productService.addProduct(productDTO);
		productId = savedProduct.getProductId();
		ProductDTO updatedProductDTO = new ProductDTO();
		updatedProductDTO.setName("Updated Test Product1");
		updatedProductDTO.setPrice(300.0);

		mockMvc.perform(put("/product/" + productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedProductDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(productId.toString()))
				.andExpect(jsonPath("$.name").value(updatedProductDTO.getName()))
				.andExpect(jsonPath("$.price").value(updatedProductDTO.getPrice()));
	}

	@Test
	void testDeleteProduct() throws Exception {
		productDTO = new ProductDTO();
		productDTO.setName("Test Product33");
		productDTO.setPrice(100.0);

		ProductDTO savedProduct = productService.addProduct(productDTO);
		productId = savedProduct.getProductId();

		mockMvc.perform(delete("/product/" + productId))
				.andExpect(status().isNoContent());
	}

	@Test
	void testCreateProductValidation() throws Exception {
		ProductDTO newProductDTO = new ProductDTO();
		newProductDTO.setName("");
		newProductDTO.setPrice(-100.0);

		mockMvc.perform(post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Name cannot be empty")))
				.andExpect(content().string(containsString("Price must be positive")));
	}

	@Test
	void testUpdateProductValidation() throws Exception {
		ProductDTO updatedProductDTO = new ProductDTO();
		updatedProductDTO.setName("");
		updatedProductDTO.setPrice(-100.0);

		mockMvc.perform(put("/product/" + productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Name cannot be empty")))
				.andExpect(content().string(containsString("Price must be positive")));
	}

	@Test
	void testGetProductNotFound() throws Exception {
		mockMvc.perform(get("/product/" + UUID.randomUUID()))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteProductNotFound() throws Exception {
		mockMvc.perform(delete("/product/" + UUID.randomUUID()))
				.andExpect(status().isNotFound());
	}

	@Test
	void testUpdateProductNotFound() throws Exception {
		ProductDTO updatedProductDTO = new ProductDTO();
		updatedProductDTO.setName("Updated Test Product2");
		updatedProductDTO.setPrice(300.0);

		mockMvc.perform(put("/product/" + UUID.randomUUID())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedProductDTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testCreateProductBadRequest() throws Exception {
		mockMvc.perform(post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testUpdateProductBadRequest() throws Exception {
		mockMvc.perform(put("/product/" + productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testCreateProductBadRequest2() throws Exception {
		ProductDTO newProductDTO = new ProductDTO();
		newProductDTO.setName("New Test Product1");
		newProductDTO.setPrice(-100.0);

		mockMvc.perform(post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Price must be positive"));
	}

	@Test
	void testUpdateProductBadRequest2() throws Exception {
		ProductDTO updatedProductDTO = new ProductDTO();
		updatedProductDTO.setName("Updated Test Product4");
		updatedProductDTO.setPrice(-100.0);

		mockMvc.perform(put("/product/" + productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Price must be positive"));
	}

	@Test
	void testCreateProductBadRequest3() throws Exception {
		ProductDTO newProductDTO = new ProductDTO();
		newProductDTO.setName("");
		newProductDTO.setPrice(100.0);

		mockMvc.perform(post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Name cannot be empty"));
	}

	@Test
	void testUpdateProductBadRequest3() throws Exception {
		ProductDTO updatedProductDTO = new ProductDTO();
		updatedProductDTO.setName("");
		updatedProductDTO.setPrice(100.0);

		mockMvc.perform(put("/product/" + productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Name cannot be empty"));
	}

	@Test
	void testCreateProductBadRequest4() throws Exception {
		ProductDTO newProductDTO = new ProductDTO();
		newProductDTO.setName(null);
		newProductDTO.setPrice(100.0);

		mockMvc.perform(post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Name cannot be empty")))
				.andExpect(content().string(containsString("Name cannot be null")));
	}

	@Test
	void testUpdateProductBadRequest4() throws Exception {
		ProductDTO updatedProductDTO = new ProductDTO();
		updatedProductDTO.setName(null);
		updatedProductDTO.setPrice(100.0);

		mockMvc.perform(put("/product/" + productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Name cannot be null")))
				.andExpect(content().string(containsString("Name cannot be empty")));

	}

	@Test
	void testCreateProductBadRequest5() throws Exception {
		ProductDTO newProductDTO = new ProductDTO();
		newProductDTO.setName("New Test Product2");
		newProductDTO.setPrice(null);

		mockMvc.perform(post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Price cannot be null"));
	}

	@Test
	void testUpdateProductBadRequest5() throws Exception {
		ProductDTO updatedProductDTO = new ProductDTO();
		updatedProductDTO.setName("Updated Test Product");
		updatedProductDTO.setPrice(null);

		mockMvc.perform(put("/product/" + productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Price cannot be null"));
	}

	@Test
	void testCreateProductBadRequest6() throws Exception {
		ProductDTO newProductDTO = new ProductDTO();
		newProductDTO.setName(null);
		newProductDTO.setPrice(null);

		mockMvc.perform(post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Name cannot be empty")))
				.andExpect(content().string(containsString("Name cannot be null")))
				.andExpect(content().string(containsString("Price cannot be null")));
	}

	@Test
	void testUpdateProductBadRequest6() throws Exception {
		ProductDTO updatedProductDTO = new ProductDTO();
		updatedProductDTO.setName(null);
		updatedProductDTO.setPrice(null);

		mockMvc.perform(put("/product/" + productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Name cannot be empty")))
				.andExpect(content().string(containsString("Name cannot be null")))
				.andExpect(content().string(containsString("Price cannot be null")));
	}

	@Test
	void testCreateProductUniqueConstraint() throws Exception {
		productDTO = new ProductDTO();
		productDTO.setName("Test Product48");
		productDTO.setPrice(100.0);

		mockMvc.perform(post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productDTO)))
				.andExpect(status().isCreated());

		mockMvc.perform(post("/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Product with name " + productDTO.getName() + " already exists"));
	}

	@Test
	void testUpdateProductUniqueConstraint() throws Exception {
		productDTO = new ProductDTO();
		productDTO.setName("Test Product55");
		productDTO.setPrice(100.0);

		ProductDTO savedProduct = productService.addProduct(productDTO);
		productId = savedProduct.getProductId();

		ProductDTO updatedProductDTO = new ProductDTO();
		updatedProductDTO.setName("Test Product55");
		updatedProductDTO.setPrice(300.0);

		mockMvc.perform(put("/product/" + productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedProductDTO)))
				.andExpect(status().isOk());
	}

	@Test
	void testUpdateProductUniqueConstraintExistingProduct() throws Exception {
		productDTO = new ProductDTO();
		productDTO.setName("Test Product56");
		productDTO.setPrice(100.0);

		ProductDTO savedProduct = productService.addProduct(productDTO);
		productId = savedProduct.getProductId();

		ProductDTO updatedProductDTO = new ProductDTO();
		updatedProductDTO.setName("Test Product55");
		updatedProductDTO.setPrice(300.0);

		mockMvc.perform(put("/product/" + productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedProductDTO)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Product with name " + updatedProductDTO.getName() + " already exists"));
	}

	@Test
	void testDeleteAllProducts() throws Exception {
		mockMvc.perform(delete("/product"))
				.andExpect(status().isNoContent());
	}
}