/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.rupeeright.tell_a_joke.jokeserver;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.rupeeright.Joker;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "jokeserver.tell_a_joke.rupeeright.com",
                ownerName = "jokeserver.tell_a_joke.rupeeright.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(/* @Named("name") String name */) {
        MyBean response = new MyBean();
        //response.setData("Hi, " + name);
        Joker myJoker = new Joker();
        response.setData( myJoker.getJoke());
        return response;
    }

}
