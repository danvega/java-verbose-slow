import java.util.List;

public class TodoRepository {

    List<Todo> findAll() {

        var sql = """
            SELECT
                id,
                user_id,
                title,
                completed
            FROM
                todo
        """;

        return List.of(null);
    }

}
