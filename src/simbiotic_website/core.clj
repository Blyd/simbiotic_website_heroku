(ns simbiotic_website.core
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [postal.core :as postal])

  (:use simbiotic-website.main-page
        simbiotic-website.email-system))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (pr-str ["Hello" :from 'Travisty])})

(defn splash []
  {:status 302
   ;:headers {"Content-Type" "image/png"} ;text/html
   :body (slurp (io/resource "simbiotic_Website.html"))})

(comment splash)
(defroutes app
           (GET "/" []
                (splash) (send-email))
           (GET "/available_Games.html" []
                (slurp (io/resource "available_Games.html")))
           (GET "/contact.html" []
                (slurp (io/resource "contact.html")))
           (ANY "*" []
                (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))

(comment (GET "/simbiotic_Website.html" [] (slurp (io/resource "simbiotic_Website.html"))))

(comment (slurp (io/resource "simbiotic_Website.html")))
