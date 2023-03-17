package com.learn.microservices.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.microservices.productservice.controller.ProductController;
import com.learn.microservices.productservice.repository.ProductRepository;
import com.learn.microservices.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;
   
    @InjectMocks
    private ProductController productController;
    
    @Mock
    private ProductService productService;
    
    @BeforeEach
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(productController).build();
    }
	@Test
	void testGetRequest() throws Exception {
        mockMvc.perform(get("/api/product"))
              //  .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

	}

}
