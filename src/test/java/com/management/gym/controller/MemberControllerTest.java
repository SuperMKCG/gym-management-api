package com.management.gym.controller;

import com.management.gym.dto.request.member.MemberCreateRequestDTO;
import com.management.gym.dto.request.member.MemberUpdateRequestDTO;
import com.management.gym.dto.response.MemberResponse;
import com.management.gym.security.JwtService;
import com.management.gym.service.MemberService;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService memberService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    private MemberResponse mockMemberResponse;
    private MemberResponse mockUpdatedResponse;

    @BeforeAll
    public static void beforeTesting(){
        System.out.println("===Beginning of member controllers testing===");
    }

    @AfterAll
    public static void afterTesting(){
        System.out.println("===Ending of member controllers testing===");
    }

    @Captor
    private ArgumentCaptor<MemberCreateRequestDTO> memberCreatedCaptor;

    @Captor
    private ArgumentCaptor<MemberUpdateRequestDTO> memberUpdatedCaptor;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        this.chargeMember();
    }

    public void chargeMember(){
        mockMemberResponse = new MemberResponse(
                1L,
                "John Smith",
                "john@gmail.com",
                "3345668902",
                LocalDate.now(),
                true,
                1L
        );

        mockUpdatedResponse = new MemberResponse(
                1L,
                "Michael Jordan",
                "mj@gmail.com",
                "3345668902",
                LocalDate.now(),
                false,
                1L
        );
    }

    @Test
    @DisplayName("Create members controller test with verification")
    public void shouldCreateMember() throws Exception {
        when(memberService.createMember(any())).thenReturn(mockMemberResponse);

        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Smith\", \"email\":\"john@gmail.com\", \"phone\":\"3345668902\", \"planID\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idMember").value(mockMemberResponse.idMember()))
                .andExpect(jsonPath("$.name").value(mockMemberResponse.name()))
                .andExpect(jsonPath("$.email").value(mockMemberResponse.email()));

        verify(memberService).createMember(memberCreatedCaptor.capture());

        MemberCreateRequestDTO capturedRequest = memberCreatedCaptor.getValue();
        assertEquals("John Smith", capturedRequest.name());
        assertEquals("john@gmail.com", capturedRequest.email());
    }

    //Test get all members controller
    @Test
    @DisplayName("Get all members controller test")
    public void shouldReturnOkWhenGettingMembers() throws Exception {
        mockMvc.perform(get("/members"))
                .andExpect(status().isOk());
    }

    //Test update members by id controller
    @Test
    @DisplayName("Update member id controller test")
    public void shouldUpdateMember() throws Exception {
        when(memberService.updateMemberByID(any(),any(Long.class))).thenReturn(mockUpdatedResponse);

        mockMvc.perform(patch("/members/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":null,\"name\":\"Michael Jordan\",\"email\":\"mj@gmail.com\",\"phone\":null,\"date\":null,\"active\":false,\"planID\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(mockUpdatedResponse.name()))
                .andExpect(jsonPath("$.email").value(mockUpdatedResponse.email()))
                .andExpect(jsonPath("$.active").value(mockUpdatedResponse.active()));

        verify(memberService).updateMemberByID(memberUpdatedCaptor.capture(), eq(1L));

        MemberUpdateRequestDTO capturedRequest = memberUpdatedCaptor.getValue();
        assertEquals("Michael Jordan", capturedRequest.name());
        assertEquals("mj@gmail.com", capturedRequest.email());
        assertFalse(capturedRequest.active());
    }


}
