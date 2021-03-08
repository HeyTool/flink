/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.table.api;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.configuration.Configuration;

import org.junit.Assert;
import org.junit.Test;

import static org.apache.flink.configuration.ExecutionOptions.RUNTIME_MODE;
import static org.apache.flink.table.api.config.TableConfigOptions.TABLE_PLANNER;

/** Test {@link EnvironmentSettings}. */
public class EnvironmentSettingsTest {

    @Test
    public void testFromConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setString("execution.runtime-mode", "batch");
        configuration.setString("table.planner", "old");
        EnvironmentSettings settings = EnvironmentSettings.fromConfiguration(configuration);

        Assert.assertFalse("Expect old planner.", settings.isBlinkPlanner());
        Assert.assertFalse("Expect batch mode.", settings.isStreamingMode());
    }

    @Test
    public void testToConfiguration() {
        EnvironmentSettings settings =
                new EnvironmentSettings.Builder().useOldPlanner().inBatchMode().build();
        Configuration configuration = settings.toConfiguration();

        Assert.assertEquals(PlannerType.OLD, configuration.get(TABLE_PLANNER));
        Assert.assertEquals(RuntimeExecutionMode.BATCH, configuration.get(RUNTIME_MODE));
    }
}
