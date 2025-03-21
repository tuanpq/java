package io.github.tuanpq.springbootjira.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/jira/api")
public class JiraController {
    
    private final static String BASE_URL = "http://localhost:2990/jira";
    private final static String USER_NAME = "admin";
    private final static String PASSWORD = "admin";

    @GetMapping("/home")
    public String home() {
        URL url = null;
        HttpURLConnection urlConnection = null;
        String input = "";
        OutputStream os = null;
        BufferedReader br = null;
        String output = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            url = new URI(BASE_URL).toURL();
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            input = makeBasicAuthenticationParameter();
            os = urlConnection.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            Map<String, List<String>> map = urlConnection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println("Key : " + entry.getKey() + ", Value : " + entry.getValue());
            }

            if (urlConnection.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
                while ((output = br.readLine()) != null) {
                    stringBuilder.append(output);
                }
                urlConnection.disconnect();
            }

            System.out.println("HTTP " + urlConnection.getResponseCode());
        } catch (Exception ex) {
            System.out.println("Error in loginToJira: " + ex.getMessage());
        }

        return stringBuilder.toString();
    }

    @GetMapping("/myself")
    public String myself() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/myself", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/permissions")
    public String permissions() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/permissions", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/applicationRole")
    public String applicationRole() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/applicationrole", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/advancedSettings")
    public String advancedSettings() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/application-properties/advanced-settings", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/configuration")
    public String configuration() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/configuration", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/dashboard", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/field")
    public String field() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/field", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/summary")
    public String summary() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/index/summary", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/issueType")
    public String issueType() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/issuetype", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/priority")
    public String priority() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/priority", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/project")
    public String project() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/project", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/projectCategory")
    public String projectCategory() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/projectCategory", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/projectType")
    public String projectType() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/project/type", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/resolution")
    public String resolution() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/resolution", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/role")
    public String role() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/role", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/screens")
    public String screens() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/screens", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/serverInfo")
    public String serverInfo() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/serverInfo", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/status")
    public String status() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/status", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/statusCategory")
    public String statusCategory() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/statuscategory", HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @GetMapping("/user")
    public String user(@RequestParam String username) {
        RestTemplate restTemplate = new RestTemplate();
        if (null == username) username = "";
        HttpEntity<String> request = new HttpEntity<>(makeBasicAuthenticationHeader());
        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/rest/api/2/user?username=" + username, HttpMethod.GET, request, String.class);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                return response.getBody();
            } else {
                return "Can not find " + username + "'s information";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    @GetMapping("/login")
    public String login() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> postRequest = new HttpEntity<>(makeBasicAuthenticationParameter(), makeBasicAuthenticationHeader());
        ResponseEntity<String> postResponse = restTemplate.exchange(BASE_URL + "/rest/auth/1/session", HttpMethod.POST, postRequest, String.class);
        
        return postResponse.getBody();
    }

    @GetMapping("/session")
    public String session() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> getRequest = new HttpEntity<>(makeBasicAuthenticationHeader());
        ResponseEntity<String> getResponse = restTemplate.exchange(BASE_URL + "/rest/auth/1/session", HttpMethod.GET, getRequest, String.class);

        return getResponse.getBody();
    }

    @GetMapping("/logout")
    public String logout() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> deleteRequest = new HttpEntity<>(makeBasicAuthenticationParameter(), makeBasicAuthenticationHeader());
        ResponseEntity<String> deleteResponse = restTemplate.exchange(BASE_URL + "/rest/auth/1/session", HttpMethod.DELETE, deleteRequest, String.class);
        
        return deleteResponse.getBody();
    }

    private HttpHeaders makeBasicAuthenticationHeader() {
        String auth = USER_NAME + ":" + PASSWORD;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        System.out.println(encodedAuth);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private String makeBasicAuthenticationParameter() {
        return "{\"username\": \"" + USER_NAME + "\", \"password\": \"" + PASSWORD + "\"}";
    }

}
