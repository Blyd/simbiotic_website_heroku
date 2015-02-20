(ns simbiotic_website.core
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]])

  (:use simbiotic-website.main-page))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (pr-str ["Hello" :from 'Travisty])})

(defn splash []
  {:status 302
   :headers {"Content-Type" "text/html"}
   :body visual})

(defroutes app
           (GET "/" []
                (splash))
           (GET "/available_Games.html" [] (slurp (io/resource "available_Games.html")))
           (ANY "*" []
                (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
