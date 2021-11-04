package com.yup.microservices.demo.Web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.unboundid.scim2.client.ScimService;
import com.unboundid.scim2.common.exceptions.ScimException;
import com.unboundid.scim2.common.types.UserResource;
import com.unboundid.scim2.common.types.Name;
import com.unboundid.scim2.common.types.Email;
import com.unboundid.scim2.common.GenericScimResource;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.filters.Filter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;

import java.util.Collections;

@RestController()
@RequestMapping("v1/scim")
public class SCIMController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/create-user")
    public String createUser() throws ScimException {
        logger.info("START  [POST] /v1/scim/create-user");

        // Create a ScimService
        //https://scim.us-east-1.amazonaws.com/{tenant_id}/scim/v2/Users
        Client client = ClientBuilder.newClient().register(OAuth2ClientSupport.feature("..bearerToken.."));;
        WebTarget target = client.target("https://example.com/scim/v2");
        ScimService scimService = new ScimService(target);

        // Create a user
        UserResource user = new UserResource();
        user.setUserName("babs");
        user.setPassword("secret");
        Name name = new Name()
                .setGivenName("Barbara")
                .setFamilyName("Jensen");
        user.setName(name);
        Email email = new Email()
                .setType("home")
                .setPrimary(true)
                .setValue("babs@example.com");
        user.setEmails(Collections.singletonList(email));
        user = scimService.create("Users", user);


        return "info";
    }
}
