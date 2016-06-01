(ns outpace.metrics-delivery.core-test
  (:require [clojure.test :refer :all]
            [outpace.metrics-delivery.core :refer :all]
            [metrics.gauges :refer [defgauge]]))

(defgauge a-num
  #(rand-int 1000))

#_(deftest a-test
  (start
    {:instrument {:jvm :all}
     :report {:console {:period 1}
              ;;:csv {:period 1}
              }})
  (Thread/sleep 10000)
  (stop-metrics))
