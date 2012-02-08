/*
 * Copyright (C) 2011 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Interfaces;

import com.noheroes.xpbank.Exceptions.InsufficientPermissionException;
import com.noheroes.xpbank.Exceptions.MissingOrIncorrectArgumentException;

/**
 * @author Sorklin <sorklin at gmail.com>
 */
public interface Cmd {
    public boolean execute() 
            throws MissingOrIncorrectArgumentException, InsufficientPermissionException;
}
