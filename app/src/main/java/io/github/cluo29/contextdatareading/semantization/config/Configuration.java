package io.github.cluo29.contextdatareading.semantization.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Configuration {
    public final Time time;
    public final Locations locations;
    public final Activities activities;
    public final Network network;
    public final Applications applications;

    @JsonCreator
    public Configuration(@JsonProperty("time") Time time, @JsonProperty("locations") Locations locations, @JsonProperty("activities") Activities activities, @JsonProperty("network") Network network, @JsonProperty("applications") Applications applications) {
        this.time = time;
        this.locations = locations;
        this.activities = activities;
        this.network = network;
        this.applications = applications;
    }

    public static final class Time {
        public final Entry entries[];

        @JsonCreator
        public Time(@JsonProperty("entries") Entry[] entries) {
            this.entries = entries;
        }

        public static final class Entry {
            public final String name;
            public final String type;
            public final Interval intervals[];
            public final String aggregate;

            @JsonCreator
            public Entry(@JsonProperty("name") String name, @JsonProperty("type") String type, @JsonProperty("intervals") Interval[] intervals, @JsonProperty("aggregate") String aggregate) {
                this.name = name;
                this.type = type;
                this.intervals = intervals;
                this.aggregate = aggregate;
            }

            public static final class Interval {
                public final String label;
                public final String[] included;
                public final String from;
                public final String to;


                @JsonCreator
                public Interval(@JsonProperty("label") String label, @JsonProperty("included") String[] included, @JsonProperty("from") String from, @JsonProperty("to") String to) {
                    this.label = label;
                    this.included = included;
                    this.from = from;
                    this.to = to;
                }
            }
        }
    }

    public static final class Locations {
        public final Entry entries[];

        @JsonCreator
        public Locations(@JsonProperty("entries") Entry[] entries) {
            this.entries = entries;
        }

        public static final class Entry {
            public final String name;
            public final String table;
            public final String column_x;
            public final String column_y;
            public final String type;
            public final Interval intervals[];
            public final String column;

            @JsonCreator
            public Entry(@JsonProperty("name") String name, @JsonProperty("table") String table, @JsonProperty(value = "column_x", required = false) String column_x, @JsonProperty(value = "column_y", required = false) String column_y, @JsonProperty("type") String type, @JsonProperty("intervals") Interval[] intervals, @JsonProperty("column") String column) {
                this.name = name;
                this.table = table;
                this.column_x = column_x;
                this.column_y = column_y;
                this.type = type;
                this.intervals = intervals;
                this.column = column;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static final class Interval {
                public final String label;
                public final Vertice[] vertices;
                public final double from;
                public final double to;


                @JsonCreator
                public Interval(@JsonProperty("label") String label, @JsonProperty("vertices") Vertice vertices[], @JsonProperty("from") double from, @JsonProperty("to") double to) {
                    this.label = label;
                    this.vertices = vertices;
                    this.from=from;
                    this.to=to;

                }

                public static final class Vertice {
                    public final double column_x;
                    public final double column_y;


                    @JsonCreator
                    public Vertice(@JsonProperty("column_x") double column_x, @JsonProperty("column_y") double column_y) {
                        this.column_x = column_x;
                        this.column_y = column_y;
                    }
                }
            }
        }
    }

    public static final class Activities {
        public final Entry entries[];

        @JsonCreator
        public Activities(@JsonProperty("entries") Entry[] entries) {
            this.entries = entries;
        }

        public static final class Entry {
            public final String name;
            public final String table;
            public final String column;
            public final String type;
            public final Interval intervals[];

            @JsonCreator
            public Entry(@JsonProperty("name") String name, @JsonProperty("table") String table, @JsonProperty("column") String column, @JsonProperty("type") String type, @JsonProperty("intervals") Interval[] intervals) {
                this.name = name;
                this.table = table;
                this.column = column;
                this.type = type;
                this.intervals = intervals;
            }

            public static final class Interval {
                public final String label;
                public final long from;
                public final long to;
                public final String included[];


                @JsonCreator
                public Interval(@JsonProperty("label") String label, @JsonProperty("from") long from, @JsonProperty("to") long to, @JsonProperty("included") String[] included) {
                    this.label = label;
                    this.from = from;
                    this.to = to;
                    this.included = included;
                }
            }
        }
    }

    public static final class Network {
        public final Entry entries[];

        @JsonCreator
        public Network(@JsonProperty("entries") Entry[] entries) {
            this.entries = entries;
        }

        public static final class Entry {
            public final String name;
            public final String table;
            public final String column;
            public final String type;
            public final long window;
            public final Interval intervals[];

            @JsonCreator
            public Entry(@JsonProperty("name") String name, @JsonProperty("table") String table, @JsonProperty("column") String column, @JsonProperty("type") String type, @JsonProperty("window") long window, @JsonProperty("intervals") Interval[] intervals) {
                this.name = name;
                this.table = table;
                this.column = column;
                this.type = type;
                this.window = window;
                this.intervals = intervals;
            }

            public static final class Interval {
                public final String label;
                public final long from;
                public final long to;

                @JsonCreator
                public Interval(@JsonProperty("label") String label, @JsonProperty("from") long from, @JsonProperty("to") long to) {
                    this.label = label;
                    this.from = from;
                    this.to = to;
                }
            }
        }
    }

    public static final class Applications {
        public final Entry entries[];

        @JsonCreator
        public Applications(@JsonProperty("entries") Entry[] entries) {
            this.entries = entries;
        }

        public static final class Entry {
            public final String name;
            public final String table;
            public final String column;
            public final String type;
            public final Interval intervals[];

            @JsonCreator
            public Entry(@JsonProperty("name") String name, @JsonProperty("table") String table, @JsonProperty("column") String column, @JsonProperty("type") String type, @JsonProperty("intervals") Interval[] intervals) {
                this.name = name;
                this.table = table;
                this.column = column;
                this.type = type;
                this.intervals = intervals;
            }

            public static final class Interval {
                public final String label;
                public final long from;
                public final long to;
                public final String included[];

                @JsonCreator
                public Interval(@JsonProperty("label") String label, @JsonProperty("from") long from, @JsonProperty("to") long to, @JsonProperty("included") String[] included) {
                    this.label = label;
                    this.from = from;
                    this.to = to;
                    this.included = included;
                }
            }
        }
    }
}