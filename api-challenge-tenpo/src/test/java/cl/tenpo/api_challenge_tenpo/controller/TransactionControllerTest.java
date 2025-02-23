package cl.tenpo.api_challenge_tenpo.controller;

import cl.tenpo.api_challenge_tenpo.dto.TransactionDto;
import cl.tenpo.api_challenge_tenpo.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import java.util.Collections;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc
class TransactionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TransactionService transactionService;

  @BeforeEach
  void setUp() {
    transactionService = Mockito.mock(TransactionService.class);
  }

//  @Test
//  void testGetAllTransaction() throws Exception {
//    when(transactionService.findAll(any())).thenReturn(Collections.emptyList());
//
//    this.mockMvc.perform(MockMvcRequestBuilders.get("/transaction/paginated")
//            .contentType("application/json")
//            .content("{}"))
//        .andExpect(MockMvcResultMatchers.status().isOk());
//  }
//
//  @Test
//  void testGetAllTransactionsById() throws Exception {
//    when(transactionService.findById(anyInt())).thenReturn(new TransactionDto());
//
//    this.mockMvc.perform(MockMvcRequestBuilders.get("/transaction/1")
//            .contentType("application/json")
//            .content("{}"))
//        .andExpect(MockMvcResultMatchers.status().isOk());
//  }
//
//  @Test
//  void testSaveTransaction() throws Exception {
//    when(transactionService.save(any(TransactionDto.class)));
//
//    this.mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
//            .contentType("application/json")
//            .content("{}"))
//        .andExpect(MockMvcResultMatchers.status().isOk());
//  }
//
//  @Test
//  void testEditTransaction() throws Exception {
//    when(transactionService.edit(any(TransactionDto.class),anyInt())).thenReturn(any(TransactionDto.class));
//
//    this.mockMvc.perform(MockMvcRequestBuilders.put("/transaction/1")
//            .contentType("application/json")
//            .content("{}"))
//        .andExpect(MockMvcResultMatchers.status().isOk());
//  }
//
//  @Test
//  void testDeleteTransaction() throws Exception {
//    this.mockMvc.perform(MockMvcRequestBuilders.delete("/transaction/1"))
//        .andExpect(MockMvcResultMatchers.status().isOk());
//  }
}