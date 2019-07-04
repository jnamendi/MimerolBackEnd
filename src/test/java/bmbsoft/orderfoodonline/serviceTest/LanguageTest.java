//package bmbsoft.orderfoodonline.serviceTest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.transaction.Transactional;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import bmbsoft.orderfoodonline.controller.LanguageController;
//import bmbsoft.orderfoodonline.entities.Country;
//import bmbsoft.orderfoodonline.entities.Language;
//import bmbsoft.orderfoodonline.model.LanguageViewModel;
//import bmbsoft.orderfoodonline.service.LanguageService;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = LanguageController.class, secure = false)
//public class LanguageTest {
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	LanguageService ls;
//
//	String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";
//
//	@Test
//	public void getAllLanguage() throws Exception {
//		
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/language/getAll")
//				.accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		String c = result.getResponse().getContentAsString();
//		System.out.println("dap.dv--------------");
//		System.out.println("xxx___" + c);
//	}
//
//	@Test
//	public void save() throws Exception {
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/language-resource/save")
//				.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		System.out.println(result.getResponse());
//	}
//
//}
