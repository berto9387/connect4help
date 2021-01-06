package org.template.controller;

import org.template.interfaces.IServiceController;

import javax.ejb.Singleton;

@Singleton(name = "ServiceControllerEJB")
public class ServiceControllerBean implements IServiceController {
    public ServiceControllerBean() {
    }
}
