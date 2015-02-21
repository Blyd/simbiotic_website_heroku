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
(def from username)

(def properties (System/getProperties))
(.put properties "mail.smtp.port" "587")
(.put properties "mail.smtp.starttls.enable" "true")
(.put properties "mail.smtp.host" "smtp.gmail.com")
(.put properties "mail.smtp.user" username)
(.put properties "mail.smtp.password" password)
(.put properties "mail.smtp.auth" "true")

(defn authenticator [u p]
  (proxy [Authenticator] []
      (getPasswordAuthentication []
        (new PasswordAuthentication u p))))
(def session (Session/getInstance properties (authenticator username password)))
(def message (new MimeMessage session))
(.addRecipient message (Message$RecipientType/TO) (first (InternetAddress/parse to))) ;(InternetAddress/parse to) (new InternetAddress to)
(.setFrom message (new InternetAddress "asd"))
(.setSubject message "asd")
(.setText message "asd")

(defn send-email [name subject body]
  (future
    (println "Email sending...")
    (.setFrom message (new InternetAddress name))
    (.setSubject message subject)
    (.setText message (str name ": " body))
    (Transport/send message)
    (println "Email sent using rerouter."))) ;Firewalls can block this proccess. But perheps this won't be a problem when pushed to heroku?