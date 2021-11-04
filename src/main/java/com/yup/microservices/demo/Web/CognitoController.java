package com.yup.microservices.demo.Web;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderAsyncClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import com.yup.microservices.demo.Dto.PasswordRequest;
import com.yup.microservices.demo.Dto.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("v1/cognito")
public class CognitoController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    //AWS credentials
    private String ACCESS_KEY = "";
    private String SECRET_KEY = "";
    private String userPoolId = "us-east-1_Kpp1sw66d";

    @GetMapping("/create-user")
    //@RequestMapping(method = RequestMethod.GET, value = "/api/javainuse")
    public String createUser(){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

        AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderAsyncClientBuilder
                .standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("us-east-1").build();

        //Test user data
        String email = "queibassiquacre-9577@yopmail.com";
        String username = "queibassiquacre";
        String password = "Test123$";

        try {
            AttributeType emailAttr = new AttributeType().withName("email").withValue(email);
            AttributeType emailVerifiedAttr =
                    new AttributeType().withName("email_verified").withValue("false");

            AdminCreateUserRequest userRequest =
                    new AdminCreateUserRequest().withUserPoolId(userPoolId)
                            .withUsername(username)
                            .withTemporaryPassword(password)
                            .withUserAttributes(emailAttr, emailVerifiedAttr)
                            .withMessageAction(MessageActionType.SUPPRESS);

            AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(userRequest);

            System.out.println("User " + createUserResult.getUser().getUsername()
                    + " is created. Status: " + createUserResult.getUser().getUserStatus());

            // Make the password permanent and not temporary
            AdminSetUserPasswordRequest adminSetUserPasswordRequest =
                    new AdminSetUserPasswordRequest().withUsername(username)
                            .withUserPoolId(userPoolId).withPassword(password).withPermanent(true);
            cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);

            cognitoClient.shutdown();
        } catch (AWSCognitoIdentityProviderException e) {
            System.out.println(e.getErrorMessage());
        } catch (Exception e) {
            System.out.println(e);
        }


        return "creado";
    }

    @GetMapping("/get-user-info")
    public UserResponse getUserInfo()
    {
        //Test user data
        String email = "queibassiquacre-9577@yopmail.com";
        String username = "queibassiquacre";
        String password = "Test123$";

        AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();
        AdminGetUserRequest userRequest = new AdminGetUserRequest()
                .withUsername(username)
                .withUserPoolId(userPoolId);


        AdminGetUserResult userResult = cognitoClient.adminGetUser(userRequest);

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(userResult.getUsername());
        userResponse.setUserStatus(userResult.getUserStatus());
        userResponse.setUserCreateDate(userResult.getUserCreateDate());
        userResponse.setLastModifiedDate(userResult.getUserLastModifiedDate());

        List<AttributeType> userAttributes = userResult.getUserAttributes();
        for(AttributeType attribute: userAttributes) {
            if(attribute.getName().equals("custom:companyName")) {
                userResponse.setCompanyName(attribute.getValue());
            }else if(attribute.getName().equals("custom:companyPosition")) {
                userResponse.setCompanyPosition(attribute.getValue());
            }else if(attribute.getName().equals("email")) {
                userResponse.setEmail(attribute.getValue());
            }
        }
        logger.info("userResponse", userResponse);
        cognitoClient.shutdown();
        return userResponse;
    }

    @GetMapping("/add-user-group")
    public String addUserToGroup()
    {
        //Test user data
        String email = "queibassiquacre-9577@yopmail.com";
        String username = "queibassiquacre";
        String password = "Test123$";
        String groupname = "admins";


        AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();
        AdminAddUserToGroupRequest addUserToGroupRequest = new AdminAddUserToGroupRequest()
                .withGroupName(groupname)
                .withUserPoolId(userPoolId)
                .withUsername(username);

        cognitoClient.adminAddUserToGroup(addUserToGroupRequest);

        cognitoClient.shutdown();

        return "";
    }

    @GetMapping("/change-password")
    public String changePassword() {
        PasswordRequest passwordRequest = new PasswordRequest();
        AWSCognitoIdentityProvider cognitoClient= getAmazonCognitoIdentityClient();
        ChangePasswordRequest changePasswordRequest= new ChangePasswordRequest()
                .withAccessToken(passwordRequest.getAccessToken())
                .withPreviousPassword(passwordRequest.getOldPassword())
                .withProposedPassword(passwordRequest.getPassword());

        var changePassWordReset = cognitoClient.changePassword(changePasswordRequest);
        cognitoClient.shutdown();
        return "Se cambio la contrasena correctamente";
    }

    @GetMapping("/enable-user")
    public String EnableUser()
    {
        //Test user data
        String email = "queibassiquacre-9577@yopmail.com";
        String username = "queibassiquacre";
        String password = "Test123$";
        String groupname = "admins";

        AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();
        AdminEnableUserRequest enableUserRequest = new AdminEnableUserRequest()
                .withUserPoolId(userPoolId)
                .withUsername(username);
        AdminEnableUserResult adminEnableUserResult = cognitoClient.adminEnableUser(enableUserRequest);
        logger.info("AdminEnableUser", adminEnableUserResult);
        return "";
    }

    @GetMapping("/disable-user")
    public String DisableUser()
    {
        //Test user data
        String email = "queibassiquacre-9577@yopmail.com";
        String username = "queibassiquacre";
        String password = "Test123$";
        String groupname = "admins";

        AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();
        AdminDisableUserRequest disableUserRequest = new AdminDisableUserRequest()
                .withUserPoolId(userPoolId)
                .withUsername(username);
        AdminDisableUserResult adminDisableUserResult = cognitoClient.adminDisableUser(disableUserRequest);
        logger.info("AdminDisableUser", adminDisableUserResult);
        return "";
    }

    private AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderAsyncClientBuilder
                .standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("us-east-1").build();
        return cognitoClient;
    }


}
