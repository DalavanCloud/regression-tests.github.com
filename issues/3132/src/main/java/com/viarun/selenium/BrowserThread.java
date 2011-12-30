package com.viarun.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

public class BrowserThread {
    public static void main(String[] args) throws InterruptedException {
        List<Browser> browsers = new ArrayList<Browser>();
        for( int i = 0; i < 16; i++ ){
            browsers.add(new Browser(":"+args[0]+"."+i));
        }


        System.out.println("Opening " + (browsers.size()-1) + " browsers in parallel...");
        for( Browser browser : browsers ){
            browser.start();
        }

        for( Browser browser : browsers ){
            browser.join();
        }

    }

    static class Browser extends Thread{
        String display;
        WebDriver driver;
        FirefoxBinary firefoxBinary;

        public Browser(String display) {
            this.display = display;
            firefoxBinary = new FirefoxBinary();
            firefoxBinary.setEnvironmentProperty("DISPLAY", display);
        }

        public void run() {
            System.out.println("   Opening Firefox on display " + display);
            try {
                driver = new FirefoxDriver(firefoxBinary,null);
            } catch (WebDriverException wde) {
                System.out.println("      ******* Browser on display " + display + " failed to open! *******");
                wde.printStackTrace();
                System.out.println();
                return;
            }
            System.out.println("      ... browser on display " + display + " opened.");

            System.out.println("   Closing browser on display : " + display);
            driver.quit();
            System.out.println("      ... browser on display " + display + " closed.");
        }
    }
}
