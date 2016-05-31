# metrics-delivery

Conveniently start metric reporters from data (configuration).

## Usage

Add the following dependency to your `project.clj` file:

[![Clojars Project](http://clojars.org/com.outpace/metrics-delivery/latest-version.svg)](http://clojars.org/com.outpace/metrics-delivery)

Require the library in your code:

```clj
    [outpace.metrics-delivery.core :as metrics-delivery]
```

Configure your reporters in a data structure, or pull it from the environment:

```clj
(defconfig metrics-config
  {:instrument {:jvm :all
                :ring :routes}
   :report {:jmx {}
            :graphite {:host "myhost"
                       :port 2003
                       :prefix "myapp"}}})
```

And start reporting (from an init or main function):

```clj
(metrics-delivery/start metrics-config)
```

Create custom metrics (guages, counters, meters, histograms, timers) using `metrics-clojure`
http://metrics-clojure.readthedocs.io/en/latest/metrics/meters.html

You can specify multiple reporters of the same type using a vector of vectors instead of a map:
```clj
[[:graphite {:host "host1"}]
 [:graphite {:host "host2"}]]
```

Check out `outpace.config` for a concise and flexible way to manage your configuration.
https://github.com/outpace/config


## License

Copyright © 2016 Outpace

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
