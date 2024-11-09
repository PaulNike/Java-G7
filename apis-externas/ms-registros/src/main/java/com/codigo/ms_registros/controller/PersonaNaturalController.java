package com.codigo.ms_registros.controller;

import com.codigo.ms_registros.aggregates.response.ResponseReniec;
import com.codigo.ms_registros.entity.PersonaNaturalEntity;
import com.codigo.ms_registros.service.PersonaNaturalService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/personaNatural")
@Tag(
        name = "personaNatural",
        description = "Esta apui contiene los endPoints necesarios para la gestión de personas naturales" +
                "esto incluye desde crear hasta eliminar una persona",
        externalDocs = @ExternalDocumentation(
                description = "Documentación adicional del ms-registros",
                url = "https://github.com/PaulNike/Java-G7/tree/master/apis-externas/ms-registros"
        )
)
public class PersonaNaturalController {

    private final PersonaNaturalService personaNaturalService;
    public PersonaNaturalController(PersonaNaturalService personaNaturalService) {
        this.personaNaturalService = personaNaturalService;
    }

    @PostMapping
    @Operation(summary = "Guerdar una nueva Persona natural",
                description = "Crea una nueva Persona natural y la almacena en la BD de la aplicación"
            /*parameters = @Parameter(
                name = "dni",
                    required = true
            )*/
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Operación Exitosa", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonaNaturalEntity.class),
                            examples = {
                            @ExampleObject(
                                    name = "Ejemplo de una persona natural",
                                    value = "{\n" +
                                            "    \"id\": 13,\n" +
                                            "    \"nombres\": \"AUDUBERTO\",\n" +
                                            "    \"apellidoPaterno\": \"ZELADA\",\n" +
                                            "    \"apellidoMaterno\": \"ALIAGA\",\n" +
                                            "    \"tipoDocumento\": \"1\",\n" +
                                            "    \"numeroDocumento\": \"06256224\",\n" +
                                            "    \"digitoVerificador\": \"\",\n" +
                                            "    \"estado\": 1,\n" +
                                            "    \"userCreated\": \"PRODRIGUEZ\",\n" +
                                            "    \"dateCreated\": \"2024-11-07T01:48:11.899+00:00\"\n" +
                                            "}"
                            )
                            }),
                    @Content(mediaType = "application/xml",
                            schema = @Schema(implementation = PersonaNaturalEntity.class))
            }),
            @ApiResponse(responseCode = "500", description = "Error en el servidor",
            content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "503", description = "INTERNAL ERROR FATAL",
            content = @Content(schema = @Schema(hidden = true)))

    })
    public ResponseEntity<PersonaNaturalEntity> guardarPersona(
            @Parameter(description = "DNI de la Persona a guardar",
                    required = true,
                    example = "12345678"
            )
            @RequestParam("dni") String dni) throws IOException {
        PersonaNaturalEntity personaNatural = personaNaturalService.guardar(dni);
        return new ResponseEntity<>(personaNatural, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PersonaNaturalEntity> guardarPersona2(
            @RequestParam("dni") String dni,
            @RequestBody PersonaNaturalEntity personaNaturalRequest) throws IOException {
        PersonaNaturalEntity personaNatural = personaNaturalService.guardar(dni);
        return new ResponseEntity<>(personaNatural, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<PersonaNaturalEntity> delete(
            @RequestParam("dni") String dni) throws IOException {
        PersonaNaturalEntity personaNatural = personaNaturalService.guardar(dni);
        return new ResponseEntity<>(personaNatural, HttpStatus.CREATED);
    }

    @GetMapping("/reniec/{dni}")
    public ResponseEntity<ResponseReniec> getInfoReniec(
            @PathVariable String dni){
     return new ResponseEntity<>(personaNaturalService
             .getInfoReniec(dni),HttpStatus.OK);
    }

}
