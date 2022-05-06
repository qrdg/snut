// package com.curation.snut.controller;

// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import com.curation.snut.entity.CuLike;
// import com.curation.snut.entity.Curation;
// import com.curation.snut.repository.CuLikeRepository;
// import com.curation.snut.repository.CurationRepository;
// import com.curation.snut.repository.MemberRepository;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.transaction.annotation.Transactional;

// @Transactional
// @AutoConfigureMockMvc
// @SpringBootTest

// public class CuLikeApiControllerTest {
// @Autowired
// MemberRepository memberRepository;
// @Autowired
// CuLikeRepository cuLikeRepository;
// @Autowired
// MockMvc mockMvc;
// @Autowired
// CurationRepository curationRepository;

// @Test
// @WithMockUser
// void testCreateCuLike() throws Exception {
// Curation curation = addCuration();

// MockMvc.perform(post("/CuLike/") + curation.getCurationNo())
// .andExpect(status().isOK());

// CuLike cuLike = cuLikeRepository.findAll().get(0);

// assertNotNull(cuLike);
// assertNotNull(cuLike.getMember().getEmail());
// assertNotNull(cuLike.getCuration().getCurationNo());
// }

// @WithMockUser
// @Test

// void testDuplicateCuLike() throws Exception {
// Curation curation = addCuration();
// mockMvc.perform(post("/cuLike/" + curation.getCurationNo()))
// .andExpect(status().isOk());

// mockMvc.perform(post("/cuLike/" + curation.getCurationNo()))
// .andExpect(status().isBadRequest());

// CuLike cuLike = cuLikeRepository.findAll().get(0);

// assertNotNull(cuLike);
// assertNotNull(cuLike.getMember().getEmail());
// assertNotNull(cuLike.getCuration().getCurationNo());
// }
// }