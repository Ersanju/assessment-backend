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

        // Validate getters
        assertEquals(1, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }

    @Test
    void testNested_AddressEntity() {
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
    void testNested_BankEntity() {
        User.Bank bank = new User.Bank();
        bank.setCardExpire("12/23");
        bank.setCardNumber("1234567890123456");
        bank.setCardType("Visa");

        user.setBank(bank);

        assertNotNull(user.getBank());
        assertEquals("12/23", user.getBank().getCardExpire());
        assertEquals("1234567890123456", user.getBank().getCardNumber());
        assertEquals("Visa", user.getBank().getCardType());
    }

    @Test
    void testNested_CryptoEntity() {
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
    void testNested_HairEntity() {
        User.Hair hair = new User.Hair();
        hair.setColor("Black");
        hair.setType("Short");

        user.setHair(hair);

        assertNotNull(user.getHair());
        assertEquals("Black", user.getHair().getColor());
        assertEquals("Short", user.getHair().getType());
    }

    @Test
    void testNested_CompanyEntity() {
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
    void testNested_CoordinatesEntity() {
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
