@Grapes([
 @Grab("org.seleniumhq.selenium:selenium-java:2.13.0"),
 @GrabExclude('xml-apis:xml-apis')
])

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.By

def driver = new FirefoxDriver()

def domain
if (args.length == 0) {
  domain = "http://seleniumhq.github.com/regression-tests.github.com"
} else {
  domain = args[0]
}

driver.get domain + "/demo/2915/index.html"

def pwField = driver.findElement(By.id("cheddarCheeseLoginPassword"))
pwField.sendKeys("Selenium")


try {
  def pwField2 = driver.findElement(By.id("cheddarCheeseLoginPassword"))
  pwField.getText() // barfs here if bug is present
  println "Issue 2915 not present"
} catch(StaleElementReferenceException e) {
  println "Issue 2915 present"
} finally {
  driver.quit()
}
