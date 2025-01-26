package com.assessment.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testGettersAndSetters() {
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setAge(30);
        user.setGender("Male");
        user.setPhone("1234567890");
        user.setUsername("john_doe");
        user.setPassword("password123");

        // Validate getters
        assertEquals(1, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals(30, user.getAge());
        assertEquals("Male", user.getGender());
        assertEquals("1234567890", user.getPhone());
        assertEquals("john_doe", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testNestedEntities() {
        User.Address address = new User.Address();
        address.setAddress("123 Main St");
        address.setCity("Sample City");
        address.setPostalCode("12345");
        address.setState("Sample State");

        user.setAddress(address);

        assertNotNull(user.getAddress());
        assertEquals("123 Main St", user.getAddress().getAddress());
        assertEquals("Sample City", user.getAddress().getCity());
        assertEquals("12345", user.getAddress().getPostalCode());
        assertEquals("Sample State", user.getAddress().getState());
    }

    @Test
    void testNestedBankEntity() {
        User.Bank bank = new User.Bank();
        bank.setCardExpire("12/23");
        bank.setCardNumber("1234567890123456");
        bank.setCardType("Visa");
        bank.setCurrency("USD");
        bank.setIban("US123456");

        user.setBank(bank);

        assertNotNull(user.getBank());
        assertEquals("12/23", user.getBank().getCardExpire());
        assertEquals("1234567890123456", user.getBank().getCardNumber());
        assertEquals("Visa", user.getBank().getCardType());
        assertEquals("USD", user.getBank().getCurrency());
        assertEquals("US123456", user.getBank().getIban());
    }

    @Test
    void testNestedCryptoEntity() {
        User.Crypto crypto = new User.Crypto();
        crypto.setCoin("Bitcoin");
        crypto.setWallet("0xb9fc2fe63b2a6c003f1c324c3bfa53259162181a");
        crypto.setNetwork("Ethereum (ERC20)");

        user.setCrypto(crypto);

        assertNotNull(user.getCrypto());
        assertEquals("Bitcoin", user.getCrypto().getCoin());
        assertEquals("0xb9fc2fe63b2a6c003f1c324c3bfa53259162181a", user.getCrypto().getWallet());
        assertEquals("Ethereum (ERC20)", user.getCrypto().getNetwork());
    }

    @Test
    void testNestedHairEntity() {
        User.Hair hair = new User.Hair();
        hair.setColor("Black");
        hair.setType("Short");

        user.setHair(hair);

        assertNotNull(user.getHair());
        assertEquals("Black", user.getHair().getColor());
        assertEquals("Short", user.getHair().getType());
    }

    @Test
    void testNestedCompanyEntity() {
        User.Company company = new User.Company();
        company.setDepartment("Engineering");
        company.setName("Dooley, Kozey and Cronin");
        company.setTitle("Sales Manager");

        user.setCompany(company);

        assertNotNull(user.getCompany());
        assertEquals("Engineering", user.getCompany().getDepartment());
        assertEquals("Dooley, Kozey and Cronin", user.getCompany().getName());
        assertEquals("Sales Manager", user.getCompany().getTitle());
    }

    @Test
    void testNestedCoordinatesEntity() {
        User.Address address = new User.Address();
        User.Coordinates coordinates = new User.Coordinates();
        coordinates.setLat(40.7128);
        coordinates.setLng(74.0060);
        
        address.setCoordinates(coordinates);

        assertNotNull(address.getCoordinates());
        assertEquals(40.7128, address.getCoordinates().getLat());
        assertEquals(74.0060, address.getCoordinates().getLng());
    }
}
