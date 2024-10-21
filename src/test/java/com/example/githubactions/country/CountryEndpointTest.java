package com.example.githubactions.country;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Currency;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

@WebServiceServerTest
class CountryEndpointTest {

    private static final Map<String, String> NAMESPACE_MAPPING = createMapping();


    @Autowired
    private MockWebServiceClient client;

    @MockBean
    private CountryRepository countryRepository;

    @Test
    void it_should_find() throws IOException {


        Country country = new Country();
        country.setName("PH");
        country.setCapital("CEBU");
        country.setCurrency(Currency.GBP);
        country.setPopulation(1);

        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(country);

        when(countryRepository.findCountry(anyString())).thenReturn(country);

        StringSource request = new StringSource(
            "<gs:getCountryRequest xmlns:gs='http://spring.io/guides/gs-producing-web-service'>" +
                "<gs:name>PH</gs:name>" +
                "</gs:getCountryRequest>"
        );

        StringSource expectedResponse = new StringSource( "<gs:getCountryResponse xmlns:gs='http://spring.io/guides/gs-producing-web-service'>" +
            "<gs:country>" +
            "<gs:name>PH</gs:name>" +
            "<gs:population>1</gs:population>" +
            "<gs:capital>CEBU</gs:capital>" +
            "<gs:currency>GBP</gs:currency>" +
            "</gs:country>" +
            "</gs:getCountryResponse>");

        client.sendRequest(withPayload(request))
            .andExpect(noFault())
            .andExpect(validPayload(new ClassPathResource("countries.xsd")))
            .andExpect(payload(expectedResponse))
            .andExpect(xpath("/gs:getCountryResponse/gs:country/gs:name",NAMESPACE_MAPPING ).evaluatesTo("PH"));

        verify(countryRepository,times(1)).findCountry(anyString());

    }

    private static Map<String, String> createMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("gs", "http://spring.io/guides/gs-producing-web-service");
        return mapping;
    }

}