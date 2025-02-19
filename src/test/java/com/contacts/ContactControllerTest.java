package com.contacts;

import com.contacts.model.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAddContact() {
        Contact contact = new Contact();
        contact.setFirstName("Mikhail");
        contact.setLastName("Lermontov");
        contact.setPhoneNumber("1234567890");
        contact.setEmail("mikhail.lermontov@example.com");

        ResponseEntity<Contact> response = restTemplate.postForEntity("/contacts", contact, Contact.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testGetAllContacts() {
        ResponseEntity<Contact[]> response = restTemplate.getForEntity("/contacts", Contact[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testUpdateContact() {
        Contact contact = new Contact();
        contact.setFirstName("Alexander");
        contact.setLastName("Pushkin");
        contact.setPhoneNumber("9876543210");
        contact.setEmail("alexander.pushkin@example.com");

        ResponseEntity<Contact> postResponse = restTemplate.postForEntity("/contacts", contact, Contact.class);
        Long contactId = postResponse.getBody().getId();

        contact.setPhoneNumber("1111111111");
        HttpEntity<Contact> requestUpdate = new HttpEntity<>(contact);
        ResponseEntity<Contact> putResponse = restTemplate.exchange("/contacts/" + contactId, HttpMethod.PUT, requestUpdate, Contact.class);

        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(putResponse.getBody().getPhoneNumber()).isEqualTo("1111111111");
    }
}
