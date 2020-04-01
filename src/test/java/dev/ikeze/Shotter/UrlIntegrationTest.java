package dev.ikeze.Shotter;

import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.repos.OwnerRepository;
import dev.ikeze.Shotter.repos.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;
import java.util.UUID;

import static dev.ikeze.Shotter.TestHelper.asJsonString;
import static dev.ikeze.Shotter.TestHelper.generateToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@SpringBootTest
public class UrlIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private OwnerRepository ownerRepository;


    @Test
    void getAllUrlsSuccessfully() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTester2", "urltest2@test.com", bCryptPasswordEncoder.encode("password")));
        urlRepository.save(new Url("testUrl3", "https://ikeze.dev/projects", testOwner));
        mvc.perform(MockMvcRequestBuilders
                .get("/urls/")
                .header("Authorization", "Bearer "+ generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").isNotEmpty());
    }

    @Test
    void getAllUrlByIdSuccessfully() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTester4", "urltest4@test.com", bCryptPasswordEncoder.encode("password")));
        var url = urlRepository.save(new Url("testUrl4", "https://ikeze.dev/projects", testOwner));
        mvc.perform(MockMvcRequestBuilders
                .get("/urls/"+ url.getId())
                .header("Authorization", "Bearer " + generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.redirect").value(url.getRedirect()));
    }
    @Test
    void getAllUrlByIdFailsWithUnknownId() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTester5", "urltest5@test.com", bCryptPasswordEncoder.encode("password")));
        mvc.perform(MockMvcRequestBuilders
                .get("/urls/"+ UUID.randomUUID())
                .header("Authorization", "Bearer " + generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllOwnerUrlsSuccessfully() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTest", "urltest@test.com", bCryptPasswordEncoder.encode("password")));
        urlRepository.save(new Url("testLink", "https://ikeze.dev/projects", testOwner));
        mvc.perform(MockMvcRequestBuilders
                .get("/urls/" + testOwner.getOwnerid() + "/owner")
                .header("Authorization", "Bearer " + generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].directory").value("testLink"));
    }

    @Test
    void getAllOwnerUrlsReturnsEmptyForUnknownOwners() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTester", "urltester@test.com", bCryptPasswordEncoder.encode("password")));
        urlRepository.save(new Url("testLink1", "https://ikeze.dev/projects", testOwner));
        mvc.perform(MockMvcRequestBuilders
                .get("/urls/" + UUID.randomUUID() + "/owner")
                .header("Authorization", "Bearer " + generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    void getAllOwnerUrlsFailsWithoutToken() throws Exception {
       mvc.perform(MockMvcRequestBuilders
               .get("/urls/" + UUID.randomUUID() + "/owner")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteUrlSuccessfully() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTester6", "urltester6@test.com", bCryptPasswordEncoder.encode("password")));
        var testUrl = urlRepository.save(new Url("testLink6", "https://ikeze.dev/projects", testOwner));
        mvc.perform(MockMvcRequestBuilders
                .delete("/urls/" + testUrl.getId())
                .header("Authorization", "Bearer "+ generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUrlFailsWithUnknownId() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTesterDel", "urltesterDel@test.com", bCryptPasswordEncoder.encode("password")));
        var randId = UUID.randomUUID();
        var exception = Objects.requireNonNull(mvc.perform(MockMvcRequestBuilders
                .delete("/urls/" + randId)
                .header("Authorization", "Bearer " + generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn().getResolvedException()).getMessage();
        assertEquals(exception, "Url not found: " + randId.toString());
    }

    @Test
    void checkIfUrlExists() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTester", "urltestEx@test.com", bCryptPasswordEncoder.encode("password")));
        var testUrl = urlRepository.save(new Url("testLinkEx", "https://ikeze.dev/projects", testOwner));
        mvc.perform(MockMvcRequestBuilders
                .get("/urls/check/" + testUrl.getDirectory())
                .header("Authorization", "Bearer " + generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(true));
    }

    @Test
    void checkIfUrlExistFalsyOnUnknownId() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTester", "urltestEx1@test.com", bCryptPasswordEncoder.encode("password")));
        mvc.perform(MockMvcRequestBuilders
                .get("/urls/check/unregisteredDirectory")
                .header("Authorization", "Bearer " + generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(false));
    }

    @Test
    void createUrlSuccessfully() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTester", "urltestCreate@test.com", bCryptPasswordEncoder.encode("password")));
        var url = new Url("testCreate", "https://ikeze.dev/blog", testOwner);
        mvc.perform(MockMvcRequestBuilders
                .post("/urls")
                .header("Authorization", "Bearer " + generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(url)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.directory").value(url.getDirectory()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void createUrlFailsIfUrlIsDuplicate() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTester11", "urltestCreate11@test.com", bCryptPasswordEncoder.encode("password")));
        var url = new Url("testCreate11", "https://ikeze.dev/projects", testOwner);
        urlRepository.save(new Url("testCreate11", "https://ikeze.dev/projects", testOwner));
        var exception = Objects.requireNonNull(mvc.perform(MockMvcRequestBuilders
                .post("/urls")
                .header("Authorization", "Bearer " + generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(url)))
                .andExpect(status().isConflict())
                .andReturn().getResolvedException()).getMessage();
        assertEquals(exception, "This Url already exists: " + url.getDirectory());
    }

    @Test
    void createUrlFailsIfFieldsAreBlank() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTester", "urltestCreate1@test.com", bCryptPasswordEncoder.encode("password")));
        var url = new Url("", "", testOwner);
        var exception = Objects.requireNonNull(mvc.perform(MockMvcRequestBuilders
                .post("/urls")
                .header("Authorization", "Bearer " + generateToken(testOwner))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(url)))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException()).getMessage();
        assertEquals(exception, "Please provide directory, redirect and/or owner fields");
    }

    @Test
    void updateUrlSuccessfully() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var testOwner = ownerRepository.save(new Owner("urlTester", "urltestUpdate@test.com", bCryptPasswordEncoder.encode("password")));
        var url = new Url("update", "https://ikeze.dev/projects", testOwner);
        urlRepository.save(url);
        mvc.perform(MockMvcRequestBuilders
                .put("/urls/" + url.getId())
                .header("Authorization", "Bearer "+ generateToken(testOwner))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Url("updatedUrl", "https://ikeze.dev/contact", testOwner))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.directory").value("updatedUrl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.redirect").value("https://ikeze.dev/contact"));
    }


}
