package com.babu.buggyapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BuggyController {

    private static final Logger logger = LoggerFactory.getLogger(BuggyController.class);
    private final Map<Integer, String> userData = new HashMap<>();

    public BuggyController() {
        userData.put(1, "Alice");
        userData.put(2, "Bob");
        // Intentionally skipping user 3 to cause null pointer later
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id) {
        logger.info("Fetching user with id: {}", id);
        String name = userData.get(id);
        return "User: " + name.toUpperCase();  // NullPointerException if user not found
    }

    @GetMapping("/convert/{value}")
    public String convertToNumber(@PathVariable String value) {
        logger.info("Converting '{}' to number...", value);
        int number = Integer.parseInt(value); // NumberFormatException if not a number
        return "Number: " + number;
    }

    @GetMapping("/calculate")
    public String calculate(@RequestParam(required = false) Integer x) {
        logger.info("Calculating 100 / x. Input x: {}", x);
        return "Result: " + (100 / x); // ArithmeticException if x is 0 or null
    }

    @GetMapping("/read-file")
    public String readFile(@RequestParam(defaultValue = "missing.txt") String filename) throws IOException {
        logger.info("Attempting to read file: {}", filename);
        FileReader reader = new FileReader(filename);  // FileNotFoundException if file missing
        reader.close();
        return "File read successfully!";
    }
}
