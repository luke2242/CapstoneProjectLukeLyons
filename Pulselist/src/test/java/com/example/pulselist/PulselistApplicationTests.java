package com.example.pulselist;

import com.example.pulselist.firebase.FirebaseConfigPulseList;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class PulselistApplicationTests {

        @MockitoBean
        private FirebaseAuth firebaseAuth;

        @MockitoBean
        private FirebaseApp firebaseApp;

    @Test
    void contextLoads() {
    }

}
