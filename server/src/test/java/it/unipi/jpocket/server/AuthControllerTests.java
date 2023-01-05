package it.unipi.jpocket.server;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unipi.jpocket.server.controller.AuthController;
import it.unipi.jpocket.server.model.User;
import it.unipi.jpocket.server.repository.UserRepository;
import it.unipi.jpocket.server.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AuthController.class)

public class AuthControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserService userService;

	private static final ObjectMapper mapper = new ObjectMapper();

	private static User testUser;

	@BeforeAll
	static void init() {
		// Creo un utente di test
		testUser = new User("TestUsername", "TestPassword");
		testUser.setId(UUID.randomUUID());
	}

	@Test
	void testAuthSignup() throws Exception{

		// Creo un HashMap con le credenziali di test
		Map<String, String> testCredentials = new HashMap<>() {{
			put("username", testUser.getUsername());
			put("password", testUser.getPassword());
		}};

		// Quando viene chiamato il metodo signup di userService, ritorna testUser
		Mockito.when(userService.signup(testCredentials.get("username"), testCredentials.get("password")))
			.thenReturn(testUser);
		
		// Converto le credenziali di test in un JSON
		String jsonCredentials = mapper.writeValueAsString(testCredentials);
		
		// Creo una richiesta POST con le credenziali di test
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/signup")
			.accept(MediaType.APPLICATION_JSON).content(jsonCredentials)
			.contentType(MediaType.APPLICATION_JSON);
		
		String jsonResponse = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();

		Map<String, UUID> response = mapper.readValue(jsonResponse, new TypeReference<Map<String, UUID>>(){});

		// Verifico che l'id ritornato sia uguale a quello dell'utente di test
		assertTrue(response.containsKey("id"));
		assertTrue(response.get("id").compareTo(testUser.getId()) == 0);
	}

	@Test
	void testAuthLogin() throws Exception{

		// Creo un HashMap con le credenziali di test
		Map<String, String> testCredentials = new HashMap<>() {{
			put("username", testUser.getUsername());
			put("password", testUser.getPassword());
		}};

		// Quando viene chiamato il metodo login di userService, ritorna testUser
		Mockito.when(userService.login(testCredentials.get("username"), testCredentials.get("password")))
			.thenReturn(testUser);
		
		ObjectMapper mapper = new ObjectMapper();
		
		// Converto le credenziali di test in un JSON
		String jsonCredentials = mapper.writeValueAsString(testCredentials);
		
		// Creo una richiesta POST con le credenziali di test
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/login")
			.accept(MediaType.APPLICATION_JSON).content(jsonCredentials)
			.contentType(MediaType.APPLICATION_JSON);
		
		String jsonResponse = mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();

		Map<String, UUID> response = mapper.readValue(jsonResponse, new TypeReference<Map<String, UUID>>(){});

		// Verifico che l'id ritornato sia uguale a quello dell'utente di test
		assertTrue(response.containsKey("id"));
		assertTrue(response.get("id").compareTo(testUser.getId()) == 0);
	}
}
