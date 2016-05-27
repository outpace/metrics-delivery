(ns outpace.metrics-delivery.core
  "Conveniently start metric reporters from data (configuration)."
  (:require
    [metrics.reporters.graphite :as graphite]
    [metrics.reporters.jmx :as jmx]
    [metrics.reporters.csv :as csv]
    [metrics.reporters.console :as console])
  (:import
    [java.util.concurrent TimeUnit]
    [com.codahale.metrics MetricFilter]))

(def constructor
  {:jmx jmx/reporter
   :graphite graphite/reporter
   :csv #(csv/reporter "./" %)
   :console console/reporter})

(def starter
  {:jmx jmx/start
   :graphite graphite/start
   :csv csv/start
   :console console/start})

(def stopper
  {:jmx jmx/stop
   :graphite graphite/stop
   :csv csv/stop
   :console console/stop})

(def keywords
  {:all MetricFilter/ALL
   :seconds TimeUnit/SECONDS
   :milliseconds TimeUnit/MILLISECONDS
   :microseconds TimeUnit/MICROSECONDS
   :nanoseconds TimeUnit/NANOSECONDS
   :minutes TimeUnit/MINUTES
   :hours TimeUnit/HOURS
   :days TimeUnit/DAYS})

(defn javaize [options]
  (into {}
        (for [[k v] options]
          [k (get keywords v v)])))

(def reporters {})

(defn stop-metrics []
  (doseq [[k v] reporters
          :let [stop (stopper k)]]
    (if stop
      (stop v)
      (throw (ex-info "No stopper found"
                      {:k k
                       :available (keys stopper)}))))
  (alter-var-root #'reporters (constantly nil)))

(defn start-metrics
  "Expects a map of reporter-type to reporter-options,
  where reporter-type is one of :jmx :graphite :csv :console
  Also you can specify :period in seconds."
  [config]
  (when reporters
    (stop-metrics))
  (alter-var-root #'reporters
    (constantly
      (into {}
            (for [[k options] config
                  :let [create (constructor k)
                        start (starter k)]]
              [k
               (cond
                 (not (map? options))
                 (throw (ex-info "Options must be a map"
                                 {:k k
                                  :options options}))
                 (not (and create start))
                 (throw (ex-info "No constructor found"
                                 {:k k
                                  :available (keys constructor)}))
                 :else
                 (doto (create (javaize options))
                   (start (get options :period 10))))]))))
  stop-metrics)
