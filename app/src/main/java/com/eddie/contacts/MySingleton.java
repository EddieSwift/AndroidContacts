package com.eddie.contacts;

class MySingleton {
    private static final MySingleton ourInstance = new MySingleton();

    static MySingleton getInstance() {
        return ourInstance;
    }

    private MySingleton() {
    }
}
