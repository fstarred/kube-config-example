package org.acme;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    Logger logger;

    @ConfigProperty(name = "mycmusername.msg")
    String mycmuser;

    @ConfigProperty(name = "mycmpassword.msg")
    String mycmpwd;

    @ConfigProperty(name = "myscusername.msg")
    String myscuser;

    @ConfigProperty(name = "myscpassword.msg")
    String myscpwd;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DataInfo hello() {
        logger.info("configmap user/password: {}/{}", mycmuser, mycmpwd);
        logger.info("secret user/password: {}/{}", myscuser, myscpwd);
        return DataInfo.of(mycmuser, mycmpwd, myscuser, myscpwd);
    }
}