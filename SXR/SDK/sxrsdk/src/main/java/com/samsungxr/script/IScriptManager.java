/* Copyright 2016 Samsung Electronics Co., LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.samsungxr.script;

import com.samsungxr.SXRAndroidResource;
import com.samsungxr.SXRMain;
import com.samsungxr.SXRResourceVolume;
import com.samsungxr.SXRScene;

import java.io.IOException;

import javax.script.ScriptEngine;

/**
 * The script manager handles script engines, script attachment/
 * detachment with scriptable objects, and other operation related to
 * scripting.
 */
public interface IScriptManager {

    public static final String LANG_JAVASCRIPT = "js";

    /**
     * Returns an engine based on language.
     *
     * @param language The name of the language. Please use constants
     * defined in {@code ScriptManager}, such as LANG_JAVASCRIPT.
     *
     * @return The engine object. {@code null} if the specified engine is
     * not found.
     */
    ScriptEngine getEngine(String language);

    /**
     * Gets a script file from a scriptable target.
     * @param target The scriptable target.
     * @return The script file or {@code null}.
     */
    IScriptFile getScriptFile(IScriptable target);

    /**
     * Destroys this engine
     */
    void destroy();

    /**
     * Loads a script file using {@link SXRAndroidResource}.
     * @param resource The resource object.
     * @param language The language string.
     * @return A script file object or {@code null} if not found.
     * @throws IOException if script file cannot be read.
     * @throws SXRScriptException if script processing error occurs.
     */
    IScriptFile loadScript(SXRAndroidResource resource, String language) throws IOException, SXRScriptException;

    /**
     * Attach a script file to a scriptable target.
     *
     * @param target The scriptable target.
     * @param scriptFile The script file object.
     */
    void attachScriptFile(IScriptable target, IScriptFile scriptFile);

    /**
     * Detach any script file from a scriptable target.
     *
     * @param target The scriptable target.
     */
    void detachScriptFile(IScriptable target);

    /**
     * Add a variable to the scripting context.
     *
     * @param varName The variable name.
     * @param value The variable value.
     */
    void addVariable(String varName, Object value);

    /**
     * Adds global bindings to the script engine
     * @param engine: the script engine for a particular script language
     */
    void addGlobalBindings(ScriptEngine engine);

    /**
     * Load a script bundle file. It defines bindings between scripts and SXRf objects
     * (e.g., nodes and the {@link SXRMain} object).
     *
     * @param filePath
     *        The path and filename of the script bundle.
     * @param volume
     *        The {@link SXRResourceVolume} from which to load the bundle file and scripts.
     * @return
     *         The loaded {@linkplain IScriptBundle script bundle}.
     *
     * @throws IOException if script bundle file cannot be read.
     */
    IScriptBundle loadScriptBundle(String filePath, SXRResourceVolume volume) throws IOException;

    /**
     * Binds a script bundle to a {@link SXRScene} object.
     *
     * @param scriptBundle
     *     The script bundle.
     * @param gvrMain
     *     The {@link SXRMain} to bind to.
     * @param bindToMainScene
     *     If {@code true}, also bind it to the main scene on the event {@link SXRMain#onAfterInit}.
     * @throws IOException if script bundle file cannot be read.
     * @throws SXRScriptException if script processing error occurs.
     */
    void bindScriptBundle(IScriptBundle scriptBundle, SXRMain gvrMain, boolean bindToMainScene)
            throws IOException, SXRScriptException;
}
