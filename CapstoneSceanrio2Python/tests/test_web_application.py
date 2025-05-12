import pytest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select

def test_verify_page_title(driver):
    driver.get("http://webdriveruniversity.com/index.html")
    heading_text = driver.find_element(By.ID, "nav-title").text
    assert heading_text == "WebdriverUniversity.com (New Approach To Learning)"

def test_click_dropdown_checkboxes_radiobuttons(driver):
    driver.find_element(By.XPATH, "//*[@id='dropdown-checkboxes-radiobuttons']/div/div[1]/h1").click()
    driver.switch_to.window(driver.window_handles[1])
    assert "Dropdown Menu(s)" in driver.title

def test_select_dropdown_value(driver):
    select = Select(driver.find_element(By.ID, "dropdowm-menu-1"))
    select.select_by_value("python")
    assert select.first_selected_option.text == "Python"

def test_verify_checkboxes(driver):
    checkboxes = driver.find_elements(By.CSS_SELECTOR, "input[type='checkbox']")
    for checkbox in checkboxes:
        if not checkbox.is_selected() and checkbox.is_enabled():
            checkbox.click()
    checked = len([cb for cb in checkboxes if cb.is_selected()])
    unchecked = len([cb for cb in checkboxes if not cb.is_selected()])
    assert checked > 0
    assert unchecked == 0

def test_verify_radio_buttons(driver):
    radio_buttons = driver.find_elements(By.CSS_SELECTOR, "input[type='radio']")
    # Ensure at least one radio button is selected
    if not any(rb.is_selected() for rb in radio_buttons):
        if radio_buttons:
            radio_buttons[0].click()  # Select the first radio button if none are selected

    # Count checked and unchecked radio buttons
    checked = len([rb for rb in radio_buttons if rb.is_selected()])
    unchecked = len([rb for rb in radio_buttons if not rb.is_selected()])

    # Assertions
    assert checked == 1, "There should be exactly one selected radio button."
    assert unchecked == len(radio_buttons) - 1, "The count of unchecked radio buttons should match."
