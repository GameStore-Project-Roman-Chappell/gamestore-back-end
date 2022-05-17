package com.gamestore.gamestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamestore.gamestore.model.Console;
import com.gamestore.gamestore.repository.ConsoleRepository;
import com.gamestore.gamestore.service.ServiceLayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsolesController.class)
public class ConsolesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    @MockBean
    ConsoleRepository consoleRepo;

    private ObjectMapper mapper = new ObjectMapper();

    Console inputXbox;

    Console outputXbox;

    String inputConsoleString;

    String outputConsoleString;

    List<Console> allConsoles;

    String allConsolesString;




    @Before
    public void setUp() throws Exception {
        inputXbox = new Console(1,"Xbox", "Microsoft", "1TB", "AMD", new BigDecimal(499.99), 50);
        outputXbox = new Console(1,"Xbox", "Microsoft", "1TB", "AMD", new BigDecimal(499.99), 50);
        inputConsoleString = mapper.writeValueAsString(inputXbox);
        outputConsoleString = mapper.writeValueAsString(outputXbox);
        allConsoles = Arrays.asList(outputXbox);
        allConsolesString = mapper.writeValueAsString(allConsoles);


        when(serviceLayer.saveConsole(inputXbox)).thenReturn(outputXbox);
        when(serviceLayer.findAllConsoles()).thenReturn(allConsoles);
        when(serviceLayer.findConsole(1)).thenReturn(outputXbox);



    }

    @Test
    public void shouldAddConsoleOnPostRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputXbox);
        String outputJson = mapper.writeValueAsString(outputXbox);

        mockMvc.perform(post("/consoles")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }


    @Test
    public void shouldGetArrayOfConsoles() throws Exception {
        mockMvc.perform(get("/consoles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allConsolesString));
    }

    @Test
    public void shouldGetConsolesById() throws Exception {
        mockMvc.perform(get("/consoles/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputConsoleString));
    }

    @Test
    public void shouldUpdateConsoles() throws Exception {
        mockMvc.perform(put("/consoles/1")
                .content(outputConsoleString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteConsole() throws Exception {
        mockMvc.perform(delete("/consoles/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenFindingInvalidId() throws Exception {
        mockMvc.perform(get("/consoles/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422WhenPutRequestContainsNonMatchingIds() throws Exception {
        mockMvc.perform(put("/consoles/999")
                .content(inputConsoleString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

}