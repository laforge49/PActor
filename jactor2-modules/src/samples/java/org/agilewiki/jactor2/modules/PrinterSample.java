package org.agilewiki.jactor2.modules;

import org.agilewiki.jactor2.core.plant.Plant;

public class PrinterSample {

    public static void main(String[] args) throws Exception {

        //A facility with one thread.
        final Plant plant = new Plant(1);

        try {

            //Print something.
            Printer.printlnAReq("Hello World!").call();

        } finally {
            //shutdown the plant
            plant.close();
        }

    }
}