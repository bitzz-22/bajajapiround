package com.bfhl.bfhl;

import com.bfhl.bfhl.dto.RequestDto;
import com.bfhl.bfhl.dto.ResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BfhlController {

    @GetMapping("/health")
    public ResponseEntity<?> health() {

        Map<String, Object> response = new HashMap<>();

        response.put("status", "ok");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/bfhl")
    public ResponseEntity<?> process(@RequestBody RequestDto body) {

        try {

            List<Object> data = body.getData();

            if (data == null) {

                Map<String, Object> error = new LinkedHashMap<>();

                error.put("is_success", false);
                error.put("message", "Invalid input");

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(error);
            }

            List<String> evenNumbers = new ArrayList<>();
            List<String> oddNumbers = new ArrayList<>();
            List<String> alphabets = new ArrayList<>();
            List<String> specialCharacters = new ArrayList<>();

            long sum = 0;

            StringBuilder alphaConcat = new StringBuilder();

            for (Object item : data) {

                if (item == null) {
                    continue;
                }

                String value = String.valueOf(item);

                // numeric values
                if (value.matches("-?\\d+")) {

                    long number = Long.parseLong(value);

                    sum += number;

                    if (number % 2 == 0) {
                        evenNumbers.add(value);
                    } else {
                        oddNumbers.add(value);
                    }
                }

                // alphabets only
                else if (value.matches("[a-zA-Z]+")) {

                    alphabets.add(value.toUpperCase());

                    alphaConcat.append(value);
                }

                // special characters
                else {

                    specialCharacters.add(value);
                }
            }

            String allAlphabets = alphaConcat.toString();

            StringBuilder concatString = new StringBuilder();

            boolean makeUpper = true;

            for (int i = allAlphabets.length() - 1; i >= 0; i--) {

                char ch = allAlphabets.charAt(i);

                if (makeUpper) {
                    concatString.append(Character.toUpperCase(ch));
                } else {
                    concatString.append(Character.toLowerCase(ch));
                }

                makeUpper = !makeUpper;
            }

            ResponseDto response = new ResponseDto();

            response.setIs_success(true);

            response.setUser_id("monish_monnat_29062005");

            response.setEmail("monishmonnat230967@acropolis.in");

            response.setRoll_number("0827CY231045");

            response.setOdd_numbers(oddNumbers);

            response.setEven_numbers(evenNumbers);

            response.setAlphabets(alphabets);

            response.setSpecial_characters(specialCharacters);

            response.setSum(String.valueOf(sum));

            response.setConcat_string(concatString.toString());

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            Map<String, Object> error = new LinkedHashMap<>();

            error.put("is_success", false);

            error.put("message", "Something went wrong");

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error);
        }
    }
}