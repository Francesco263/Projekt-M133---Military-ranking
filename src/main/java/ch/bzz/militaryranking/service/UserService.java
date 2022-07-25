package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
import ch.bzz.militaryranking.model.User;
import ch.bzz.militaryranking.util.AESEncrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * services for authentication and authorization of users
 */

@Path("user")
public class UserService {

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    )
    {
        int httpStatus = 404;

        User user = DataHandler.findUser(username, password);
        if(user.getRole().equals("guest")){
            httpStatus = 404;
        }
        else{
            httpStatus = 200;
        }

        NewCookie cookie = new NewCookie(
                "userRole",
                AESEncrypt.encrypt(user.getRole()),
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );

        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;

    }

    /**
     * logout the current user
     * @return
     */
    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout(){
        int httpStatus = 404;
        NewCookie cookie = new NewCookie(
                "userRole",
                AESEncrypt.encrypt("guest"),
                "/",
                "",
                "Login-Cookie",
                1 ,
                false
        );
        if (cookie.getMaxAge() == 1){
           httpStatus = 200;
        }
        return Response
                .status(httpStatus)
                .cookie(cookie)
                .entity("")
                .build();
    }
}
