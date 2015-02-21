(ns simbiotic-website.email-system
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]])

  (:import (javax.mail
             Authenticator
             PasswordAuthentication
             Session
             Message$RecipientType
             Transport)
           (javax.mail.internet
             MimeMessage
             InternetAddress)))

(def username "tjrr.simbiotic@gmail.com")
(def password "travis.jacob.reroute.simbiotic.service")

(def to "tj.simbiotic.service@gmail.com")

(def properties
  (doto (System/getProperties)
    (.put "mail.smtp.port" "587")
    (.put "mail.smtp.starttls.enable" "true")
    (.put "mail.smtp.host" "smtp.gmail.com")
    (.put "mail.smtp.user" username)
    (.put "mail.smtp.password" password)
    (.put "mail.smtp.auth" "true")))

(defn authenticator [u p]
  (proxy [Authenticator] []
      (getPasswordAuthentication []
        (new PasswordAuthentication u p))))
(def session (Session/getInstance properties (authenticator username password)))
(def message (new MimeMessage session))
(.addRecipient message (Message$RecipientType/TO) (first (InternetAddress/parse to))) ;(InternetAddress/parse to) (new InternetAddress to)

(defn send-email
  ;BOOKMARK Firewalls can block this proccess. But perheps this won't be a problem when pushed to heroku?
  "Asyncronous"
  [name email subject body]
  (future
    (println "Email sending...")
    (doto message
      (.setFrom (new InternetAddress name))
      (.setSubject subject)
      (.setText (str name " - " email ": " body)))
    (Transport/send message)
    (println "Email sent using rerouter.")))