package org.hmk;

public class Main {
    public static void main(String[] args) {
        Config config = ConfigLoader.load();

        new Automation().start(config);
    }
}