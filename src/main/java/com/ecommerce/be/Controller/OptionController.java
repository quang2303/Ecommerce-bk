package com.ecommerce.be.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.be.Entity.Option;
import com.ecommerce.be.Service.OptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


@RestController
@RequestMapping("api/v1/options")
public class OptionController {
    
    private OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }



    @Operation(summary = "Get root options which has parentId = 0", tags = { "Option" }, responses = {
        @ApiResponse(responseCode = "200", description = "Return a list of options", content = {
            @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Option.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    @GetMapping() 
    public ResponseEntity<List<Option>> getRootOptions() {
        return ResponseEntity.ok(optionService.getRootOptions());
    }





    @Operation(summary = "Get child options of a option", tags = { "Option" }, responses = {
        @ApiResponse(responseCode = "200", description = "Return a list of options", content = {
            @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Option.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    @GetMapping("/{parentOptionName}/children")
    public ResponseEntity<List<Option>> getChildOptions(@PathVariable String parentOptionName) {
        return ResponseEntity.ok(optionService.getChildOptions(parentOptionName));
    }
}
