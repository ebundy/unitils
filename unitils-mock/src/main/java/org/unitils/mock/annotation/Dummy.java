/*
 * Copyright 2008,  Unitils.org
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
package org.unitils.mock.annotation;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Annotation that can be used on fields to create dummy objects for these fields.
 * A dummy object is a proxy that will return default values for every method. This can be used to quickly create test
 * objects without having to worry about correctly filling in every field. Even classes with non-public default constructors
 * can be dummyfied by annotating the field.
 *
 * Example:
 *
 * '@Dummy
 * private MyClass myClass;
 *
 * This will create a proxy for MyClass that will return default values for all methods (when needed ofcourse). The
 * dummy will be created regardless whether there is a default constructor.
 *
 * <p/>
 * Following defaults are used:<ul>
 * <li>Number values: 0</li>
 * <li>Object values: null</li>
 * <li>Collectionn, arrays etc: empty values</li></ul>
 * <p/>
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Dummy {

}