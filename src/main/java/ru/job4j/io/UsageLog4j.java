package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        LOG.trace("trace message");
        LOG.debug("debug message");
        LOG.info("info message");
        LOG.warn("warn message");
        LOG.error("error message");

        boolean bln = true;
        byte bt = 1;
        short srt = 5;
        int intgr = 10;
        long lng = 15L;
        float flt = 20f;
        double dbl = 30;
        char chr = 'a';
        LOG.debug(
                "boolean : {}, byte : {}, short : {}, int : {}, long : {}, float : {}, double : {}, char : {}",
                bln, bt, srt, intgr, lng, flt, dbl, chr
        );
    }
}