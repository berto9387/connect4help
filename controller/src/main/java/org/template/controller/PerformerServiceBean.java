package org.template.controller;

import org.template.interfaces.IPerformerServiceController;

import javax.ejb.Stateful;

@Stateful(name = "PerformerServiceEJB")
public class PerformerServiceBean implements IPerformerServiceController {
    public PerformerServiceBean() {
    }
}
