package io.github.cluo29.contextdatareading.semantization.parser;

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
import io.github.cluo29.contextdatareading.AbstractEvent;
import io.github.cluo29.contextdatareading.semantization.config.ConfigEntries;
import io.github.cluo29.contextdatareading.semantization.config.Configuration;
import io.github.cluo29.contextdatareading.semantization.config.JsonConfigReader;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstractEventParser implements Parser {
    JsonConfigReader jsonConfigReader = new JsonConfigReader();
    public Configuration configuration = jsonConfigReader.deserializeConfiguration();
    public final String not_specified = "not_specified";

    public Map<String, String> parseTimeStamp(AbstractEvent event) {
        long timestamp = event.timestamp();
        String aggregate = configuration.time.entries[1].aggregate;
        Map<String, String> result = new HashMap<String, String>();
        String weekDay;
        String dayTime;
        if (aggregate.equals("true")) {
            weekDay = getWeekDay(timestamp);
            result.put(ConfigEntries.WEEK_DAY.getValue(),weekDay);
        }
        dayTime = getDayTime(timestamp);
        result.put(ConfigEntries.TIMESTAMP.getValue(),dayTime);
        return result;
    }

    private String getDayTime(long timestamp) {
        LocalTime time = new LocalTime(timestamp);
        String dayTime;

        Configuration.Time.Entry entry = getProperConfigEntryByName(ConfigEntries.TIMESTAMP.getValue());
        Configuration.Time.Entry.Interval intervals[] =entry.intervals;

        for (int i = 0; i < intervals.length; i++) {
            String from = intervals[i].from;
            String to = intervals[i].to;

            int fromHour = Integer.valueOf(from.substring(0, 2));
            int fromMinute = Integer.valueOf(from.substring(3, 5));
            int toHour = Integer.valueOf(to.substring(0, 2));
            int toMinute = Integer.valueOf(to.substring(3, 5));

            LocalTime fromTime = new LocalTime(fromHour, fromMinute);
            LocalTime toTime = new LocalTime(toHour, toMinute);

            if (time.isAfter(fromTime) && time.isBefore(toTime)) {
                dayTime = intervals[i].label;
                return dayTime;
            }
        }
        return not_specified;
    }


    private String getWeekDay(long timestamp) {
        DateTime time = new DateTime(timestamp);
        String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
        Configuration.Time.Entry entry = getProperConfigEntryByName(ConfigEntries.WEEK_DAY.getValue());
        Configuration.Time.Entry.Interval intervals[] =entry.intervals;
        String type1 = intervals[0].label;
        String type2 = intervals[1].label;
        String[] daysType1 = intervals[0].included;
        String[] daysType2 = intervals[1].included;

        String eventDay = days[time.getDayOfWeek()];

        for (int i = 0; i < daysType1.length; i++) {
            if (eventDay.equals(daysType1[i])) return type1;
        }
        for (int i = 0; i < daysType2.length; i++) {
            if (eventDay.equals(daysType1[i])) return type2;
        }
        return not_specified;
    }

    private Configuration.Time.Entry getProperConfigEntryByName(String name) {
        Configuration.Time.Entry[] entries = configuration.time.entries;
        for (Configuration.Time.Entry entry : entries) {
            if (entry.name.equals(name)) return entry;
        }
        return null;
    }

}
