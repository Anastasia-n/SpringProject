package ru.anastasia.springcourse.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.web.context.WebApplicationContext;
import ru.anastasia.springcourse.config.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class MainControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
               // .defaultRequest(get("/")
                //       .with(user("admin").password("12345").roles("USER")))
                .apply(springSecurity())
                .build();
    }

   @Test
   @WithMockUser(username = "admin", password = "12345", roles = "USER")
    public void testMainPage() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("mainPage"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "12345", roles = "USER")
    public void testEditName() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/home/{id}/edit",1)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
