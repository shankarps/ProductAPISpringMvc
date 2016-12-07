package com.shankar.docs;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.shankar.controllers.ProductController;
import com.shankar.model.Product;
import com.shankar.service.ProductService;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class SpringRestDocsAPIDocumentationTest {

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private ProductController productController;

	@MockBean
	private ProductService productService;
	
	private Product testProduct;
	
	@Rule
	public JUnitRestDocumentation restDocumentation =
			new JUnitRestDocumentation("target/generated-snippets");

	private MockMvc mockMvc;
	
	private FieldDescriptor[] productDesc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation)) 
				.build();

		testProduct = new Product();
		testProduct.setCode("1");
		testProduct.setPrice(120.00d);
		testProduct.setName("Test 1");
		Set<String> tags = new HashSet<String>();
		tags.add("computer");
		testProduct.setTags(tags);
		
		productDesc = new FieldDescriptor[] {
				fieldWithPath("code").description("Unique identifier of the product"),
				fieldWithPath("name").description("Name of the Product"),
				fieldWithPath("price").description("Price of product"),
				fieldWithPath("tags").description("Collection of tags")};
	
	}

	@Test
	public void testGetProductByCode() throws Exception {

		given(this.productService.getProductByCode("1"))
		.willReturn(testProduct);
		
		this.mockMvc.perform(
				get("/products/1").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(document("products", responseFields(productDesc)));
	}
	
	@Test
	public void testGetAllProducts() throws Exception {

		given(this.productService.getProductByCode("1"))
		.willReturn(testProduct);
		
		this.mockMvc.perform(
				get("/products").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(document("productsList", responseFields(productDesc)));
	}

}
