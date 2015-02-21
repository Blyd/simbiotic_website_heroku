(ns simbiotic-website.email-system
  (:import (javax.mail
             Authenticator
             PasswordAuthentication
             Session
             Message$RecipientType
             Transport)
           (javax.mail.internet
             MimeMessage
             InternetAddress)))

(def to "travis.akablyd@hotmail.com")
(def from "javaxmailtestemail@gmail.com")

(def properties (System/getProperties))
(.put properties "mail.smtp.port" "587")
(.put properties "mail.smtp.starttls.enable" "true")
(.put properties "mail.smtp.host" "smtp.gmail.com")
(.put properties "mail.smtp.user" "javaxmailtestemail@gmail.com")
(.put properties "mail.smtp.password" "javaxmail")
(.put properties "mail.smtp.auth" "true")




(def username "javaxmailtestemail@gmail.com")
(def password "javaxmail")
;(def host "localhost")
;(.setProperty properties "mail.smtp.host" host)

(defn authenticator [u p]
  (proxy [Authenticator] []
      (getPasswordAuthentication []
        (new PasswordAuthentication u p))))
(def session (Session/getInstance properties (authenticator username password)))
(def message (new MimeMessage session))
(.setFrom message (new InternetAddress from))
(.addRecipient message (Message$RecipientType/TO) (first (InternetAddress/parse to))) ;(InternetAddress/parse to) (new InternetAddress to)
(.setSubject message "This is the Subject line")
(.setText message "This is the message")




(defn send-email []
  (Transport/send message)) ;Firewalls can block this proccess. But perheps this won't be a problem when pushed to heroku?