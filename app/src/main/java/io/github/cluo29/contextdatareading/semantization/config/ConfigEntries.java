package io.github.cluo29.contextdatareading.semantization.config;

/* https://github.com/cluo29 Chu reused their code
 * Copyright 2014 Szymon Bielak <bielakszym@gmail.com> and
 *     Michał Rus <https://michalrus.com/>
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
public enum ConfigEntries {
    TIMESTAMP("timestamp"),
    WEEK_DAY("weekday"),
    GEOGRAPHICAL("geographical"),
    SPEED("speed"),
    ACTIVITY_NAME("name"),
    ACTIVITY_CONFIDENCE("confidence"),
    RECEIVED_BYTES("received_bytes"),
    SENT_BYTES("sent_bytes"),
    RECEIVED_PACKETS("received_packets"),
    SENT_PACKETS("sent_packets"),
    APP_NAME("name"),
    APP_USAGE("usage_time");

    private String value;

    ConfigEntries(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
