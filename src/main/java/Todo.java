import java.util.Objects;

public record Todo(Integer userId, Integer id, String title, boolean completed) {
}
