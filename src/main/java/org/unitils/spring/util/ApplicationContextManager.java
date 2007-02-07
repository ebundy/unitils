/*
 * Copyright 2006 the original author or authors.
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
package org.unitils.spring.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.unitils.core.UnitilsException;
import org.unitils.core.util.AnnotatedInstanceManager;
import org.unitils.spring.annotation.SpringApplicationContext;

import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;

/**
 * todo javadoc
 * <p/>
 * A class for storing and creating Spring application contexts.
 *
 * @author Tim Ducheyne
 * @author Filip Neven
 */
public class ApplicationContextManager extends AnnotatedInstanceManager<ApplicationContext, SpringApplicationContext> {


    public ApplicationContextManager() {
        super(ApplicationContext.class, SpringApplicationContext.class);
    }


    /**
     * Gets the application context for the given test. A new one will be created if it does not exist yet. If a superclass
     * has also declared the creation of an application context, this one will be retrieved (or created if it was not
     * created yet) and used as parent context for this classes context.
     * <p/>
     * If needed, an application context will be created using the settings of the {@link SpringApplicationContext}
     * annotation.
     * <p/>
     * If a class level {@link SpringApplicationContext} annotation is found, the passed locations will be loaded using
     * a <code>ClassPathXmlApplicationContext</code>.
     * Custom creation methods can be created by annotating them with {@link SpringApplicationContext}. They
     * should have an <code>ApplicationContext</code> as return type and either no or exactly 1 argument of type
     * <code>ApplicationContext</code>. In the latter case, the current configured application context is passed as the argument.
     * <p/>
     * A UnitilsException will be thrown if no context could be retrieved or created.
     *
     * @param testObject The test instance, not null
     * @return The application context, not null
     */
    public ApplicationContext getApplicationContext(Object testObject) {
        return super.getInstance(testObject);
    }


    /**
     * Forces the reloading of the application context the next time that it is requested. If classes are given
     * only contexts that are linked to those classes will be reset. If no classes are given, all cached
     * contexts will be reset.
     *
     * @param classes The classes for which to reset the contexts
     */
    public void invalidateApplicationContext(Class<?>... classes) {
        super.invalidateInstance(classes);
    }


    /**
     * Creates an application context for the given locations. A <code>ClassPathXmlApplicationContext</code> will be
     * created with the given application context as parent.
     * <p/>
     * If the locations array is null or empty or contains a single empty location, no new context is created. The parent
     * application context will be returned instead.
     *
     * @param values The file locations
     * @return The new context or the parent if no new context was created
     */
    protected ApplicationContext createInstanceForValues(List<String> values) {
        try {
            // create application context
            return new ClassPathXmlApplicationContext(new ArrayList<String>(values).toArray(new String[0]));

        } catch (Throwable t) {
            throw new UnitilsException("Unable to create application context for locations " + values, t);
        }
    }

    protected List<String> getAnnotationValues(SpringApplicationContext annotation) {
        if (annotation == null) {
            return null;
        }
        String[] locations = annotation.value();
        if (locations.length == 0 || (locations.length == 1 && StringUtils.isEmpty(locations[0]))) {
            return null;
        }
        return asList(locations);
    }
}