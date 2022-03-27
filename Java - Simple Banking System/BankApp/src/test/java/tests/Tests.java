package tests;

import com.bankApp.BankApp;
import com.bankApp.userTypes.DBAdministrator;
import com.bankApp.userTypes.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    @Test
    void testUser(){
        User user = new User("Negrea", "Balazs", "6140765244531");
        assertEquals("Balazs Negrea",user.getName());
        assertEquals("some text",user.getCnp());
    }

    @Test
    void testBankAccount(){

    }

    @Test
    void testDataBase(){

    }
}
