# Java is too verbose and slow

I've worked with JVM for most of my 23-year career, and it seems like every week someone declares Java as dead, calling it ancient, verbose, and slow. However, these claims couldn't be further from the truth.

Such comments often come from people who haven't written any Java since their introductory computer science course 20 years ago. Yes, Java has evolved over the years, but that's expected of a language that has been around for 28 years. The world relies heavily on Java, and it remains one of the most popular programming languages, regardless of the metric or survey you consider.

This repository and video serve as examples demonstrating that Java is neither verbose nor slow.

## Verbose

- Unnamed classes and Instance Methods
- var keyword
- Records
- String Templates
- Text Blocks

Anyone who tells you that Java is verbose probably hasn't written Java since CS 101 about 15 years ago. 

### JEP 445: Unnamed Classes and Instance Methods

Evolve the Java language so that students can write their first programs without needing to understand language features designed for large programs. Far from using a separate dialect of Java, students can write streamlined declarations for single-class programs and then seamlessly expand their programs to use more advanced features as their skills grow.

https://openjdk.org/jeps/445

Before JEP 445: 

```java
package dev.danvega;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

After JEP 445: 

```java
void main() {
    System.out.println("Hello, World!");
}
```

### var keyword 

In JDK 10 and later, you can declare local variables with non-null initializers with the var identifier, which can help you write code that’s easier to read,

```java
void main() throws IOException, InterruptedException {
    
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
            .build();
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.body());

}
```

```java
void main() throws IOException, InterruptedException {

    var httpClient = HttpClient.newHttpClient();
    var request = HttpRequest.newBuilder() // var
            .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
            .build();
    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.body());
    
}
```

### Records

Record classes, which are a special kind of class, help to model plain data aggregates with less ceremony than normal classes.

For background information about record classes, see JEP 395.

A record declaration specifies in a header a description of its contents; the appropriate accessors, constructor, equals, hashCode, and toString methods are created automatically. A record's fields are final because the class is intended to serve as a simple "data carrier".

Here is an example of a regular class and a record class that represent the same data:

```java
public class Todo {

    private Integer userId;
    private Integer id;
    private String title;
    private boolean completed;

    // no-args constructor
    public Todo() {
    }

    // all-args constructor
    public Todo(Integer userId, Integer id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return completed == todo.completed && Objects.equals(userId, todo.userId) && Objects.equals(id, todo.id) && Objects.equals(title, todo.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, id, title, completed);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
```

```java
public record Todo(Integer userId, Integer id, String title, boolean completed) {
}
```

### String Templates

String templates complement Java's existing string literals and text blocks by coupling literal text with embedded expressions and template processors to produce specialized results. An embedded expression is a Java expression except it has additional syntax to differentiate it from the literal text in the string template. A template processor combines the literal text in the template with the values of the embedded expressions to produce a result.

```java
@Override
public String toString() {
    return "Todo{" +
            "userId=" + userId +
            ", id=" + id +
            ", title='" + title + '\'' +
            ", completed=" + completed +
            '}';
}
```

```java
@Override
public String toString() {
    return STR."Todo{userId=\{userId}, id=\{id}, title='\{title}\{'\''}, completed=\{completed}\{'}'}";
}
```

### Text Blocks

A text block's principalis munus is to provide clarity by way of minimizing the Java syntax required to render a string that spans multiple lines.

In earlier releases of the JDK, embedding multi-line code snippets required a tangled mess of explicit line terminators, string concatenations, and delimiters. Text blocks eliminate most of these obstructions, allowing you to embed code snippets and text sequences more or less as-is.

Here is an example of a SQL query in a string literal and a text block:

```java
public class TodoRepository {

    List<Todo> findAll() {

        var sql = "    SELECT\n" +
                  "        id,\n" +
                  "        user_id,\n" +
                  "        title,\n" +
                  "        completed\n" +
                  "    FROM\n" +
                  "        todo\n";

        return List.of(null);
    }

}
```

Text Blocks

```java
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
```


## Slow

The [One Billion Row Challenge (1BRC)](https://github.com/gunnarmorling/1brc?tab=readme-ov-file) is a fun exploration of how far modern Java can be pushed for aggregating one billion rows from a text file. Grab all your (virtual) threads, reach out to SIMD, optimize your GC, or pull any other trick, and create the fastest implementation for solving this task!

The top 3 results all use GraalVM to compile down to a native image: 

- 00:01.535
- 00:01.587
- 00:01.608

Yes you are reading that correctly, less than 2 seconds to read in 1 billion rows of data. If you take away GraalVM the top 3 results are: 

- 00:02.367
- 00:02.507
- 00:02.557

That is less than 3 seconds to read in 1 billion rows of data.

## Insecure

WASHINGTON – Today, the White House Office of the National Cyber Director (ONCD) [released a report](https://www.whitehouse.gov/oncd/briefing-room/2024/02/26/press-release-technical-report/) calling on the technical community to proactively reduce the attack surface in cyberspace. ONCD makes the case that technology manufacturers can prevent entire classes of vulnerabilities from entering the digital ecosystem by adopting memory safe programming languages.

Memory safe languages are 

- Java
- Go
- C#
- Ruby
- Swift
- ...

## Conclusion

