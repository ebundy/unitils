/*
 * Copyright (C) 2006, Ordina
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package be.ordina.unitils.db.maintainer.script;

import be.ordina.unitils.db.maintainer.VersionScriptPair;

import java.util.Properties;
import java.util.List;
import java.util.Collections;
import java.io.File;

/**
 * Implementation of <code>ScriptSource</code> that reads script files from the filesystem, given a version in the
 * form of a last modification date. This implementation is recreating the database from scratch each time one of the
 * DDL scripts is updated.
 * <p>
 * The directory and the file extension are configured using a Properties object by invoking the init method
 */
public class FromScratchFileScriptSource extends AbstractFileScriptSource {

    public void init(Properties properties) {
        super.init(properties);

    }

    public List<VersionScriptPair> getScripts(Long currentVersion) {
        List<File> scriptFiles = getScriptFilesSorted();
        Long scriptsTimestamp = getHighestScriptTimestamp(scriptFiles);
        if (scriptsTimestamp > currentVersion) {
            return getStatementsFromFiles(scriptFiles);
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private Long getHighestScriptTimestamp(List<File> scriptFiles) {
        Long highestTimestamp = 0L;
        for (File scriptFile : scriptFiles) {
            highestTimestamp = Math.max(highestTimestamp, scriptFile.lastModified());
        }
        return highestTimestamp;
    }

}
