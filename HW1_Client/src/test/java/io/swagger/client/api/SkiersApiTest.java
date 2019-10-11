/*
 * Ski Data API for NEU Seattle distributed systems course
 * An API for an emulation of skier managment system for RFID tagged lift tickets. Basis for CS6650 Assignments for 2019
 *
 * OpenAPI spec version: 1.0.1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.api;

import io.swagger.client.ApiException;
import io.swagger.client.model.LiftRide;
import io.swagger.client.model.ResponseMsg;
import io.swagger.client.model.SkierVertical;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for SkiersApi
 */
@Ignore
public class SkiersApiTest {

    private final SkiersApi api = new SkiersApi();

    /**
     * write a new lift ride for the skier
     *
     * get the total vertical for the skier for the specified ski day
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getSkierDayVerticalTest() throws ApiException {
        Integer resortID = null;
        String seasonID = null;
        String dayID = null;
        Integer skierID = null;
        Integer response = api.getSkierDayVertical(resortID, seasonID, dayID, skierID);

        // TODO: test validations
    }
    /**
     * get the total vertical for the skier for specified seasons at the specified resort
     *
     * get the total vertical for the skier the specified resort. If no season is specified, return all seasons
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getSkierResortTotalsTest() throws ApiException {
        Integer skierID = null;
        List<String> resort = null;
        List<String> season = null;
        List<SkierVertical> response = api.getSkierResortTotals(skierID, resort, season);

        // TODO: test validations
    }
    /**
     * write a new lift ride for the skier
     *
     * Stores new lift ride details in the data store
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void writeNewLiftRideTest() throws ApiException {
        LiftRide body = null;
        Integer resortID = null;
        String seasonID = null;
        String dayID = null;
        Integer skierID = null;
        api.writeNewLiftRide(body, resortID, seasonID, dayID, skierID);

        // TODO: test validations
    }
}
