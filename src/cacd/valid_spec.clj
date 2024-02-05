(ns cacd.valid-spec
  (:require [clojure.spec.alpha :as s]))

(defn ipv4? [x]
  (try
    (let [[_ a b c d] (re-matches #"(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})" x)
          between (fn [y] (and (<= 0 (Integer. y))
                              (>= 255 (Integer. y))
                              )
                    )
          ]
      (if (and (between a) (between b) (between c) (between d))
        true
        false))
      (catch Exception e false)))


(s/def :network/ipv4 ipv4?)
