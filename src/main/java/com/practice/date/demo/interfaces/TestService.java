package com.practice.date.demo.interfaces;

import com.practice.date.demo.entity.Test;
import com.practice.date.demo.payload.request.TestRequest;
import com.practice.date.demo.payload.response.TestResponse;

import java.util.List;

public interface TestService {

    TestResponse createTest(TestRequest test);

    TestResponse getTestResponse(Test test);

    List<TestResponse> getAllTest();
}
