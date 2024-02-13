package com.practice.date.demo.service;

import com.practice.date.demo.entity.Test;
import com.practice.date.demo.interfaces.TestService;
import com.practice.date.demo.payload.request.TestRequest;
import com.practice.date.demo.payload.response.TestResponse;
import com.practice.date.demo.repo.TestRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    @Override
    public TestResponse createTest(TestRequest request) {
        Test test = modelMapper.map(request, Test.class);
        test = testRepository.save(test);

        return modelMapper.map(test, TestResponse.class);
    }

    @Override
    public TestResponse getTestResponse(Test test) {
        return modelMapper.map(test, TestResponse.class);
    }

    @Override
    public List<TestResponse> getAllTest() {
        return testRepository.findAll()
                .stream()
                .map(test -> {
                    return modelMapper.map(test, TestResponse.class);
                }).collect(Collectors.toList());
    }
}