/*
 * This file is subject to the terms and conditions defined in file 'LICENSE.txt',
 * which is part of this source code package.
 */

package com.sda.microblogging.exception;

import static java.lang.String.format;

/**
 * @author pawel.iwan@ateknea.com
 */
public class IllegalParameterValueException extends RuntimeException {

    private static final String TITLE = "Illegal or missing parameter value";

    public IllegalParameterValueException(String message) {
        super(TITLE + ". " + message);
    }

    public IllegalParameterValueException(String format, Object... params) {
        this(format(format, params));
    }
}
