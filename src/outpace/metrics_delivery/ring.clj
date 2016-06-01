(ns outpace.metrics-delivery.ring
  (:require
    [metrics.core :refer [default-registry]]
    [metrics.counters :refer (counter inc! dec!)]
    [metrics.meters :refer (meter mark!)]
    [metrics.timers :refer (timer time!)])
  (:import [com.codahale.metrics MetricRegistry]))

(defn instrument-by-uri
  "Instrument a ring handler by uri"
  ([handler]
   (instrument-by-uri handler default-registry))
  ([handler ^MetricRegistry reg]
   (let [uris (atom {})
         times (atom {})]
     (fn [{:keys [uri] :as request}]
       ;; TODO: can we detect /user/123ab is really just /user
       (when [uri]
         (mark! (get @uris uri
                     (let [m (meter reg ["ring" "uri" uri])]
                       (swap! uris assoc uri m)
                       m))))
       (time! (get @times uri
                   (let [t (timer reg ["ring" "uri-time" uri])]
                     (swap! times assoc uri t)
                     t))
              (handler request))))))
