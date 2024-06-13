package io.quarkus.workshop.superheroes.villain;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;


@ApplicationPath("/")
@OpenAPIDefinition(
    info = @Info(
        title = "Villain API",
        description = "This API allows CRUD operations on a villain",
        version = "1.0",
        contact = @Contact(name = "Villain", url = "https://github.com/y00ss")
    ),
    servers = { @Server(url = "http://localhost:8084") },
    externalDocs = @ExternalDocumentation(url = "https://github.com/y00ss", description = "Enjoying quarkus")
)
public class VillainApplication  extends Application {
    // empty on propose
}
