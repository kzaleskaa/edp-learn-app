package com.finalproject.config;

import com.finalproject.Models.POJO.User;
import com.google.inject.Injector;

public class InjectorHolder {
    private Injector injector;
    private final static InjectorHolder INSTANCE = new InjectorHolder();

    public InjectorHolder() {}

    public static InjectorHolder getInstance() {
        return INSTANCE;
    }

    public void setInjector(Injector u) {
        this.injector = u;
    }

    public Injector getInjector() {
        return this.injector;
    }
}
