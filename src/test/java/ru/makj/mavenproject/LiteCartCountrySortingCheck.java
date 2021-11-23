package ru.makj.mavenproject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class LiteCartCountrySortingCheck {

    WebDriver driver;
    List<String> sortedList;
    List<String> geoZoneCities;
    WebElement elementsList;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @After
    public void stop() {
        driver.quit();
    }


    //1. проверить, что страны расположены в алфавитном порядке
    @Test
    public void runCountrySortingCheck() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        loginForm();

        List<WebElement> countryList = driver.findElements(By.cssSelector("form[name = countries_form] tr.row td:nth-child(5) a"));
        System.out.println("Check that countries are in alphabetical order: ");
        List<String> initialList = getGeoZonesCitiesNames(countryList, "textContent");

        sortedList = sortingAlphabetical(initialList);
        Assert.assertEquals(sortedList, initialList);
    }

    //1.2 для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить,
    // что зоны расположены в алфавитном порядке
    @Test
    public void runCountryGeoZonesSortingCheck() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        loginForm();
        List<WebElement> countryZonesList = driver.findElements(By.cssSelector("form[name = countries_form] tr.row td:nth-child(6)"));
        List<Integer> integers = getCountryGeoZonesList(countryZonesList);
        for (int i = 0; i < integers.size(); i++) {
            if (integers.get(i) > 0) {
                countryZonesList = driver.findElements(By.cssSelector("form[name = countries_form] tr.row td:nth-child(5) a"));
                elementsList = countryZonesList.get(i);
                elementsList.click();

                List<WebElement> citiesList = driver.findElements(By.cssSelector("#table-zones input[type=hidden][name*=name]"));
                System.out.println("Check that cities are in alphabetical order: ");
                geoZoneCities = getGeoZonesCitiesNames(citiesList, "value");

                sortedList = sortingAlphabetical(geoZoneCities);
                Assert.assertEquals(sortedList, geoZoneCities);
                System.out.println("\n");
                driver.navigate().back();
            }
        }
    }

    //2. зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке
    @Test
    public void runGeoZonesSortingCheck() {
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        loginForm();
        List<WebElement> countryList = driver.findElements(By.cssSelector("form[name = geo_zones_form] tr.row td:nth-child(4)"));
        for (int i = 0; i < countryList.size(); i++) {
            countryList = driver.findElements(By.cssSelector("form[name = geo_zones_form] tr.row td:nth-child(3) a"));
            elementsList = countryList.get(i);
            elementsList.click();

            List<WebElement> selectedElements = driver.findElements(By.cssSelector("select[name*=zone_code] [selected]"));
            System.out.println("Check that cities are in alphabetical order: ");
            geoZoneCities = getGeoZonesCitiesNames(selectedElements, "textContent");

            sortedList = sortingAlphabetical(geoZoneCities);
            Assert.assertEquals(sortedList, geoZoneCities);
            System.out.println("\n");
            driver.navigate().back();
        }
    }

    /**
     * Сортировка списка
     *
     * @param unsortedList список String
     * @return отсортированый список
     */
    private List<String> sortingAlphabetical(List<String> unsortedList) {
        List<String> sortedList = new ArrayList<>(unsortedList);
        sortedList.sort(Comparator.naturalOrder());
        return sortedList;
    }

    /**
     * Получение списка количества зон
     *
     * @param webElements список Веб Элементов
     * @return список Integers
     */
    private List<Integer> getCountryGeoZonesList(List<WebElement> webElements) {
        List<Integer> integers = new ArrayList<>();
        for (WebElement element : webElements) {
            int countryZone = Integer.parseInt(element.getAttribute("textContent"));
            integers.add(countryZone);
        }
        return integers;
    }

    /**
     * Получение списка названий городов/стран
     *
     * @param webElements   список Веб Элементов
     * @param attributeName имя атрибута для получения текста
     * @return список стран/городов
     */
    private List<String> getGeoZonesCitiesNames(List<WebElement> webElements, String attributeName) {
        List<String> cities = new ArrayList<>();
        for (WebElement selectedElement : webElements) {
            String citiesNames = selectedElement.getAttribute(attributeName);
            cities.add(citiesNames);
            System.out.println(citiesNames);
        }
        return cities;
    }

    private void loginForm() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }
}


