/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.artifacts;

import org.gradle.api.Incubating;

/**
 * A configuration attribute matcher is responsible for telling if an attribute from a source configuration
 * matches an attribute from a target configuration. Attribute matchers are setup through a {@link ConfigurationAttributesMatchingStrategy}.
 * When matching an attribute, Gradle will locate the corresponding attribute matcher and score the target
 * attribute against the source one.
 *
 * The {@link #score(String, String)} method is responsible for telling if an attribute matches or not. The {@link #defaultValue(String)}
 * method is called whenever a requested attribute is not found in the target configuration. In that case, this method is called with
 * the requested attribute value, giving a chance to provide a fallback value for this attribute. This can be used to implement
 * optional attributes or partial matches strategies.
 */
@Incubating
public interface ConfigurationAttributeMatcher {
    /**
     * Compares a requested attribute value to the candidate attribute value. There are 3 possible outcomes:
     * <ul>
     *     <li>if attribute values match strictly, then this method must return 0.</li>
     *     <li>if attribute values don't match, then this method must return -1.</li>
     *     <li>if attributes do not strictly match, but a compatibility is found, then return a score greater than 0. The closer to 0, the closer to a match we are.</li>
     * </ul>
     * @param requestedValue the value from the source configuration
     * @param attributeValue the value from the target configuration, or the default value if not found
     * @return a score for the match
     */
    int score(String requestedValue, String attributeValue);

    /**
     * Returns a default value for a missing attribute in the target configuration.
     * @param requestedValue the value which has been requested
     * @return a value that will be used to compare a score, or <code>null</code> if this attribute must absolutely be found.
     */
    String defaultValue(String requestedValue);
}