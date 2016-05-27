# metrics-delivery

Conveniently start metric reporters from data (configuration).

## Usage

Add the following dependency to your `project.clj` file:

[![Clojars Project](http://clojars.org/com.outpace/metrics-delivery/latest-version.svg)](http://clojars.org/com.outpace/metrics-delivery)

Configure your reporters in a data structure, or pull it from the environment:

```clj
(defconfig metrics-config
  {:jmx {}
   :graphite {:host "influxdb"
              :port 2003
              :prefix "quant"
              :rate-unit :seconds
              :duration-unit :milliseconds
              :filter :all}})  
```

And now to start reporting:

```clj
(start-metrics metrics-config)
```

Check out `outpace.config` for a concise and flexible way to manage your configuration.


## License

Copyright © 2016 Outpace

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
