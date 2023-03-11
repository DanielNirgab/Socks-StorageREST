package com.example.socksstoragerest.controller;


import com.example.socksstoragerest.entity.SocksEntity;
import com.example.socksstoragerest.repository.SocksRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SocksStorageController.class)
public class SocksControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SocksRepository socksRepository;

    @MockBean
    private SocksStorageController socksStorageController;

    private SocksEntity testSocks;


    @BeforeEach
    void init() {

        testSocks = new SocksEntity();
        testSocks.setId(null);
        testSocks.setCottonPart(50);
        testSocks.setQuantity(1);
        testSocks.setColor("testColor");

    }

    @Test
    void shouldReturnInt_whenGetQuantityOfSocksBy() throws Exception {
        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt())).thenReturn(Optional.empty());
        when(socksRepository.findByColorAndCottonPartAfter(anyString(), anyInt())).thenReturn(Collections.emptyList());
        when(socksRepository.findByColorAndCottonPartBefore(anyString(), anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/socks")
                .queryParam("color", "black")
                .queryParam("operation", "moreThan")
                .queryParam("cottonPart", "25"))
                .andExpect(status().isOk())
                .andExpect(content().json("0"));

        mockMvc.perform(get("/socks")
                        .queryParam("color", "red")
                        .queryParam("operation", "equal")
                        .queryParam("cottonPart", "50"))
                .andExpect(status().isOk())
                .andExpect(content().json("0"));

        mockMvc.perform(get("/socks")
                        .queryParam("color", "green")
                        .queryParam("operation", "lessThan")
                        .queryParam("cottonPart", "50"))
                .andExpect(status().isOk())
                .andExpect(content().json("0"));
    }

    @Test
    void shouldPass_whenAddSocks() throws Exception {
        JSONObject testSocksObject = new JSONObject();
        testSocksObject.put("color", testSocks.getColor());
        testSocksObject.put("cottonPart", testSocks.getCottonPart());
        testSocksObject.put("quantity", testSocks.getQuantity());

        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(post("/socks/income")
                        .content(testSocksObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFail_whenAddSocksNotValid() throws Exception {
        JSONObject testSocksObject = new JSONObject();
        testSocksObject.put("color", testSocks.getColor());
        testSocksObject.put("cottonPart", testSocks.getCottonPart());
        testSocksObject.put("quantity", testSocks.getQuantity() * 0);

        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(post("/socks/income")
                        .content(testSocksObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldPass_whenRemoveSocks() throws Exception {
        JSONObject testSocksObject = new JSONObject();
        testSocksObject.put("color", testSocks.getColor());
        testSocksObject.put("cottonPart", testSocks.getCottonPart());
        testSocksObject.put("quantity", testSocks.getQuantity());

        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt())).thenReturn(Optional.of(testSocks));

        mockMvc.perform(post("/socks/outcome")
                        .content(testSocksObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFail_whenRemoveSocksMoreThanHave() throws Exception {
        JSONObject testSocksObject = new JSONObject();
        testSocksObject.put("color", testSocks.getColor());
        testSocksObject.put("cottonPart", testSocks.getCottonPart());
        testSocksObject.put("quantity", testSocks.getQuantity() * 2);

        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt())).thenReturn(Optional.of(testSocks));

        mockMvc.perform(post("/socks/outcome")
                        .content(testSocksObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldFail_whenRemoveSocksNotValid() throws Exception {
        JSONObject testSocksObject = new JSONObject();
        testSocksObject.put("color", testSocks.getColor());
        testSocksObject.put("cottonPart", testSocks.getCottonPart() * 1000);
        testSocksObject.put("quantity", testSocks.getQuantity());

        when(socksRepository.findByColorAndCottonPart(anyString(), anyInt())).thenReturn(Optional.of(testSocks));

        mockMvc.perform(post("/socks/outcome")
                        .content(testSocksObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
