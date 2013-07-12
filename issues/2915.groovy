@Grapes([
 @Grab("org.seleniumhq.selenium:selenium-java:2.33.0")
])

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.By

def driver = new FirefoxDriver()

def domain
if (args.length == 0) {
  domain = "http://regression-tests.seleniumhq.org"
} else {
  domain = args[0]
}

driver.get domain + "/demo/2915/index.html"

try {
  def pwField = driver.findElement(By.id("cheddarCheeseLoginPassword"))
  pwField.sendKeys("Selenium") // this does not barf.
  String pw = pwField.getAttribute("value") // barfs here if bug is present
  if (pw == "Selenium") {
      println "Issue 2915 not present, field value retrieved" 
  } else {
      println "field value retrieved but it is incorrect: '$pw'" // it SOMETIMES does this path when the FF screen is hidden. 
  } 
} catch(StaleElementReferenceException e) {
  println "Issue 2915 present, get via workaround? " 
  println "locate it again, and get the field value: '" + driver.findElement(By.id("cheddarCheeseLoginPassword")).getAttribute("value") + "'"
} finally {
  driver.quit()
}
