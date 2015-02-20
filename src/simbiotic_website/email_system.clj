(ns simbiotic-website.email-system)

(import java.util.Properties)
(import 'javax.mail.Address)
(import javax.mail.Message)
(import javax.mail.Session)
(import javax.mail.internet.MimeMessage)
(import javax.mail.internet.InternetAddress)
(import javax.mail.Message$RecipientType)
(import javax.mail.Transport)

(def properties (System/getProperties))
(def to "javaxmailtestemail@gmail.com")
(def from "javaxmailtestemail@gmail.com")
(def host "localhost")
(.setProperty properties "mail.smtp.host" host)
(def session (Session/getDefaultInstance properties))
(def message (new MimeMessage session))
(.setFrom message (new InternetAddress from))
(.addRecipient message (Message$RecipientType/TO) (new InternetAddress to))
(.setSubject message "This is the Subject line")
(.setText message "This is the message")

(.put properties "mail.smtp.port" "25")
(.put properties "mail.smtp.starttls.enable" "true")
(.put properties "mail.smtp.host" "smtp.gmail.com")
(.put properties "mail.smtp.user" "javaxmailtestemail@gmail.com")
(.put properties "mail.smtp.password" "javaxmail")
(.put properties "mail.smtp.auth" "true")


(defn send-email []
  (Transport/send message))

(comment (def authenticator (let [username "javaxmailtestemail@gmail.com"
                                  password "javaxmail"]
                              (proxy [Authenticator] []
                                (PasswordAuthentication [username password]
                                  (new PasswordAuthentication username password))))))