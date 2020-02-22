package com.team6.rideshare.network;

import com.team6.rideshare.data.Driver;
import com.team6.rideshare.data.Passenger;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@Rest(rootUrl = "http://127.0.0.1:8000",
    converters = {StringHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class})
public interface RideShareREST extends RestClientErrorHandling {

    @Get("/drivers/{name}")
    List<Passenger> getDriverRoute(@Path String name);

    @Get("/passengers/{name}")
    String getPassengerDriver(@Path String name);

    @Post("/new/driver")
    BooleanWrapper registerNewDriver(@Body Driver driver);

    @Post("/new/passenger")
    BooleanWrapper registerNewPassenger(@Body Passenger passenger);

}
