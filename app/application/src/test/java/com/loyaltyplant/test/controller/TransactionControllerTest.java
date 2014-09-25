package com.loyaltyplant.test.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TransactionControllerTest.Config.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@WebAppConfiguration
public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @DatabaseSetup("transaction-test-balances-before.xml")
    public void testDebitFailure() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = post("/api/transaction/debit")
                .accept(MediaType.APPLICATION_JSON)
                .param("from", "1")
                .param("amount", "10");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.command").value(notNullValue()))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorMessage").value(notNullValue()));
    }

    @Test
    @DatabaseSetup("transaction-test-balances-before.xml")
    public void testDebitSuccess() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = post("/api/transaction/debit")
                .accept(MediaType.APPLICATION_JSON)
                .param("from", "2")
                .param("amount", "10");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.command").value(notNullValue()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorMessage").value(nullValue()));
    }

    @Test
    @DatabaseSetup("transaction-test-balances-before.xml")
    @ExpectedDatabase(value = "transaction-test-after-transfer.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testTransferSuccess() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = post("/api/transaction/transfer")
                .accept(MediaType.APPLICATION_JSON)
                .param("from", "2")
                .param("to", "1")
                .param("amount", "12");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.command").value(notNullValue()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorMessage").value(nullValue()));
    }

    @EnableAutoConfiguration
    @ComponentScan("com.loyaltyplant.test")
    public static class Config {

    }
}
