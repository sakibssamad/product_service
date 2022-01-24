package com.scube.product_service.controller;

import com.scube.product_service.payload.FeedbackDto;
import com.scube.product_service.payload.ProductDto;
import com.scube.product_service.service.FeedbackService;
import com.scube.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@Slf4j
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/products/{productId}/feedbacks")
    public FeedbackDto createFeedback(@PathVariable("productId") long productId,
                                     @RequestBody FeedbackDto feedbackDto) {
        log.info("Inside the createFeedback Controller");

        return feedbackService.createFeedback(productId, feedbackDto);
    }

    @GetMapping("/products/{productId}/feedbacks")
    public List<FeedbackDto> getAllFeedbackByProductId(@PathVariable("productId") long productId) {

        log.info("Inside the getAllFeedbackByProductId Controller");

        return feedbackService.getAllFeedbackByProductId(productId);

    }

    @PutMapping("/products/{productId}/feedbacks/{feedbackId}")
    public ResponseEntity<FeedbackDto> updateFeedback(@PathVariable("productId") long productId,
                                    @PathVariable("feedbackId") long feedbackId,
                                    @RequestBody FeedbackDto feedbackDto) {

        log.info("Inside the updateFeedback Controller");

       FeedbackDto feedbackDtoResponse = feedbackService.updateFeedbackById(productId, feedbackId, feedbackDto);
        return new ResponseEntity<>(feedbackDtoResponse, HttpStatus.OK);

    }

    @Operation(summary = "This is to fetch unique feedback stored in Db for a unique product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched unique feedback form Db for a unique product",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Feedback not found with id : feedbackId",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Feedback is not from this product",
                    content = @Content)
    })

    @GetMapping("/products/{productId}/feedbacks/{feedbackId}")
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable("productId") long productId,
                                                      @PathVariable("feedbackId") long feedbackId) {

        log.info("Inside the updateFeedback Controller");

        FeedbackDto feedbackDtoResponse = feedbackService.getFeedbackById(productId, feedbackId);
        return new ResponseEntity<>(feedbackDtoResponse, HttpStatus.OK);

    }

    @DeleteMapping("/products/{productId}/feedbacks/{feedbackId}")
    public ResponseEntity<String> deleteFeedbackById(@PathVariable("productId") long productId,
                                      @PathVariable("feedbackId") long feedbackId) {

        log.info("Inside the deleteFeedbackById Controller");

        feedbackService.deleteFeedbackById(productId, feedbackId);

        return new ResponseEntity<>("Feedback Deleted Successfully", HttpStatus.OK);
    }
}
