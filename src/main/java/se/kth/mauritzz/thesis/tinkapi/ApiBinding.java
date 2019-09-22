package se.kth.mauritzz.thesis.tinkapi;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public abstract class ApiBinding {

    protected static final String API_URL = "https://main.staging.oxford.tink.se/api/v1/";
    protected RestTemplate restTemplate;

    public ApiBinding(String accessToken) {
        this.restTemplate = new RestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());

        if (accessToken != null) {
            restTemplate.getInterceptors().add(getBearerTokenInterceptor(accessToken));
        }

        restTemplate.getInterceptors().add(setJsonIfBodyWithoutType());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        return (request, bytes, execution) -> {
            request.getHeaders().setAccept(List.of(MediaType.APPLICATION_JSON));
            request.getHeaders().setBearerAuth(accessToken);
            return execution.execute(request, bytes);
        };
    }

    private ClientHttpRequestInterceptor setJsonIfBodyWithoutType() {
        return (request, body, execution) -> {
            if (request.getMethod() == HttpMethod.POST && request.getHeaders().getContentType() == null) {
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            }

            return execution.execute(request, body);
        };
    }

    private ClientHttpRequestInterceptor getNoTokenInterceptor() {
        return (request, bytes, execution) -> {
            throw new IllegalStateException(
                    "Can't access the API without an access token");
        };
    }

    protected  <T> T postForm(String url, MultiValueMap<String, String> requestBody, Class<T> responseClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForObject(url, request, responseClass);
    }

}