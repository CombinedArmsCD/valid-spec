(ns cacd.valid-spec.commons-import

  (:import [org.apache.commons.validator.routines

           CurrencyValidator
           CreditCardValidator

           DomainValidator
           UrlValidator
           EmailValidator]))


(defn CurrencyValid?
  "This returns true or false if a currency is value <bool rtn> only."
  [x]
  (let [a (CurrencyValidator/getInstance)]
    (.isValid a x))
  )

(defn rtn-CurrencyValid
  "This returns a valid, BigDecimal number from a currency, or nil.  Value is returned."
  [x]
  (let [a (CurrencyValidator/getInstance)]
    (.validate a x)))

(defn CreditCardValid?
  "This is not implemented yet."
  [x]
  (throw
    (UnsupportedOperationException.
      (str
        "CreditCardValid? has not been implememted yet.\n Input was :" x))))

(defn fn-DomainValid?
  "This takes an input and checks if it is a valid domain.
  Options include if local domains are acceptable.

  Local domains end in localhost or do not have sub domain names.

  Domain names are evaluated according to the standards RFC1034, section 3, and RFC1123, section 2.1.
  "

  [& {:keys [local]
      :or {local nil}}]
  (let [a (if local
            (DomainValidator/getInstance true)
            (DomainValidator/getInstance))]
    (fn [x]
      (.isValid a x))))

(defn fn-UrlValid?
  "This takes an input and check if it is a valid domain."
  [& {:keys [schemes local-urls]
      :or {schemes nil local-urls false}}]
  (let [opt (+ UrlValidator/NO_FRAGMENTS
               (if (= schemes "ALL")
                 UrlValidator/ALLOW_ALL_SCHEMES
                 0)
               (if local-urls
                 UrlValidator/ALLOW_LOCAL_URLS
                 0))
        a (if (and schemes (not= schemes "ALL"))
            (new UrlValidator (into-array String schemes) opt)
            (new UrlValidator opt))]
   (fn [x] (.isValid a x))))


(defn EmailValidFormat?
  "This takes an input and returns true if email is a validly formed email address
  in accordance with RFC 822"
  [x]
  (let [a (EmailValidator/getInstance)]
    (.isValid a x)))


;; Comment area
;; When attempting to access private or protected objects in java.
;; The function below can work
(comment
  ;; (defn invoke-private-method
  ;; [obj fn-name & args]
  ;; (let [m (first
  ;;           (filter (fn [x]
  ;;                     (.. x getName (equals fn-name)))
  ;;                   (.. obj getClass getDeclaredMethods)))]
  ;;   (. m (setAccessible true))
  ;;   (. m (invoke obj (into-array Object args)))))

)
