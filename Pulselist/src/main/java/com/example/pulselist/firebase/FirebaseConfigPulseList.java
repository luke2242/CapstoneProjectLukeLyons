package com.example.pulselist.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
@Profile("!test")
public class FirebaseConfigPulseList {

    @Value("${FIREBASE_PRIVATE_KEY_JSON:}")
    private String privateKeyJson;

    @Value("${FIREBASE_PRIVATE_KEY_PATH:}")
    private String privateKeyPath;

    @Value("classpath:/firebase_private_key.json")
    private Resource classpathPrivateKey;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {

        if (!FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.getInstance();
        }

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(resolveCredentialsStream()))
                .build();

        return FirebaseApp.initializeApp(firebaseOptions);
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }

    private InputStream resolveCredentialsStream() throws IOException {
        if (StringUtils.hasText(privateKeyJson)) {
            String normalizedJson = privateKeyJson.replace("\\n", "\n");
            return new ByteArrayInputStream(normalizedJson.getBytes(StandardCharsets.UTF_8));
        }

        if (StringUtils.hasText(privateKeyPath)) {
            return Files.newInputStream(Path.of(privateKeyPath));
        }

        if (classpathPrivateKey.exists()) {
            return classpathPrivateKey.getInputStream();
        }

        throw new IOException("No Firebase credentials found. Set FIREBASE_PRIVATE_KEY_JSON or FIREBASE_PRIVATE_KEY_PATH in the environment, or include firebase_private_key.json on the classpath for local development.");
    }
}
