package org.agilewiki.jactor2.core.blades.misc;

import org.agilewiki.jactor2.core.blades.SyncAgentBase;

/**
 * A PrinterAgent can be used to print multiple lines
 * without having any other text being interleaved by other blades
 * using the same Printer.
 * </p>
 * <h3>Sample Usage:</h3>
 * <pre>
 * //Prints a banner without allowing any intervening lines.
 * public class PrinterAgentSample extends PrinterAgent {
 *
 *     public PrinterAgentSample(final Printer _printer) throws Exception {
 *         super(_printer);
 *     }
 *
 *     // Returns a request to print a Hi! banner.
 *     public SyncBladeRequest<Void> startSReq() {
 *         return new SyncBladeRequest<Void>() {
 *
 *             {@literal @}Override protected Void processSyncRequest() throws Exception {
 *                 println("*********");
 *                 println("*       *");
 *                 println("*  Hi!  *");
 *                 println("*       *");
 *                 println("*********");
 *                 return null;
 *             }
 *         };
 *     }
 * }
 * </pre>
 */
abstract public class PrinterAgent extends SyncAgentBase<Void, Printer> {
    /**
     * Create a printer adjunct.
     *
     * @param _printer The printer used to print the text.
     */
    public PrinterAgent(final Printer _printer) throws Exception {
        super(_printer);
    }

    /**
     * Print a string.
     *
     * @param _string The string to be printed
     */
    protected void println(final String _string) throws Exception {
        local(blade.printlnSReq(_string));
    }

    /**
     * Print a formatted string.
     *
     * @param _format The formatting.
     * @param _args   The data to be formatted.
     */
    protected void printf(final String _format,
                          final Object... _args) throws Exception {
        local(blade.printSReq(_format, _args));
    }
}
