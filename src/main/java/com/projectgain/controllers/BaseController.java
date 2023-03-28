package com.projectgain.controllers;

import com.projectgain.views.ViewFactory;

public abstract class BaseController {
    private String fxmlViewName;
    protected ViewFactory viewFactory;

    public BaseController(String fxmlViewName, ViewFactory viewFactory) {
        this.fxmlViewName = fxmlViewName;
        this.viewFactory = viewFactory;
    }
    public String getFxmlViewName() {
        return fxmlViewName;
    }

    public void setFxmlViewName(String fxmlViewName) {
        this.fxmlViewName = fxmlViewName;
    }
}
