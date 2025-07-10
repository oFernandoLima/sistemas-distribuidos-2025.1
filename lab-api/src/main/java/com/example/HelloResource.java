package com.example;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/hello")
public class HelloResource {
 @Inject
 private HelloService helloService;
 @GET
 @Path("/{name}")
 @Produces(MediaType.TEXT_PLAIN)
 public String sayHello(@PathParam("name") String name) {
 return helloService.sayHello(name);
 }
}
