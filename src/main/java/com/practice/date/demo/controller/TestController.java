package com.practice.date.demo.controller;

import com.practice.date.demo.interfaces.TestService;
import com.practice.date.demo.payload.request.TestRequest;
import com.practice.date.demo.payload.response.TestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-timezone")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/")
    public ResponseEntity<?> testClientTimezone() {
        List<TestResponse> allTestResponse = testService.getAllTest();

        return ResponseEntity.ok(allTestResponse);
    }

    @PostMapping("/")
    public ResponseEntity<?> testClientTimezone(@RequestBody TestRequest request) {
        TestResponse test = testService.createTest(request);

        return ResponseEntity.ok(test);
    }
}