package org.template.controller;

import org.template.interfaces.IPerformerServiceController;

import javax.ejb.Stateful;

@Stateful(name = "RequestServiceEJB")
public class RequestServiceBean implements IPerformerServiceController {
    public RequestServiceBean() {
    }
}
