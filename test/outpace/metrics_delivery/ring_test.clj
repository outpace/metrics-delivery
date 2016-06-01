(ns outpace.metrics-delivery.ring-test
  (:require
    [clojure.test :refer :all]
    [outpace.metrics-delivery.ring :refer :all]
    [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
    [ring.mock.request :as mock]
    [compojure.core :refer [defroutes GET]]))

(defroutes test-routes
  (GET "/status" req "Running")
  (GET "/user/123" req "123"))

(def handler
  (-> test-routes
    (wrap-defaults api-defaults)
    (instrument-by-uri)))

(deftest ring-test
  (is (= "Running"
         (:body (handler (mock/request :get "/status"))))))
