import requests
import json
import time

URLs = [
    "http://localhost:8080/bfhl",
    "https://bajajapiround-production.up.railway.app/bfhl"
]

with open("testcases.json", "r") as file:
    test_cases = json.load(file)

passed = 0
failed = 0

for URL in URLs:

    print(f"\n========== API TEST STARTED for {URL} ==========\n")

    for test in test_cases:

        print(f"Running Test: {test['name']}")

        try:

            response = requests.post(
                URL,
                headers={"Content-Type": "application/json"},
                json=test["payload"],
                timeout=15
            )

            if response.status_code != 200:
                print(f"FAILED -> Status Code {response.status_code}\n")
                failed += 1
                continue

            # JSON received from server
            data = response.json()

            # Expected JSON structure
            expected = test["expected"]

            errors = []

            # Compare every expected field dynamically
            for key, expected_value in expected.items():

                actual_value = data.get(key)

                if actual_value != expected_value:
                    errors.append(
                        f"{key} mismatch | expected={expected_value} got={actual_value}"
                    )

            # Optional:
            # Check for unexpected extra keys
            extra_keys = set(data.keys()) - set(expected.keys())

            if extra_keys:
                errors.append(
                    f"Unexpected fields returned: {list(extra_keys)}"
                )

            if errors:

                print("FAILED")

                for err in errors:
                    print(" -", err)

                print()

                failed += 1

            else:

                print("PASS\n")
                passed += 1

        except Exception as e:

            print("ERROR ->", str(e))
            print()

            failed += 1

        time.sleep(0.5)

    print("========== SUMMARY ==========")
    print("PASSED :", passed)
    print("FAILED :", failed)
    print("=============================")