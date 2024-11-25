(ns sqlite-component.core
  (:require [clojure.tools.logging :as log]
            [integrant.core :as ig]
            [next.jdbc :as jdbc]))

(defmethod ig/init-key ::sqlite
  [_ {:keys [components]}]
  (log/info :starting ::sqlite)
  (-> components :config :sqlite jdbc/get-connection))

(defmethod ig/halt-key! ::sqlite
  [_ _]
  (log/info :stopping ::sqlite))
