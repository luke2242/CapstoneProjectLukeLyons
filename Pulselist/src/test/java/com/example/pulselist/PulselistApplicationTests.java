package com.example.pulselist;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(properties = {
        "firebase.project-id=test",
        "FIREBASE_PROJECT_ID=test"
})
@ActiveProfiles("test")
class PulselistApplicationTests {

        @MockitoBean
        private FirebaseAuth firebaseAuth;

        @MockitoBean
        private FirebaseApp firebaseApp;

    @Test
    void contextLoads() {
    }

}
