(defproject com.outpace/metrics-delivery "0.1.0-SNAPSHOT"
  :description "Conveniently start metric reporters from data (configuration)."
  :url "http://github.com/outpace/metrics-delivery"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[ring "1.4.0"]
                                  [ring/ring-defaults "0.2.0"]
                                  [ring/ring-mock "0.3.0"]
                                  [compojure "1.5.0"]]}}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [metrics-clojure "2.6.1"]
                 [metrics-clojure-graphite "2.6.1"]
                 [metrics-clojure-ring "2.6.1"]
                 [metrics-clojure-jvm "2.6.1"]
                 [org.clojure/core.memoize "0.5.8"]])
