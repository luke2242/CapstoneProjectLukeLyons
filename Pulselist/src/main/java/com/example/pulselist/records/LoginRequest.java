import com.fasterxml.jackson.annotation.JsonAlias;

public record LoginRequest(
    @JsonAlias({"username", "email"}) String username,
    String password
) {}
