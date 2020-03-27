package dev.ikeze.Shotter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.repos.OwnerRepository;
import dev.ikeze.Shotter.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@SpringBootTest
class OwnerIntegrationTest {
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public OwnerIntegrationTest() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder();
	}

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private MockMvc mvc;


	@Test
	void createOwnerAndReturnItSuccessfully() throws Exception {
		var owner = new Owner("matt", "matt@email.com", bCryptPasswordEncoder.encode("password"));
		mvc.perform(MockMvcRequestBuilders
				.post("/owners/create")
				.content(asJsonString(owner))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value( "matt"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.ownerid").exists());

	}

	@Test
	void errorsOnDuplicateOwner() throws Exception {
		var owner = new Owner("mattie", "mattie@email.com", bCryptPasswordEncoder.encode("password"));
		ownerRepository.save(owner);
		var exception = Objects.requireNonNull(mvc.perform(MockMvcRequestBuilders
				.post("/owners/create")
				.content(asJsonString(owner))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andReturn().getResolvedException()).getMessage();
		assertEquals(exception, "Owner is a duplicate: " + owner.getEmail());
	}

	@Test
	void failsOnMissingRequiredOwnerParams() throws Exception {
		var owner = new Owner("mattie", "", bCryptPasswordEncoder.encode("password"));
		ownerRepository.save(owner);
		var exception = Objects.requireNonNull(mvc.perform(MockMvcRequestBuilders
				.post("/owners/create")
				.content(asJsonString(owner))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn().getResolvedException()).getMessage();
		assertEquals(exception, "Please provide email field(s)");
	}

	@Test
	void findOwnerByIdSuccessfully() throws Exception {
		var owner = new Owner("jose", "jose@joe.com", bCryptPasswordEncoder.encode("password"));
		var ownerInDB = ownerRepository.save(owner);
		mvc.perform(MockMvcRequestBuilders
				.get("/owners/" + ownerInDB.getOwnerid())
				.header("Authorization", "Bearer "+ generateToken(ownerInDB))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value( "jose"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.ownerid").value(ownerInDB.getOwnerid().toString()));
	}
	@Test
	void findOwnerByIdFailsWithUnregisteredId() throws Exception {
		var owner = new Owner("ike", "ike@himself.com", bCryptPasswordEncoder.encode("password"));
		var ownerInDB = ownerRepository.save(owner);
		var randomId = UUID.randomUUID();
		var exception = Objects.requireNonNull(mvc.perform(MockMvcRequestBuilders
				.get("/owners/" + randomId)
				.header("Authorization", "Bearer "+ generateToken(ownerInDB))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn().getResolvedException()).getMessage();
		assertEquals(exception, "Owner not found: " + randomId.toString());
	}

	@Test
	void findOwnerByIdFailsWithoutToken() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/owners/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void checkIfOwnerExists() throws Exception {
		var owner = new Owner("ike2", "ike2@himself.com", bCryptPasswordEncoder.encode("password"));
		ownerRepository.save(owner);
		mvc.perform(MockMvcRequestBuilders
				.get("/owners/check/" + owner.getEmail())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.state").value(true));
	}

	@Test
	void checkIfOwnerExistsFalse() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/owners/check/any@email.com")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.state").value(false));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public  static String generateToken(Owner owner) {
		JwtUtil jwtUtil = new JwtUtil();
		final UserDetails userDetails = new User(owner.getEmail(), owner.getPassword(), new ArrayList<>());
		Map<String, Object> claims = new HashMap<>();
		claims.put("Id", owner.getOwnerid());
		return jwtUtil.generateToken(userDetails, claims);
	}

}
