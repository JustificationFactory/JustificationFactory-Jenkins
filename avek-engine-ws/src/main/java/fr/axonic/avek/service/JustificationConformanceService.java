package fr.axonic.avek.service;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface JustificationConformanceService {

    @GET
    @Path("/system/{justification_system_name}/completed")
    @Consumes(MediaType.APPLICATION_JSON)
    Response checkJustificationSystemCompleteness(@PathParam("justification_system_name") String justificationDiagramName);

    @GET
    @Path("/operation/comparison/{justification_diagram_1_name}/{justification_diagram_2_name}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response compareJustificationDiagrams(@PathParam("{justification_diagram_1_name}") String justificationDiagram1Name,@PathParam("{justification_diagram_2_name}") String justificationDiagram2Name);

    @GET
    @Path("/operation/derivation/{justification_diagram_1_name}/{justification_diagram_2_name}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response deriveJustificationDiagrams(@PathParam("{justification_diagram_1_name}") String justificationDiagram1Name,@PathParam("{justification_diagram_2_name}") String justificationDiagram2Name);

    @GET
    @Path("/operation/achievement/{justification_diagram_name}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response achieveJustificationDiagram(@PathParam("{justification_diagram_name}") String justificationDiagramName);

    @GET
    @Path("/operation/alignment/{justification_diagram_1_name}/{justification_diagram_2_name}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response alignJustificationDiagrams(@PathParam("{justification_diagram_1_name}") String justificationDiagram1Name,@PathParam("{justification_diagram_2_name}") String justificationDiagram2Name);


}
