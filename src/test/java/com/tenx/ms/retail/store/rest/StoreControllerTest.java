package com.tenx.ms.retail.store.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.TestConstants;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class StoreControllerTest extends AbstractIntegrationTest {

    private static final String REQUEST_URI = "%s/%s/stores/";
    private final RestTemplate template = new TestRestTemplate();

    @Autowired
    private ObjectMapper mapper;

    @Value("classpath:json/store/createStoreRequest.json")
    private File createStoreRequest;

    @Value("classpath:json/store/invalidInputCreateStoreRequest.json")
    private File invalidInputCreateStoreRequest;

    @Test
    public void testCreateStore() {

        try {
            ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(REQUEST_URI, basePath(), TestConstants.API_VERSION_1),
                FileUtils.readFileToString(createStoreRequest),
                HttpMethod.POST
            );

            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());
            String received = response.getBody();

            ResourceCreated rc = mapper.readValue(received, ResourceCreated.class);
            Integer storeId = (Integer) rc.getId();
            assertThat("Incorrect store id", storeId > 0, is(true));
        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testGetAllStores() {

        try {
            ResponseEntity<String> response = getJSONResponse(
                    template,
                    String.format(REQUEST_URI, basePath(), TestConstants.API_VERSION_1),
                    null,
                    HttpMethod.GET
            );

            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());
            String received = response.getBody();

            Paginated<Store> storePaginated = mapper.readValue(received, new TypeReference<Paginated<Store>>(){});
            assertThat("Incorrect store count", storePaginated.getTotalCount(), greaterThanOrEqualTo(3L));

            List<Store> stores = storePaginated.getContent()
                    .stream()
                    .collect(Collectors.toList());

            assertThat("Incorrect store id", stores.get(0).getStoreId(), is(1L));
            assertThat("Incorrect store name", stores.get(0).getName(), is("Store 1"));
            assertThat("Incorrect store id", stores.get(1).getStoreId(), is(2L));
            assertThat("Incorrect store name", stores.get(1).getName(), is("Store 2"));
            assertThat("Incorrect store id", stores.get(2).getStoreId(), is(3L));
            assertThat("Incorrect store name", stores.get(2).getName(), is("Store 3"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetStoreById() {

        try {
            ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(REQUEST_URI, basePath(), TestConstants.API_VERSION_1) + "1",
                null,
                HttpMethod.GET
            );

            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());
            String received = response.getBody();

            Store store = mapper.readValue(received, Store.class);
            assertThat("Null store", store, is(notNullValue()));
            assertThat("Incorrect store name", store.getName(), is("Store 1"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetStoreByName() {

        try {
            ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(REQUEST_URI, basePath(), TestConstants.API_VERSION_1) + "/name/Store 1",
                null,
                HttpMethod.GET
            );

            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());
            String received = response.getBody();

            Store store = mapper.readValue(received, Store.class);
            assertThat("Null store", store, is(notNullValue()));
            assertThat("Incorrect store name", store.getName(), is("Store 1"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInvalidCreateStoreInput() {

        try {
            ResponseEntity<String> response = getJSONResponse(
                template,
                String.format(REQUEST_URI, basePath(), TestConstants.API_VERSION_1),
                FileUtils.readFileToString(invalidInputCreateStoreRequest),
                HttpMethod.POST
            );

            assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetInvalidStoreId(){
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath(), TestConstants.API_VERSION_1) + "/" + "123/",
            null,
            HttpMethod.GET
        );

        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetInvalidStoreName(){
        ResponseEntity<String> response = getJSONResponse(
            template,
            String.format(REQUEST_URI, basePath(), TestConstants.API_VERSION_1) + "/name" + "blah",
            null,
            HttpMethod.GET
        );

        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
