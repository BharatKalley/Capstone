# Selenium with Python - Capstone Project

This project demonstrates the use of Selenium with Python and pytest framework to test a web application. The tests include:

1. Verifying the title of a webpage.
2. Clicking on a link and switching to a new tab.
3. Selecting values from a dropdown menu.
4. Checking and verifying multiple checkboxes.
5. Selecting and verifying radio buttons.

## Setup Instructions

1. Clone the repository.
2. Create a virtual environment:
   ```
   python -m venv venv
   ```
3. Activate the virtual environment:
   - On Windows:
     ```
     .\venv\Scripts\activate
     ```
   - On macOS/Linux:
     ```
     source venv/bin/activate
     ```
4. Install dependencies:
   ```
   pip install selenium pytest
   ```

## Running Tests

To run the tests, execute the following command:
```bash
pytest tests/
```
